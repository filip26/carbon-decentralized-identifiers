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
import com.apicatalog.multicodec.codec.KeyCodec;

@DisplayName("DID Key")
@TestMethodOrder(OrderAnnotation.class)
class DidKeyTest {

    @DisplayName("Create DID key from string")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "testVectors" })
    void fromString(DidKeyTestCase testCase) {
        try {

            final DidKey didKey = DidKey.from(testCase.uri);

            if (testCase.negative) {
                fail("Expected failure but got " + didKey);
                return;
            }

            assertNotNull(didKey);
            assertNotNull(didKey.getRawKey());
            assertEquals(testCase.version, didKey.getVersion());
            assertEquals(testCase.codec, didKey.getCodec());
            assertEquals(testCase.keyLength, didKey.getRawKey().length);

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
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkiTBz1ymuepAQ4HEHYSF1H8quG5GLVVQR3djdX3mDooWp",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkjchhfUsD6mmvni8mCdXHw216Xrm9bQe2mBH1P5RDjVJG",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MknGc3ocHs3zdPiJbnaaqDi58NGb4pk1Sp9WxWufuXSdxf",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32
                    ),
            DidKeyTestCase.create(
                    "did:key:z6MkiVQTYk3L2XKY6yg6MyeN2QLE5QkKcXByUeY1dkdiLx4j",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32
                    ),
            DidKeyTestCase.create(
                    "did:key:zQ3shokFTS3brHcDQrn82RUDfCZESWL1ZdCEJwekUDPQiYBme",
                    KeyCodec.SECP256K1_PUBLIC_KEY,
                    33
                    ),
            DidKeyTestCase.create(
                    "did:key:zQ3shtxV1FrJfhqE1dvxYRcCknWNjHc3c5X1y3ZSoPDi2aur2",
                    KeyCodec.SECP256K1_PUBLIC_KEY,
                    33
                    ),
            DidKeyTestCase.create(
                    "did:key:zQ3shZc2QzApp2oymGvQbzP8eKheVshBHbU4ZYjeXqwSKEn6N",
                    KeyCodec.SECP256K1_PUBLIC_KEY,
                    33
                    ),            
            DidKeyTestCase.create(
                    "did:key:zDnaerDaTF5BXEavCrfRZEk316dpbLsfPDZ3WJ5hRTPFU2169",
                    KeyCodec.P256_PUBLIC_KEY,
                    33
                    ),
            DidKeyTestCase.create(
                    "did:key:zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv",
                    KeyCodec.P256_PUBLIC_KEY,
                    33
                    ),
            DidKeyTestCase.create(
                    "did:key:z82Lm1MpAkeJcix9K8TMiLd5NMAhnwkjjCBeWHXyu3U4oT2MVJJKXkcVBgjGhnLBn2Kaau9",
                    KeyCodec.P384_PUBLIC_KEY,
                    49
                    ),
            DidKeyTestCase.create(
                    "did:key:z82LkvCwHNreneWpsgPEbV3gu1C6NFJEBg4srfJ5gdxEsMGRJUz2sG9FE42shbn2xkZJh54",
                    KeyCodec.P384_PUBLIC_KEY,
                    49
                    ),
            DidKeyTestCase.create(
                    "did:key:z2J9gaYxrKVpdoG9A4gRnmpnRCcxU6agDtFVVBVdn1JedouoZN7SzcyREXXzWgt3gGiwpoHq7K68X4m32D8HgzG8wv3sY5j7",
                    KeyCodec.P521_PUBLIC_KEY,
                    67
                    ),
            DidKeyTestCase.create(
                    "did:key:z2J9gcGdb2nEyMDmzQYv2QZQcM1vXktvy1Pw4MduSWxGabLZ9XESSWLQgbuPhwnXN7zP7HpTzWqrMTzaY5zWe6hpzJ2jnw4f",
                    KeyCodec.P521_PUBLIC_KEY,
                    67
                    ),

            // invalid keys
            DidKeyTestCase.create("http:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"),
            DidKeyTestCase.create("did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2"),
            DidKeyTestCase.create(null),

            // versioned keys
            DidKeyTestCase.create(
                    "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32,
                    "1.1"
                    ),
            DidKeyTestCase.create(
                    "did:key:0.7:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                    KeyCodec.ED25519_PUBLIC_KEY,
                    32,
                    "0.7"
                    ),
    };

}
