package com.apicatalog.did;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("DID")
@TestMethodOrder(OrderAnnotation.class)
class DidTest {

    @DisplayName("from(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void fromString(String uri, String method, String specificId) {
        final Did did = Did.from(uri);

        assertNotNull(did);
        assertFalse(did.isDidUrl());
        assertEquals(method, did.getMethod());
        assertEquals(specificId, did.getMethodSpecificId());
    }

    @DisplayName("!from(String)")
    @ParameterizedTest()
    @MethodSource({ "negativeVectors" })
    void fromStringNegative(String uri) {
        try {
            Did.from(uri);
            fail();
        } catch (IllegalArgumentException e) {
            /* expected */ }
    }

    @DisplayName("from(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void fromUri(String input, String method, String specificId) {
        final Did did = Did.from(URI.create(input));

        assertNotNull(did);
        assertFalse(did.isDidUrl());
        assertEquals(method, did.getMethod());
        assertEquals(specificId, did.getMethodSpecificId());
    }

    @DisplayName("toString()")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void toString(String input, String method, String specificId) {
        final Did did = Did.from(input);

        assertNotNull(did);
        assertEquals(input, did.toString());
    }

    @DisplayName("!from(URI)")
    @ParameterizedTest()
    @MethodSource({ "negativeVectors" })
    void fromUriNegative(String uri) {
        try {
            Did.from(URI.create(uri));
            fail();
        } catch (IllegalArgumentException | NullPointerException e) {
            /* expected */ }
    }

    @DisplayName("toUri()")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void toUri(String input, String method, String specificId) {
        final Did did = Did.from(URI.create(input));

        assertNotNull(did);
        assertEquals(URI.create(input), did.toUri());
    }

    @DisplayName("isDid(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void stringIsDid(String uri) {
        assertTrue(Did.isDid(uri));
    }

    @DisplayName("isNotDid(String)")
    @ParameterizedTest()
    @MethodSource({ "negativeVectors" })
    void stringIsNotDid(String uri) {
        assertFalse(Did.isDid(uri));
    }

    @DisplayName("isDid(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void uriIsDid(String uri) {
        assertTrue(Did.isDid(URI.create(uri)));
    }

    static Stream<Arguments> validVectors() {
        return Stream.of(
                Arguments.of(
                        "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                        "key",
                        "z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH"),
                Arguments.of("did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "example",
                        "z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"),
                Arguments.of("did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "key",
                        "1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"),
                Arguments.of("did:web:method:specific:identifier",
                        "web",
                        "method:specific:identifier"),
                Arguments.of("did:tdw:example.com:dids:12345",
                        "tdw",
                        "example.com:dids:12345"),
                Arguments.of("did:tdw:12345.example.com",
                        "tdw",
                        "12345.example.com"),
                Arguments.of("did:tdw:example.com_12345",
                        "tdw",
                        "example.com_12345"));
    }

    static Stream<Arguments> negativeVectors() {
        return Stream.of(
                Arguments.of("did:example:123456/path"),
                Arguments.of("did:example:123456?versionId=1"),
                Arguments.of("did:example:123#public-key-0"),
                Arguments.of("did:example:123?service=agent&relativeRef=/credentials#degree"),
                Arguments.of("did:example:123?service=files&relativeRef=/resume.pdf"),
                Arguments.of("did:example:123#"),
                Arguments.of("did:example:123?"),
                Arguments.of("did:example:123/"),
                null,
                Arguments.of(""),
                Arguments.of("https://example.com"),
                Arguments.of("irc:example:channel"),
                Arguments.of("did:example.com:channel"),
                Arguments.of("did:example: "),
                Arguments.of("did:example:"),
                Arguments.of("did:example"),
                Arguments.of("did:"),
                Arguments.of("did"),
                Arguments.of(":example:channel"),
                Arguments.of(" :example:channel"),
                Arguments.of("did::channel"),
                Arguments.of("did: :channel"),
                Arguments.of(" did:method:id"),
                Arguments.of("did:method:id "));
    }

}
