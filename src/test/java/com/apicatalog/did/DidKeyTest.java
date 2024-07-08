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
import com.apicatalog.multibase.MultibaseDecoder;

@DisplayName("DID Key")
@TestMethodOrder(OrderAnnotation.class)
class DidKeyTest {

    @DisplayName("Create DID key from string")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "testVectors" })
    void fromString(DidKeyTestCase testCase) {
        try {

            final DidKey didKey = DidKey.from(testCase.uri, MultibaseDecoder.getInstance());

            if (testCase.negative) {
                fail("Expected failure but got " + didKey);
                return;
            }

            assertNotNull(didKey);
            assertNotNull(didKey.getKey());
            assertEquals(testCase.version, didKey.getVersion());
            assertEquals(testCase.keyLength, didKey.getKey().length);

        } catch (IllegalArgumentException | NullPointerException e) {
            if (!testCase.negative) {
                e.printStackTrace();
                fail(e);
            }
        }
    }

    static Stream<DidKeyTestCase> testVectors() {
        return Arrays.stream(testCases);
    }

    static final DidKeyTestCase testCases[] = new DidKeyTestCase[] {
            DidKeyTestCase.create(
                    "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                    34
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkiTBz1ymuepAQ4HEHYSF1H8quG5GLVVQR3djdX3mDooWp",
                    34
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkjchhfUsD6mmvni8mCdXHw216Xrm9bQe2mBH1P5RDjVJG",
                    34
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MknGc3ocHs3zdPiJbnaaqDi58NGb4pk1Sp9WxWufuXSdxf",
                    34
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                    34
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkiVQTYk3L2XKY6yg6MyeN2QLE5QkKcXByUeY1dkdiLx4j",
                    34
                    ),
            DidKeyTestCase.create(
                    "did:key:zQ3shokFTS3brHcDQrn82RUDfCZESWL1ZdCEJwekUDPQiYBme",
                    35
                    ),
            DidKeyTestCase.create(
                    "did:key:zQ3shtxV1FrJfhqE1dvxYRcCknWNjHc3c5X1y3ZSoPDi2aur2",
                    35
                    ),
            DidKeyTestCase.create(
                    "did:key:zQ3shZc2QzApp2oymGvQbzP8eKheVshBHbU4ZYjeXqwSKEn6N",
                    35
                    ),            
            DidKeyTestCase.create(
                    "did:key:zDnaerDaTF5BXEavCrfRZEk316dpbLsfPDZ3WJ5hRTPFU2169",
                    35
                    ),
            DidKeyTestCase.create(
                    "did:key:zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv",
                    35
                    ),
            DidKeyTestCase.create(
                    "did:key:z82Lm1MpAkeJcix9K8TMiLd5NMAhnwkjjCBeWHXyu3U4oT2MVJJKXkcVBgjGhnLBn2Kaau9",
                    51
                    ),
            DidKeyTestCase.create(
                    "did:key:z82LkvCwHNreneWpsgPEbV3gu1C6NFJEBg4srfJ5gdxEsMGRJUz2sG9FE42shbn2xkZJh54",
                    51
                    ),
            DidKeyTestCase.create(
                    "did:key:z2J9gaYxrKVpdoG9A4gRnmpnRCcxU6agDtFVVBVdn1JedouoZN7SzcyREXXzWgt3gGiwpoHq7K68X4m32D8HgzG8wv3sY5j7",
                    69
                    ),
            DidKeyTestCase.create(
                    "did:key:z2J9gcGdb2nEyMDmzQYv2QZQcM1vXktvy1Pw4MduSWxGabLZ9XESSWLQgbuPhwnXN7zP7HpTzWqrMTzaY5zWe6hpzJ2jnw4f",
                    69
                    ),

            // invalid keys
            DidKeyTestCase.create("http:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"),
            DidKeyTestCase.create("did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"),
            DidKeyTestCase.create(null),

            // versioned keys
            DidKeyTestCase.create(
                    "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                    34,
                    "1.1"
                    ),
            DidKeyTestCase.create(
                    "did:key:0.7:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                    34,
                    "0.7"
                    ),
    };

}
