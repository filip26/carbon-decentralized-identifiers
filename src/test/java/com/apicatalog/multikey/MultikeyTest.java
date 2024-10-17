package com.apicatalog.multikey;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.TestCase;
import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.json.JsonLdComparison;
import com.apicatalog.linkedtree.Linkable;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.io.JsonLdTreeReader;
import com.apicatalog.linkedtree.jsonld.io.JsonLdTreeWriter;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.codec.KeyCodec;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@DisplayName("Multikey")
@TestMethodOrder(OrderAnnotation.class)
class MultikeyTest {

    static JsonLdTreeWriter WRITER = new JsonLdTreeWriter();

    @Test
    void read1() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(TestCase.resource("multikey/multikey-1.jsonld")).get();

        Multikey doc = reader.read(
                Multikey.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("https://controller.example/123456789abcdefghi#keys-1"), doc.id());
        assertEquals(Multikey.TYPE, doc.type());
        assertEquals(URI.create("https://controller.example/123456789abcdefghi"), doc.controller());
        assertNull(doc.privateKey());
        assertNull(doc.expires());
        assertNull(doc.revoked());
        assertEquals(KeyCodec.ED25519_PUBLIC_KEY, doc.publicKey().codec());
        assertEquals(KeyCodec.ED25519_PUBLIC_KEY.name(), doc.publicKey().type());
        assertArrayEquals(KeyCodec.ED25519_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("z6MkmM42vxfqZQsv4ehtTjFFxQ4sQKS2w6WR7emozFAn5cxu")), doc.publicKey().rawBytes());
    }

    @Test
    void read3() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(TestCase.resource("multikey/multikey-3.jsonld")).get();

        Multikey doc = reader.read(
                Multikey.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("https://controller.example/123#keys-2"), doc.id());
        assertEquals(Multikey.TYPE, doc.type());
        assertEquals(URI.create("https://controller.example/123"), doc.controller());
        assertNull(doc.privateKey());
        assertNull(doc.expires());
        assertNull(doc.revoked());
        assertEquals(KeyCodec.P256_PUBLIC_KEY, doc.publicKey().codec());
        assertEquals(KeyCodec.P256_PUBLIC_KEY.name(), doc.publicKey().type());
        assertArrayEquals(KeyCodec.P256_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv")), doc.publicKey().rawBytes());
    }

    @Test
    void read4() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(TestCase.resource("multikey/multikey-4.jsonld")).get();

        Multikey doc = reader.read(
                Multikey.class,
                List.of("https://www.w3.org/ns/controller/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("https://controller.example/#keys-4"), doc.id());
        assertEquals(Multikey.TYPE, doc.type());
        assertEquals(URI.create("https://controller.example/4"), doc.controller());

        assertEquals(Instant.parse("2025-12-06T15:41:46Z"), doc.expires());
        assertEquals(Instant.parse("2024-10-06T15:41:46Z"), doc.revoked());

        assertEquals(KeyCodec.P256_PUBLIC_KEY, doc.publicKey().codec());
        assertEquals(KeyCodec.P256_PUBLIC_KEY.name(), doc.publicKey().type());
        assertArrayEquals(KeyCodec.P256_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv")), doc.publicKey().rawBytes());

        assertEquals(KeyCodec.P256_PRIVATE_KEY, doc.privateKey().codec());
        assertEquals(KeyCodec.P256_PRIVATE_KEY.name(), doc.privateKey().type());
        assertArrayEquals(KeyCodec.P256_PRIVATE_KEY.decode(Multibase.BASE_58_BTC.decode("z42twTcNeSYcnqg1FLuSFs2bsGH3ZqbRHFmvS9XMsYhjxvHN")), doc.privateKey().rawBytes());
    }

    @DisplayName("Read/Write")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "testResources" })
    void readWrite(String name, JsonObject doc) throws TreeBuilderError, NodeAdapterError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(JsonDocument.of(doc)).get();

        var multikey = reader.read(Multikey.class, input);
        
        assertNotNull(multikey.id());
        assertNotNull(multikey.controller());
        assertEquals(Multikey.TYPE, multikey.type());
        assertNotNull(multikey.publicKey().rawBytes());
        assertNotNull(multikey.publicKey().codec());

        var output = WRITER.write(((Linkable)multikey).ld().asFragment().root());

        assertNotNull(output);

        JsonObject compacted = JsonLd.compact(JsonDocument.of(output),
                JsonDocument.of(
                Json.createArrayBuilder().add(
                "https://w3id.org/security/multikey/v1").build())
                
                ).get();
        if (!JsonLdComparison.equals(compacted, doc)) {
            assertTrue(TestCase.compareJson(name, ((Linkable)multikey).ld(), compacted, doc));
            fail();
        }

    }

    static final Stream<Object[]> testResources() throws IOException, URISyntaxException {
        return TestCase.resources("multikey", ".jsonld");
    }

}
