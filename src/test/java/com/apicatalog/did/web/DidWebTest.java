package com.apicatalog.did.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("DID Web")
@TestMethodOrder(OrderAnnotation.class)
class DidWebTest {

    @DisplayName("of(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void ofUri(String uri, URI url) {

        final DidWeb didWeb = DidWeb.of(URI.create(uri));

        assertNotNull(didWeb);
        assertEquals(url, didWeb.getUrl());
        assertEquals(url.getHost(), didWeb.getDomain());
        assertEquals(url.getPort(), didWeb.getPort());
        assertEquals(url.getPath(), didWeb.getPath());
    }

    @DisplayName("! of(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "negativeVectors" })
    void ofUriFail(String uri) {
        assertThrows(IllegalArgumentException.class, () -> DidWeb.of(URI.create(uri)));
    }

    static Stream<Arguments> positiveVectors() {
        return Stream.of(
                Arguments.of("did:web:w3c-ccg.github.io",
                        URI.create("https://w3c-ccg.github.io/.well-known/did.json")),
                Arguments.of("did:web:w3c-ccg.github.io:user:alice",
                        URI.create("https://w3c-ccg.github.io/user/alice/did.json")),
                Arguments.of("did:web:example.com%3A3000",
                        URI.create("https://example.com:3000/.well-known/did.json")),
                Arguments.of("did:web:example.com%3A3000:user:alice",
                        URI.create("https://example.com:3000/user/alice/did.json")),
                Arguments.of("did:web:example.com:u:bob",
                        URI.create("https://example.com/u/bob/did.json")));
    }

    static Stream<Arguments> negativeVectors() {
        return Stream.of(
                Arguments.of("did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH"));
    }

}
