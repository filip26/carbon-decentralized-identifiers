package com.apicatalog.did.document;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.apicatalog.controller.Service;
import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jwk.JsonWebKey;
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
    void read1() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .scan(VerificationMethod.class)
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

        Multikey mkey = (Multikey) method;

        assertEquals(KeyCodec.SECP256K1_PUBLIC_KEY, mkey.publicKey().codec());
        assertArrayEquals(KeyCodec.SECP256K1_PUBLIC_KEY.decode(Multibase.BASE_58_BTC.decode("zQ3shXbcy44dMdKHV9cwBdFuWgRHoDZxZz9Tn9fZnPTB1CrST")),
                mkey.publicKey().rawBytes());
        assertNull(mkey.privateKey());

        assertEquals(Set.of(URI.create("at://filipk.bsky.social")), doc.alsoKnownAs());

        assertEquals(1, doc.service().size());

        Service service = doc.service().iterator().next();
        assertEquals(URI.create("#atproto_pds"), service.id());
        assertTrue(service.type().contains("AtprotoPersonalDataServer"));
        assertEquals(1, service.endpoint().size());
        assertEquals(URI.create("https://enoki.us-east.host.bsky.network"), service.endpoint().iterator().next().id());

        assertTrue(doc.assertion().isEmpty());
        assertTrue(doc.authentication().isEmpty());
        assertTrue(doc.capabilityDelegation().isEmpty());
        assertTrue(doc.capabilityInvocation().isEmpty());
    }

    @Test
    void read2() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        TreeMapping mapping = TreeMapping
                .createBuilder()
                .scan(Multikey.class)
                .scan(JsonWebKey.class)
                .scan(VerificationMethod.class)
                .scan(DidDocument.class)
                .build();

        JsonLdTreeReader reader = JsonLdTreeReader.of(mapping);

        JsonArray input = JsonLd.expand(resource("doc-1.jsonld")).get();

        DidDocument doc = reader.read(
                DidDocument.class,
                List.of("https://www.w3.org/ns/did/v1", "https://w3id.org/security/jwk/v1"),
                input);

        assertNotNull(doc);
        assertEquals(URI.create("did:web:example.com"), doc.id());
        assertTrue(doc.type().isEmpty());

        assertEquals(0, doc.controller().size());

        assertEquals(3, doc.verification().size());

        Iterator<VerificationMethod> mit = doc.verification().iterator();
        
        VerificationMethod vm1 = mit.next();
        assertEquals(URI.create("did:web:example.com#key-0"), vm1.id());
        assertEquals(URI.create("did:web:example.com"), vm1.controller());
        assertTrue(vm1.type().contains("https://w3id.org/security#JsonWebKey"));
        assertTrue(vm1 instanceof JsonWebKey);
        assertNotNull(((JsonWebKey)vm1).publicKey());

        VerificationMethod vm2 = mit.next();
        assertEquals(URI.create("did:web:example.com#key-1"), vm2.id());
        assertEquals(URI.create("did:web:example.com"), vm2.controller());
        assertTrue(vm2.type().contains("https://w3id.org/security#JsonWebKey"));
        assertTrue(vm2 instanceof JsonWebKey);
        assertNotNull(((JsonWebKey)vm2).publicKey());

        VerificationMethod vm3 = mit.next();
        assertEquals(URI.create("did:web:example.com#key-2"), vm3.id());
        assertEquals(URI.create("did:web:example.com"), vm3.controller());
        assertTrue(vm3.type().contains("https://w3id.org/security#JsonWebKey"));
        assertTrue(vm3 instanceof JsonWebKey);
        assertNotNull(((JsonWebKey)vm3).publicKey());

        assertEquals(0, doc.alsoKnownAs().size());

        assertEquals(0, doc.service().size());
        
        assertEquals(2, doc.assertion().size());
        
        Iterator<VerificationMethod> sit = doc.assertion().iterator();
        VerificationMethod sm1 = sit.next();
        VerificationMethod sm2 = sit.next();
        
        assertEquals(vm1.id(), sm1.id());
        assertEquals(vm1.controller(), sm1.controller());
        assertNotNull(((JsonWebKey)sm1).publicKey());

        assertEquals(vm3.id(), sm2.id());
        assertEquals(vm3.controller(), sm2.controller());
        assertNotNull(((JsonWebKey)sm2).publicKey());

        assertEquals(2, doc.authentication().size());
        
        Iterator<VerificationMethod> ait = doc.authentication().iterator();
        VerificationMethod am1 = ait.next();
        VerificationMethod am2 = ait.next();
        
        assertEquals(vm1.id(), am1.id());
        assertEquals(vm1.controller(), am1.controller());
        assertNotNull(((JsonWebKey)am1).publicKey());

        assertEquals(vm2.id(), am2.id());
        assertEquals(vm2.controller(), am2.controller());
        assertNotNull(((JsonWebKey)am2).publicKey());
        
        assertEquals(2, doc.keyAgreement().size());

        Iterator<VerificationMethod> kit = doc.keyAgreement().iterator();
        VerificationMethod km1 = kit.next();
        VerificationMethod km2 = kit.next();
        
        assertEquals(vm2.id(), km1.id());
        assertEquals(vm2.controller(), km1.controller());
        assertNotNull(((JsonWebKey)km1).publicKey());

        assertEquals(vm3.id(), km2.id());
        assertEquals(vm3.controller(), am2.controller());
        assertNotNull(((JsonWebKey)km2).publicKey());

        
        assertTrue(doc.capabilityDelegation().isEmpty());
        assertTrue(doc.capabilityInvocation().isEmpty());
    }

    static final JsonDocument resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(DidDocumentTest.class.getResourceAsStream(name))) {
            return JsonDocument.of(reader.readObject());
        }
    }
}
