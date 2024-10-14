package com.apicatalog.jwk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.io.JsonLdTreeReader;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;

import jakarta.json.Json;
import jakarta.json.JsonArray;

@DisplayName("Multikey")
@TestMethodOrder(OrderAnnotation.class)
class JsonWebKeyTest {

    @Test
    void read1() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(JsonWebKey.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("jwk-1.jsonld")).get();

        JsonWebKey doc = reader.read(
                JsonWebKey.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("https://controller.example/#key-1"), doc.id());
        assertEquals(JsonWebKey.TYPE, doc.type());
        assertEquals(URI.create("https://controller.example/1"), doc.controller());
        
        assertEquals(Instant.parse("2025-12-06T15:41:46Z"), doc.expires());
        assertEquals(Instant.parse("2024-10-06T15:41:46Z"), doc.revoked());
        
        assertEquals(
                "{\"kid\":\"key-1\",\"kty\":\"EC\",\"crv\":\"P-384\",\"alg\":\"ES384\",\"x\":\"1F14JSzKbwxO-Heqew5HzEt-0NZXAjCu8w-RiuV8_9tMiXrSZdjsWqi4y86OFb5d\",\"y\":\"dnd8yoq-NOJcBuEYgdVVMmSxonXg-DU90d7C4uPWb_Lkd4WIQQEH0DyeC2KUDMIU\"}"
                , doc.publicKey().toString());
        assertEquals(
                "{\"kty\":\"EC\",\"crv\":\"P-384\",\"alg\":\"ES384\",\"d\":\"fGwges0SX1mj4eZamUCL4qtZijy9uT15fI4gKTuRvre4Kkoju2SHM4rlFOeKVraH\",\"x\":\"1F14JSzKbwxO-Heqew5HzEt-0NZXAjCu8w-RiuV8_9tMiXrSZdjsWqi4y86OFb5d\",\"y\":\"dnd8yoq-NOJcBuEYgdVVMmSxonXg-DU90d7C4uPWb_Lkd4WIQQEH0DyeC2KUDMIU\"}"
                , doc.privateKey().toString());
        
    }

    static final JsonDocument resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(JsonWebKeyTest.class.getResourceAsStream(name))) {
            return JsonDocument.of(reader.readObject());
        }
    }
}
