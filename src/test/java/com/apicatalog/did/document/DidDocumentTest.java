package com.apicatalog.did.document;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.io.JsonLdTreeReader;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.codec.KeyCodec;
import com.apicatalog.multikey.Multikey;

import jakarta.json.Json;
import jakarta.json.JsonArray;

@DisplayName("DID Document")
@TestMethodOrder(OrderAnnotation.class)
class DidDocumentTest {

    @Test
    void read() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .scan(DidDocument.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("bsky-ex.jsonld")).get();

        DidDocument doc = reader.read(
                DidDocument.class,
                List.of("https://www.w3.org/ns/did/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("did:plc:7l2pjkp6si2qk6niu2tzloed"), doc.id());
        assertTrue(doc.type().isEmpty());
        
        assertEquals(0, doc.controller().size());

        assertEquals(1, doc.verification().size());
        
        VerificationMethod method = doc.verification().iterator().next();
        assertEquals(URI.create("did:plc:7l2pjkp6si2qk6niu2tzloed#atproto"), method.id());
        assertEquals(URI.create("did:plc:7l2pjkp6si2qk6niu2tzloed"), method.controller());
        assertTrue(method.type().contains("https://w3id.org/security#Multikey"));
        assertTrue(method instanceof Multikey);
        
        Multikey mkey = (Multikey)method;
        
        assertEquals(KeyCodec.SECP256K1_PUBLIC_KEY, mkey.publicKey().codec());
        assertArrayEquals(KeyCodec.SECP256K1_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("zQ3shXbcy44dMdKHV9cwBdFuWgRHoDZxZz9Tn9fZnPTB1CrST")),
                mkey.publicKey().rawBytes());
        assertNull(mkey.privateKey());
        
        assertEquals(Set.of(URI.create("at://filipk.bsky.social")), doc.alsoKnownAs());
        
        assertEquals(1, doc.service().size());
        
        ServiceEndpoint endpoint = doc.service().iterator().next();
        assertEquals(URI.create("#atproto_pds"), endpoint.id());
        assertTrue(endpoint.type().contains("AtprotoPersonalDataServer"));

        assertTrue(doc.assertion().isEmpty());
        assertTrue(doc.authentication().isEmpty());
        assertTrue(doc.capabilityDelegation().isEmpty());
        assertTrue(doc.capabilityInvocation().isEmpty());

    }

    static final JsonDocument resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(DidDocumentTest.class.getResourceAsStream(name))) {
            return JsonDocument.of(reader.readObject());
        }
    }
}
