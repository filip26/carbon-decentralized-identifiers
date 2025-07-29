package com.apicatalog.did.key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.multicodec.Multicodec;
import com.apicatalog.multicodec.Multicodec.Tag;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.multicodec.codec.KeyCodec;

@DisplayName("DID Key")
@TestMethodOrder(OrderAnnotation.class)
class DidKeyTest {

    MulticodecDecoder CODECS = MulticodecDecoder.getInstance(Tag.Key);

    @DisplayName("of(String)")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "positiveVectors" })
    void ofString(URI uri, int keyLength, String version, Multicodec codec) {
        final DidKey didKey = DidKey.of(uri, CODECS);
        assertEquals(version, didKey.version());
        assertEquals(keyLength, didKey.rawBytes().length);
        assertEquals(codec, didKey.codec());
    }

    @DisplayName("!of(String)")
    @ParameterizedTest
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
                Arguments.of("did:key:z6MkpTHR8VNsBxYAAWHut2Geadd9jSwuBV8xRoAnwWsdvktH",
                        32, "1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:z6MkiTBz1ymuepAQ4HEHYSF1H8quG5GLVVQR3djdX3mDooWp",
                        32, "1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:z6MkjchhfUsD6mmvni8mCdXHw216Xrm9bQe2mBH1P5RDjVJG",
                        32, "1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:z6MknGc3ocHs3zdPiJbnaaqDi58NGb4pk1Sp9WxWufuXSdxf",
                        32, "1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        32, "1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:z6MkiVQTYk3L2XKY6yg6MyeN2QLE5QkKcXByUeY1dkdiLx4j",
                        32, "1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:zQ3shokFTS3brHcDQrn82RUDfCZESWL1ZdCEJwekUDPQiYBme",
                        33, "1", KeyCodec.SECP256K1_PUBLIC_KEY),
                Arguments.of("did:key:zQ3shtxV1FrJfhqE1dvxYRcCknWNjHc3c5X1y3ZSoPDi2aur2",
                        33, "1", KeyCodec.SECP256K1_PUBLIC_KEY),
                Arguments.of("did:key:zQ3shZc2QzApp2oymGvQbzP8eKheVshBHbU4ZYjeXqwSKEn6N",
                        33, "1", KeyCodec.SECP256K1_PUBLIC_KEY),
                Arguments.of("did:key:zDnaerDaTF5BXEavCrfRZEk316dpbLsfPDZ3WJ5hRTPFU2169",
                        33, "1", KeyCodec.P256_PUBLIC_KEY),
                Arguments.of("did:key:zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv",
                        33, "1", KeyCodec.P256_PUBLIC_KEY),
                Arguments.of("did:key:z82Lm1MpAkeJcix9K8TMiLd5NMAhnwkjjCBeWHXyu3U4oT2MVJJKXkcVBgjGhnLBn2Kaau9",
                        49, "1", KeyCodec.P384_PUBLIC_KEY),
                Arguments.of("did:key:z82LkvCwHNreneWpsgPEbV3gu1C6NFJEBg4srfJ5gdxEsMGRJUz2sG9FE42shbn2xkZJh54",
                        49, "1", KeyCodec.P384_PUBLIC_KEY),
                Arguments.of("did:key:z2J9gaYxrKVpdoG9A4gRnmpnRCcxU6agDtFVVBVdn1JedouoZN7SzcyREXXzWgt3gGiwpoHq7K68X4m32D8HgzG8wv3sY5j7",
                        67, "1", KeyCodec.P521_PUBLIC_KEY),
                Arguments.of("did:key:z2J9gcGdb2nEyMDmzQYv2QZQcM1vXktvy1Pw4MduSWxGabLZ9XESSWLQgbuPhwnXN7zP7HpTzWqrMTzaY5zWe6hpzJ2jnw4f",
                        67, "1", KeyCodec.P521_PUBLIC_KEY),
                Arguments.of("did:key:z6LSeu9HkTHSfLLeUs2nnzUSNedgDUevfNQgQjQC23ZCit6F",
                        32, "1", KeyCodec.X25519_PUBLIC_KEY),
                Arguments.of("did:key:z6LStiZsmxiK4odS4Sb6JmdRFuJ6e1SYP157gtiCyJKfrYha",
                        32, "1", KeyCodec.X25519_PUBLIC_KEY),
                Arguments.of("did:key:z6LSoMdmJz2Djah2P4L9taDmtqeJ6wwd2HhKZvNToBmvaczQ",
                        32, "1", KeyCodec.X25519_PUBLIC_KEY),
                Arguments.of("did:key:zUC7K4ndUaGZgV7Cp2yJy6JtMoUHY6u7tkcSYUvPrEidqBmLCTLmi6d5WvwnUqejscAkERJ3bfjEiSYtdPkRSE8kSa11hFBr4sTgnbZ95SJj19PN2jdvJjyzpSZgxkyyxNnBNnY",
                        96, "1", KeyCodec.BLS12_381_G2_PUBLIC_KEY),
                Arguments.of("did:key:zUC7KKoJk5ttwuuc8pmQDiUmtckEPTwcaFVZe4DSFV7fURuoRnD17D3xkBK3A9tZqdADkTTMKSwNkhjo9Hs6HfgNUXo48TNRaxU6XPLSPdRgMc15jCD5DfN34ixjoVemY62JxnW",
                        96, "1", KeyCodec.BLS12_381_G2_PUBLIC_KEY),
                Arguments.of(
                        "did:key:z4MXj1wBzi9jUstyPMS4jQqB6KdJaiatPkAtVtGc6bQEQEEsKTic4G7Rou3iBf9vPmT5dbkm9qsZsuVNjq8HCuW1w24nhBFGkRE4cd2Uf2tfrB3N7h4mnyPp1BF3ZttHTYv3DLUPi1zMdkULiow3M1GfXkoC6DoxDUm1jmN6GBj22SjVsr6dxezRVQc7aj9TxE7JLbMH1wh5X3kA58H3DFW8rnYMakFGbca5CB2Jf6CnGQZmL7o5uJAdTwXfy2iiiyPxXEGerMhHwhjTA1mKYobyk2CpeEcmvynADfNZ5MBvcCS7m3XkFCMNUYBS9NQ3fze6vMSUPsNa6GVYmKx2x6JrdEjCk3qRMMmyjnjCMfR4pXbRMZa3i",
                        270, "1", KeyCodec.RSA_PUBLIC_KEY),
                Arguments.of(
                        "did:key:zgghBUVkqmWS8e1ioRVp2WN9Vw6x4NvnE9PGAyQsPqM3fnfPf8EdauiRVfBTcVDyzhqM5FFC7ekAvuV1cJHawtfgB9wDcru1hPDobk3hqyedijhgWmsYfJCmodkiiFnjNWATE7PvqTyoCjcmrc8yMRXmFPnoASyT5beUd4YZxTE9VfgmavcPy3BSouNmASMQ8xUXeiRwjb7xBaVTiDRjkmyPD7NYZdXuS93gFhyDFr5b3XLg7Rfj9nHEqtHDa7NmAX7iwDAbMUFEfiDEf9hrqZmpAYJracAjTTR8Cvn6mnDXMLwayNG8dcsXFodxok2qksYF4D8ffUxMRmyyQVQhhhmdSi4YaMPqTnC1J6HTG9Yfb98yGSVaWi4TApUhLXFow2ZvB6vqckCNhjCRL2R4MDUSk71qzxWHgezKyDeyThJgdxydrn1osqH94oSeA346eipkJvKqYREXBKwgB5VL6WF4qAK6sVZxJp2dQBfCPVZ4EbsBQaJXaVK7cNcWG8tZBFWZ79gG9Cu6C4u8yjBS8Ux6dCcJPUTLtixQu4z2n5dCsVSNdnP1EEs8ZerZo5pBgc68w4Yuf9KL3xVxPnAB1nRCBfs9cMU6oL1EdyHbqrTfnjE8HpY164akBqe92LFVsk8RusaGsVPrMekT8emTq5y8v8CabuZg5rDs3f9NPEtogjyx49wiub1FecM5B7QqEcZSYiKHgF4mfkteT2",
                        526, "1", KeyCodec.RSA_PUBLIC_KEY),
                // versioned keys
                Arguments.of("did:key:1.1:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        32, "1.1", KeyCodec.ED25519_PUBLIC_KEY),
                Arguments.of("did:key:0.7:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        32, "0.7", KeyCodec.ED25519_PUBLIC_KEY));
    }

    static Stream<Arguments> negativeVectors() {
        return Stream.of(
                Arguments.of("http:key:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        0, null),
                Arguments.of("did:example:z6MkicdicToW5HbxPP7zZV1H7RHvXgRMhoujWAF2n5WQkdd2",
                        0, null),
                Arguments.of(null, 0, null),
                Arguments.of("", 0, null),
                Arguments.of("did", 0, null),
                Arguments.of("did:key", 0, null),
                Arguments.of("did:key:", 0, null),
                Arguments.of("did:key:y", 0, null),
                Arguments.of("did:key:z6x", 0, null));
    }
}
