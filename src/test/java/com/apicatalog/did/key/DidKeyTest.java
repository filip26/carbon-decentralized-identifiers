package com.apicatalog.did.key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.multicodec.Multicodec.Tag;
import com.apicatalog.multicodec.MulticodecDecoder;

@DisplayName("DID Key")
@TestMethodOrder(OrderAnnotation.class)
class DidKeyTest {

    MulticodecDecoder CODECS = MulticodecDecoder.getInstance(Tag.Key);

    @DisplayName("of(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void ofString(URI uri, int keyLength, String version) {
        final DidKey didKey = DidKey.of(uri, CODECS);
        assertNotNull(didKey);
        assertNotNull(didKey.raw());
        assertEquals(version, didKey.version());
        assertEquals(keyLength, didKey.raw().length);
    }

    @DisplayName("!of(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "negativeVectors" })
    void ofStringNegative(URI uri, int keyLength, String version) {
        try {

            final DidKey didKey = DidKey.of(uri, CODECS);

            fail("Expected failure but got " + didKey);

        } catch (IllegalArgumentException | NullPointerException e) {
        }
    }

    static Stream<Arguments> positiveVectors() {
        return Stream.of(
                Arguments.of(
                        "did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                        32, "1"),
                Arguments.of(
                        "did:key:z6MkiTBz1ymuepAQ4HEHYSF1H8quG5GLVVQR3djdX3mDooWp",
                        32, "1"),
                Arguments.of(
                        "did:key:z6MkjchhfUsD6mmvni8mCdXHw216Xrm9bQe2mBH1P5RDjVJG",
                        32, "1"),
                Arguments.of(
                        "did:key:z6MknGc3ocHs3zdPiJbnaaqDi58NGb4pk1Sp9WxWufuXSdxf",
                        32, "1"),
                Arguments.of(
                        "did:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        32, "1"),
                Arguments.of(
                        "did:key:z6MkiVQTYk3L2XKY6yg6MyeN2QLE5QkKcXByUeY1dkdiLx4j",
                        32, "1"),
                Arguments.of(
                        "did:key:zQ3shokFTS3brHcDQrn82RUDfCZESWL1ZdCEJwekUDPQiYBme",
                        33, "1"),
                Arguments.of(
                        "did:key:zQ3shtxV1FrJfhqE1dvxYRcCknWNjHc3c5X1y3ZSoPDi2aur2",
                        33, "1"),
                Arguments.of(
                        "did:key:zQ3shZc2QzApp2oymGvQbzP8eKheVshBHbU4ZYjeXqwSKEn6N",
                        33, "1"),
                Arguments.of(
                        "did:key:zDnaerDaTF5BXEavCrfRZEk316dpbLsfPDZ3WJ5hRTPFU2169",
                        33, "1"),
                Arguments.of(
                        "did:key:zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv",
                        33, "1"),
                Arguments.of(
                        "did:key:z82Lm1MpAkeJcix9K8TMiLd5NMAhnwkjjCBeWHXyu3U4oT2MVJJKXkcVBgjGhnLBn2Kaau9",
                        49, "1"),
                Arguments.of(
                        "did:key:z82LkvCwHNreneWpsgPEbV3gu1C6NFJEBg4srfJ5gdxEsMGRJUz2sG9FE42shbn2xkZJh54",
                        49, "1"),
                Arguments.of(
                        "did:key:z2J9gaYxrKVpdoG9A4gRnmpnRCcxU6agDtFVVBVdn1JedouoZN7SzcyREXXzWgt3gGiwpoHq7K68X4m32D8HgzG8wv3sY5j7",
                        67, "1"),
                Arguments.of(
                        "did:key:z2J9gcGdb2nEyMDmzQYv2QZQcM1vXktvy1Pw4MduSWxGabLZ9XESSWLQgbuPhwnXN7zP7HpTzWqrMTzaY5zWe6hpzJ2jnw4f",
                        67, "1"),
                // versioned keys
                Arguments.of(
                        "did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        32, "1.1"),
                Arguments.of(
                        "did:key:0.7:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        32, "0.7"));
    }

    static Stream<Arguments> negativeVectors() {
        return Stream.of(
                Arguments.of("http:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2", 0,
                        null),
                Arguments.of("did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2", 0, null),
                Arguments.of(null, 0, null));
    }
}
