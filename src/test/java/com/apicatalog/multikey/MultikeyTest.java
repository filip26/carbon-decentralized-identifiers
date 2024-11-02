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
import com.apicatalog.linkedtree.jsonld.io.JsonLdWriter;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.codec.KeyCodec;
import com.apicatalog.multicodec.key.GenericMulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKey;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@DisplayName("Multikey")
@TestMethodOrder(OrderAnnotation.class)
class MultikeyTest {

    static TreeMapping MAPPING = TreeMapping
            .createBuilder()
            .scan(Multikey.class)
            .build();

    static JsonLdTreeReader READER = JsonLdTreeReader.of(MAPPING);

    static JsonLdWriter WRITER = new JsonLdWriter()
            .scan(Multikey.class);

    static Multikey MULTIKEY_1 = GenericMultikey.of(
            URI.create("https://controller.example/123456789abcdefghi#keys-1"),
            URI.create("https://controller.example/123456789abcdefghi"),
            new GenericMulticodecKey(
                    KeyCodec.ED25519_PUBLIC_KEY,
                    KeyCodec.ED25519_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("z6MkmM42vxfqZQsv4ehtTjFFxQ4sQKS2w6WR7emozFAn5cxu"))));

    static Multikey MULTIKEY_4 = GenericMultikey.of(
            URI.create("https://controller.example/#keys-4"),
            URI.create("https://controller.example/4"),
            new GenericMulticodecKey(
                    KeyCodec.P256_PUBLIC_KEY,
                    KeyCodec.P256_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("zDnaerx9CtbPJ1q36T5Ln5wYt3MQYeGRG5ehnPAmxcf5mDZpv"))),
            new GenericMulticodecKey(
                    KeyCodec.P256_PRIVATE_KEY,
                    KeyCodec.P256_PRIVATE_KEY.decode(Multibase.BASE_58_BTC.decode("z42twTcNeSYcnqg1FLuSFs2bsGH3ZqbRHFmvS9XMsYhjxvHN"))))
            .expires(Instant.parse("2025-12-06T15:41:46Z"))
            .revoked(Instant.parse("2024-10-06T15:41:46Z"));

    @Test
    void read3() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        JsonArray input = JsonLd.expand(TestCase.resource("multikey/multikey-3.jsonld")).get();

        Multikey doc = READER.read(
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

    @DisplayName("Read")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "resources" })
    void read(String name, Multikey expected) throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        JsonArray input = JsonLd.expand(TestCase.resource("multikey/" + name)).get();

        Multikey multikey = READER.read(Multikey.class, input);

        assertNotNull(multikey);
        assertEquals(expected.id(), multikey.id());
        assertEquals(expected.type(), multikey.type());
        assertEquals(expected.controller(), multikey.controller());

        assertEquals(expected.expires(), multikey.expires());
        assertEquals(expected.revoked(), multikey.revoked());

        if (expected.publicKey() != null) {
            assertEquals(expected.publicKey().codec(), multikey.publicKey().codec());
            assertEquals(expected.publicKey().type(), multikey.publicKey().type());
            assertArrayEquals(expected.publicKey().rawBytes(), multikey.publicKey().rawBytes());
        }
        assertTrue(MulticodecKey.equals(expected.publicKey(), multikey.publicKey()));

        if (expected.privateKey() != null) {
            assertEquals(expected.privateKey().codec(), multikey.privateKey().codec());
            assertEquals(expected.privateKey().type(), multikey.privateKey().type());
            assertArrayEquals(expected.privateKey().rawBytes(), multikey.privateKey().rawBytes());
        }

        assertTrue(MulticodecKey.equals(expected.privateKey(), multikey.privateKey()));

        assertTrue(Multikey.equals(expected, multikey));
    }

    @Test
    void writeInstance() throws IOException, URISyntaxException {

        var compacted = WRITER.compacted(MULTIKEY_4);
        assertNotNull(compacted);

        var name = "multikey-4.jsonld";
        var doc = TestCase.resource("multikey/multikey-4.jsonld").getJsonContent().orElseThrow();

        if (!JsonLdComparison.equals(compacted, doc)) {
            assertTrue(TestCase.compareJson(name, null, compacted, doc));
            fail();
        }
    }

    @DisplayName("Compacted Write")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "testResources" })
    void writeCompacted(String name, JsonObject doc) throws TreeBuilderError, NodeAdapterError, JsonLdError {

        JsonArray input = JsonLd.expand(JsonDocument.of(doc)).get();

        var multikey = READER.read(Multikey.class, input);

        var compacted = WRITER.compacted(multikey);
        assertEquals(Multikey.TYPE, multikey.type());

        if (!JsonLdComparison.equals(compacted, doc)) {
            assertTrue(TestCase.compareJson(name, ((Linkable) multikey).ld(), compacted, doc));
            fail();
        }
    }

    static final Stream<Object[]> testResources() throws IOException, URISyntaxException {
        return TestCase.resources("multikey", ".jsonld");
    }

    static final Stream<Object[]> resources() {
        return Stream.of(new Object[][] {
                new Object[] { "multikey-1.jsonld", MULTIKEY_1 },
                new Object[] { "multikey-4.jsonld", MULTIKEY_4 },
        });
    }
}
