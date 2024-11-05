package com.apicatalog.jwk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.TestCase;
import com.apicatalog.controller.ControllerDocumentLoader;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.json.JsonLdComparison;
import com.apicatalog.linkedtree.Linkable;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.JsonLdKeyword;
import com.apicatalog.linkedtree.jsonld.io.JsonLdReader;
import com.apicatalog.linkedtree.jsonld.io.JsonLdWriter;
import com.apicatalog.linkedtree.orm.mapper.TreeReaderMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonStructure;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonParser;

@DisplayName("JsonWebKey")
@TestMethodOrder(OrderAnnotation.class)
class JsonWebKeyTest {

    static TreeReaderMapping MAPPING = TreeReaderMapping
            .createBuilder()
            .scan(JsonWebKey.class)
            .build();

    static JsonLdReader READER = JsonLdReader.of(MAPPING, ControllerDocumentLoader.resources());

    static JsonLdWriter WRITER = new JsonLdWriter()
            .scan(JsonWebKey.class);

    static JsonWebKey JWK_1 = GenericJsonWebKey.of(
            URI.create("https://controller.example/#key-1"),
            URI.create("https://controller.example/1"),
            parse(
                    "{\"kid\":\"key-1\",\"kty\":\"EC\",\"crv\":\"P-384\",\"alg\":\"ES384\",\"x\":\"1F14JSzKbwxO-Heqew5HzEt-0NZXAjCu8w-RiuV8_9tMiXrSZdjsWqi4y86OFb5d\",\"y\":\"dnd8yoq-NOJcBuEYgdVVMmSxonXg-DU90d7C4uPWb_Lkd4WIQQEH0DyeC2KUDMIU\"}"),
            parse(
                    "{\"kty\":\"EC\",\"crv\":\"P-384\",\"alg\":\"ES384\",\"d\":\"fGwges0SX1mj4eZamUCL4qtZijy9uT15fI4gKTuRvre4Kkoju2SHM4rlFOeKVraH\",\"x\":\"1F14JSzKbwxO-Heqew5HzEt-0NZXAjCu8w-RiuV8_9tMiXrSZdjsWqi4y86OFb5d\",\"y\":\"dnd8yoq-NOJcBuEYgdVVMmSxonXg-DU90d7C4uPWb_Lkd4WIQQEH0DyeC2KUDMIU\"}"))
            .expires(Instant.parse("2025-12-06T15:41:46Z"))
            .revoked(Instant.parse("2024-10-06T15:41:46Z"));

    static JsonWebKey JWK_2 = GenericJsonWebKey.of(
            URI.create("https://jsonwebkey.example/issuer/123#key-0"),
            URI.create("https://jsonwebkey.example/issuer/123"),
            parse("{\n"
                    + "    \"kty\": \"EC\",\n"
                    + "    \"crv\": \"P-256\",\n"
                    + "    \"x\": \"Ums5WVgwRkRTVVFnU3k5c2xvZllMbEcwM3NPRW91ZzN\",\n"
                    + "    \"y\": \"nDQW6XZ7b_u2Sy9slofYLlG03sOEoug3I0aAPQ0exs4\"\n"
                    + "  }"));

    @DisplayName("Read")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "resources" })
    void read(String name, JsonWebKey expected) throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        JsonObject resource = resource(name).getJsonContent().map(JsonStructure::asJsonObject).orElseThrow();

        JsonWebKey jwk = READER.read(
                JsonWebKey.class,
                resource);

        assertNotNull(jwk);
        assertEquals(expected.id(), jwk.id());
        assertEquals(expected.type(), jwk.type());
        assertEquals(expected.controller(), jwk.controller());
        assertEquals(expected.expires(), jwk.expires());
        assertEquals(expected.revoked(), jwk.revoked());
        assertEquals(expected.publicKey(), jwk.publicKey());
        assertEquals(expected.privateKey(), jwk.privateKey());
        assertTrue(JsonWebKey.equals(expected, jwk));
    }

    @DisplayName("Compact")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "resources" })
    void compact(String name, JsonWebKey input) throws IOException, URISyntaxException {

        var compacted = WRITER.compacted(input);
        assertNotNull(compacted);

        JsonObject expected = resource(name).getJsonContent().map(JsonStructure::asJsonObject).orElseThrow();

        if (!expected.containsKey(JsonLdKeyword.CONTEXT)) {
            expected = Json.createObjectBuilder(expected)
                    .add(JsonLdKeyword.CONTEXT, "https://w3id.org/security/jwk/v1").build();
        }

        if (!JsonLdComparison.equals(compacted, expected)) {
            assertTrue(TestCase.compareJson(name, null, compacted, expected));
            fail();
        }
    }

    @DisplayName("Read & Compact")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "testResources" })
    void readAndCompact(String name, JsonObject expected) throws TreeBuilderError, NodeAdapterError, JsonLdError {

        var jwk = READER.read(JsonWebKey.class, expected);

        var compacted = WRITER.compacted(jwk);
        assertEquals(JsonWebKey.TYPE, jwk.type());

        if (!expected.containsKey(JsonLdKeyword.CONTEXT)) {
            compacted = Json.createObjectBuilder(compacted).remove(JsonLdKeyword.CONTEXT).build();
        }
        
        if (!JsonLdComparison.equals(compacted, expected)) {
            assertTrue(TestCase.compareJson(name, ((Linkable) jwk).ld(), compacted, expected));
            fail();
        }
    }

    static final JsonDocument resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(JsonWebKeyTest.class.getResourceAsStream(name))) {
            return JsonDocument.of(reader.readObject());
        }
    }

    static final Stream<Object[]> testResources() throws IOException, URISyntaxException {
        return TestCase.resources("jwk", ".jsonld");
    }

    static final Stream<Object[]> resources() {
        return Stream.of(new Object[][] {
                new Object[] { "jwk-1.jsonld", JWK_1 },
                new Object[] { "jwk-2.jsonld", JWK_2 },
        });
    }

    static final JsonValue parse(String json) {
        try (JsonParser parser = Json.createParser(new StringReader(json))) {
            parser.next();
            return parser.getValue();
        }
    }
}
