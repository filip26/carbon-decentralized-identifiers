package com.apicatalog.controller.method;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jwk.JsonWebKey;
import com.apicatalog.linkedtree.Linkable;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.io.JsonLdTreeReader;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.codec.KeyCodec;
import com.apicatalog.multikey.Multikey;

import jakarta.json.Json;
import jakarta.json.JsonArray;

@TestMethodOrder(OrderAnnotation.class)
class VerificationMethodTest {

    @Test
    void read() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(JsonWebKey.class)
                .scan(Multikey.class)
                .scan(VerificationMethod.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("../../jwk/jwk-1.jsonld")).get();

        VerificationMethod doc = reader.read(
                VerificationMethod.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertTrue(doc instanceof JsonWebKey);
        assertEquals(URI.create("https://controller.example/#key-1"), doc.id());
        assertEquals(JsonWebKey.TYPE, doc.type());
        assertEquals(URI.create("https://controller.example/1"), doc.controller());

        assertEquals(Instant.parse("2025-12-06T15:41:46Z"), doc.expires());
        assertEquals(Instant.parse("2024-10-06T15:41:46Z"), doc.revoked());

        assertEquals(
                "{\"kid\":\"key-1\",\"kty\":\"EC\",\"crv\":\"P-384\",\"alg\":\"ES384\",\"x\":\"1F14JSzKbwxO-Heqew5HzEt-0NZXAjCu8w-RiuV8_9tMiXrSZdjsWqi4y86OFb5d\",\"y\":\"dnd8yoq-NOJcBuEYgdVVMmSxonXg-DU90d7C4uPWb_Lkd4WIQQEH0DyeC2KUDMIU\"}",
                ((JsonWebKey)doc).publicKey().toString());
        assertEquals(
                "{\"kty\":\"EC\",\"crv\":\"P-384\",\"alg\":\"ES384\",\"d\":\"fGwges0SX1mj4eZamUCL4qtZijy9uT15fI4gKTuRvre4Kkoju2SHM4rlFOeKVraH\",\"x\":\"1F14JSzKbwxO-Heqew5HzEt-0NZXAjCu8w-RiuV8_9tMiXrSZdjsWqi4y86OFb5d\",\"y\":\"dnd8yoq-NOJcBuEYgdVVMmSxonXg-DU90d7C4uPWb_Lkd4WIQQEH0DyeC2KUDMIU\"}",
                ((JsonWebKey)doc).privateKey().toString());

    }

    @Test
    void read2() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(JsonWebKey.class)
                .scan(Multikey.class)
                .scan(VerificationMethod.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("../../multikey/multikey-3.jsonld")).get();

        VerificationMethod doc = reader.read(
                VerificationMethod.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("https://controller.example/123#keys-2"), doc.id());
        assertEquals(Multikey.TYPE, doc.type());
        assertEquals(URI.create("https://controller.example/123"), doc.controller());
        assertNull(((Multikey)doc).privateKey());
        assertNull(doc.expires());
        assertNull(doc.revoked());
        assertEquals(KeyCodec.P256_PUBLIC_KEY, ((Multikey)doc).publicKey().codec());
        assertEquals(KeyCodec.P256_PUBLIC_KEY.name(), ((Multikey)doc).publicKey().type());
        assertArrayEquals(KeyCodec.P256_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv")), ((Multikey)doc).publicKey().rawBytes());
    }
    
    @Test
    void read3() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(VerificationMethod.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("../../jwk/jwk-1.jsonld")).get();

        VerificationMethod doc = reader.read(
                VerificationMethod.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertTrue(doc instanceof Linkable);
        assertEquals(URI.create("https://controller.example/#key-1"), doc.id());
        assertEquals(JsonWebKey.TYPE, ((Linkable)doc).ld().asFragment().type().iterator().next());
        assertEquals(URI.create("https://controller.example/1"), doc.controller());
    }
    
    static final JsonDocument resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(VerificationMethodTest.class.getResourceAsStream(name))) {
            return JsonDocument.of(reader.readObject());
        }
    }
}
