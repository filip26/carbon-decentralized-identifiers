package com.apicatalog.did;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
    @MethodSource({ "testVectors" })
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

    static Stream<String> testVectors() {
        return Arrays.stream(testCases);
    }

    static final String testCases[] = new String[] {
            "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
            "did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
            "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
            "did:web:method:specific:identifier",
    };

}
