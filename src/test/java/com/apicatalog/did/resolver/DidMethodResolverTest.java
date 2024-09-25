package com.apicatalog.did.resolver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.did.Did;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.key.DidKey;
import com.apicatalog.did.key.DidKeyResolver;
import com.apicatalog.multibase.MultibaseDecoder;

@DisplayName("DID Method Resolver")
@TestMethodOrder(OrderAnnotation.class)
class DidMethodResolverTest {

    static DidResolver RESOLVER = DidMethodResolver.create()
            .add(DidKey.METHOD_NAME, new DidKeyResolver(MultibaseDecoder.getInstance()))
            .build();

    @DisplayName("resolve(did)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void resolve(String uri) {
        final DidDocument doc = RESOLVER.resolve(Did.from(uri));
        assertNotNull(doc);
    }

    static Stream<Arguments> positiveVectors() {
        return Stream.of(
                Arguments.of(
                        "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                        "key",
                        "z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH"),
                Arguments.of("did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "key",
                        "1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2")
        );
    }

    static Stream<Arguments> negativeVectors() {
        return Stream.of(
                Arguments.of("did:example:123456/path"),
                Arguments.of("did:web:method:specific:identifier"),
                Arguments.of("did:tdw:example.com_12345"));
    }

}
