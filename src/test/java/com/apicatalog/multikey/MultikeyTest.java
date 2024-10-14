package com.apicatalog.multikey;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.codec.KeyCodec;

import jakarta.json.Json;
import jakarta.json.JsonArray;

@DisplayName("Multikey")
@TestMethodOrder(OrderAnnotation.class)
class MultikeyTest {

    @Test
    void read() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("multikey-1.jsonld")).get();

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
        assertArrayEquals(KeyCodec.ED25519_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("z6MkmM42vxfqZQsv4ehtTjFFxQ4sQKS2w6WR7emozFAn5cxu")), doc.publicKey().raw());
    }

    static final JsonDocument resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(MultikeyTest.class.getResourceAsStream(name))) {
            return JsonDocument.of(reader.readObject());
        }
    }

    public static void main(String[] args) {

        for (Method method : Multikey.class.getMethods()) {
            System.out.println(method);
        }

    }
}
