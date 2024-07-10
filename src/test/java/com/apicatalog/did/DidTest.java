package com.apicatalog.did;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

@DisplayName("DID")
@TestMethodOrder(OrderAnnotation.class)
class DidTest {

    @DisplayName("DID.from(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void fromString(String uri, String method, String specificId) {
        try {

            final Did did = Did.from(uri);
            
            assertNotNull(did);
            assertEquals(method, did.getMethod());
            assertEquals(specificId, did.getMethodSpecificId());

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @DisplayName("DID.from(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void fromUri(String input, String method, String specificId) {
        try {

            final Did did = Did.from(URI.create(input));

            assertNotNull(did);
            assertEquals(method, did.getMethod());
            assertEquals(specificId, did.getMethodSpecificId());

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @DisplayName("isDid(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "validVectors" })
    void stringIsDid(String uri) {
        assertTrue(Did.isDid(uri));
    }

    @DisplayName("isNotDid(String)")
    @ParameterizedTest(name = "{0}")
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

    static Stream<String[]> validVectors() {
        return Arrays.stream(new String[][] {
                {
                        "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                        "key",
                        "z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH"
                },
                {
                        "did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "example",
                        "z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"
                },
                {
                        "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        "key",
                        "1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"
                },
                {
                        "did:web:method:specific:identifier",
                        "web",
                        "method:specific:identifier"
                },
        });
    }
    
    static Stream<String> negativeVectors() {
        return Arrays.stream(new String[] {
                "did:example:123456/path",
                "did:example:123456?versionId=1",
                "did:example:123#public-key-0",
                "did:example:123?service=agent&relativeRef=/credentials#degree",
                "did:example:123?service=files&relativeRef=/resume.pdf",
        });
    }

}
