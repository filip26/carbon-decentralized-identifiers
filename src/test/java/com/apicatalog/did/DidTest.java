package com.apicatalog.did;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.did.key.DidKey;
import com.apicatalog.did.key.DidKeyTestCase;
import com.apicatalog.multibase.MultibaseDecoder;

@DisplayName("DID")
@TestMethodOrder(OrderAnnotation.class)
class DidTest {

    @DisplayName("Create DID from string")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "stringValidVectors" })
    void fromString(String input) {
        try {

            final Did did = Did.from(input);

            assertNotNull(did);
//            assertNotNull(didKey.getKey());
//            assertEquals(testCase.version, didKey.getVersion());
//            assertEquals(testCase.keyLength, didKey.getKey().length);

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @DisplayName("Create DID from URI")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "uriValidVectors" })
    void fromUri(URI input) {
        try {

            final Did did = Did.from(input);

            assertNotNull(did);
//            assertNotNull(didKey.getKey());
//            assertEquals(testCase.version, didKey.getVersion());
//            assertEquals(testCase.keyLength, didKey.getKey().length);

        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            fail(e);
        }
    }

    @DisplayName("isDid(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "stringValidVectors" })
    void isDid(String input) {
        assertTrue(Did.isDid(input));
    }

    @DisplayName("isDid(URI)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "uriValidVectors" })
    void isDid(URI input) {
        assertTrue(Did.isDid(input));
    }

    static Stream<String> stringValidVectors() {
        return Arrays.stream(new String[] {
                "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                "did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                "did:web:method:specific:identifier",
        });
    }

    static Stream<URI> uriValidVectors() {
        return stringValidVectors().map(URI::create);
    }

}
