package com.apicatalog.did;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("DID URL")
@TestMethodOrder(OrderAnnotation.class)
class DidUrlTest {

    @DisplayName("of(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void ofString(String uri, String method, String specificId, String path, String query, String fragment) {
        final DidUrl didUrl = DidUrl.of(uri);

        assertNotNull(didUrl);
        assertTrue(didUrl.isDidUrl());
        assertEquals(method, didUrl.getMethod());
        assertEquals(specificId, didUrl.getMethodSpecificId());
        assertEquals(path, didUrl.getPath());
        assertEquals(query, didUrl.getQuery());
        assertEquals(fragment, didUrl.getFragment());
    }

    @DisplayName("of(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void ofUri(String input, String method, String specificId, String path, String query, String fragment) {
        final DidUrl didUrl = DidUrl.of(URI.create(input));

        assertNotNull(didUrl);
        assertTrue(didUrl.isDidUrl());
        assertEquals(method, didUrl.getMethod());
        assertEquals(specificId, didUrl.getMethodSpecificId());
        assertEquals(path, didUrl.getPath());
        assertEquals(query, didUrl.getQuery());
        assertEquals(fragment, didUrl.getFragment());
    }

    @DisplayName("toUri()")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void toUri(String input, String method, String specificId, String path, String query, String fragment) {
        final DidUrl didUrl = DidUrl.of(URI.create(input));

        assertNotNull(didUrl);
        assertEquals(URI.create(input), didUrl.toUri());
    }

    @DisplayName("isDidUrl(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void stringIsDid(String uri) {
        assertTrue(DidUrl.isDidUrl(uri));
    }

    @DisplayName("isDidUrl(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void uriIsDid(String uri) {
        assertTrue(DidUrl.isDidUrl(URI.create(uri)));
    }

    @DisplayName("toString()")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void toString(String input) {
        final DidUrl didUrl = DidUrl.of(input);

        assertNotNull(didUrl);
        assertEquals(input, didUrl.toString());
    }

    static Stream<Arguments> positiveVectors() {
        return Stream.of(
                Arguments.of(
                        "did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "example",
                        "z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        null,
                        null,
                        null),
                Arguments.of("did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "key",
                        "1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        null,
                        null,
                        null),
                Arguments.of("did:web:method:specific:identifier",
                        "web",
                        "method:specific:identifier",
                        null,
                        null,
                        null),
                Arguments.of("did:example:123456/path",
                        "example",
                        "123456",
                        "/path",
                        null,
                        null),
                Arguments.of("did:example:123456?versionId=1",
                        "example",
                        "123456",
                        null,
                        "versionId=1",
                        null),
                Arguments.of("did:example:123#public-key-0",
                        "example",
                        "123",
                        null,
                        null,
                        "public-key-0"),
                Arguments.of("did:example:123?service=agent&relativeRef=/credentials#degree",
                        "example",
                        "123",
                        null,
                        "service=agent&relativeRef=/credentials",
                        "degree"),
                Arguments.of("did:example:123?service=files&relativeRef=/resume.pdf",
                        "example",
                        "123",
                        null,
                        "service=files&relativeRef=/resume.pdf",
                        null),
                Arguments.of("did:example:1?",
                        "example",
                        "1",
                        null,
                        "",
                        null),
                Arguments.of("did:example:a#",
                        "example",
                        "a",
                        null,
                        null,
                        ""),
                Arguments.of("did:example:a/",
                        "example",
                        "a",
                        "/",
                        null,
                        null),
                Arguments.of("did:example:a/?#",
                        "example",
                        "a",
                        "/",
                        "",
                        ""));
    }
}
