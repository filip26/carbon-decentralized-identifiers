package com.apicatalog.did;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("DID URL")
@TestMethodOrder(OrderAnnotation.class)
class DidUrlTest {

    @DisplayName("from(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void fromString(String uri, String method, String specificId, String path, String query, String fragment) {
        try {

            final DidUrl didUrl = DidUrl.from(uri);

            assertNotNull(didUrl);
            assertTrue(didUrl.isDidUrl());
            assertEquals(method, didUrl.getMethod());
            assertEquals(specificId, didUrl.getMethodSpecificId());
            assertEquals(path, didUrl.getPath());
            assertEquals(query, didUrl.getQuery());
            assertEquals(fragment, didUrl.getFragment());

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @DisplayName("from(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void fromUri(String input, String method, String specificId, String path, String query, String fragment) {
        try {

            final DidUrl didUrl = DidUrl.from(URI.create(input));

            assertNotNull(didUrl);
            assertTrue(didUrl.isDidUrl());
            assertEquals(method, didUrl.getMethod());
            assertEquals(specificId, didUrl.getMethodSpecificId());
            assertEquals(path, didUrl.getPath());
            assertEquals(query, didUrl.getQuery());
            assertEquals(fragment, didUrl.getFragment());

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @DisplayName("isDidUrl(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void stringIsDid(String uri) {
        assertTrue(DidUrl.isDidUrl(uri));
    }

    @DisplayName("isDidUrl(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void uriIsDid(String uri) {
        assertTrue(DidUrl.isDidUrl(URI.create(uri)));
    }

    static Stream<String[]> validVectors() {
        return Arrays.stream(new String[][] {
                {
                        "did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "example",
                        "z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        null,
                        null,
                        null,
                },
                {
                        "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "key",
                        "1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        null,
                        null,
                        null,
                },
                {
                        "did:web:method:specific:identifier",
                        "web",
                        "method:specific:identifier",
                        null,
                        null,
                        null,
                },
                {
                        "did:example:123456/path",
                        "example",
                        "123456",
                        "/path",
                        null,
                        null
                },
                {
                        "did:example:123456?versionId=1",
                        "example",
                        "123456",
                        null,
                        "versionId=1",
                        null
                },
                {
                        "did:example:123#public-key-0",
                        "example",
                        "123",
                        null,
                        null,
                        "public-key-0"
                },
                {
                        "did:example:123?service=agent&relativeRef=/credentials#degree",
                        "example",
                        "123",
                        null,
                        "service=agent&relativeRef=/credentials",
                        "degree"
                },
                {
                        "did:example:123?service=files&relativeRef=/resume.pdf",
                        "example",
                        "123",
                        null,
                        "service=files&relativeRef=/resume.pdf",
                        null
                },
                {
                        "did:example:123?",
                        "example",
                        "123",
                        null,
                        "",
                        null
                },
                {
                        "did:example:123#",
                        "example",
                        "123",
                        null,
                        null,
                        ""
                }

        });
    }
}
