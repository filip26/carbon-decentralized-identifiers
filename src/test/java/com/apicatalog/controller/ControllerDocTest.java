package com.apicatalog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jwk.JsonWebKey;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.io.JsonLdReader;
import com.apicatalog.linkedtree.jsonld.io.JsonLdWriter;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;
import com.apicatalog.multikey.Multikey;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@DisplayName("Controller Document")
@TestMethodOrder(OrderAnnotation.class)
class ControllerDocTest {

    static TreeMapping MAPPING = TreeMapping
            .createBuilder()
            .scan(Multikey.class)
            .scan(JsonWebKey.class)
            .scan(VerificationMethod.class)
            .scan(ControllerDocument.class)
            .build();

    static JsonLdReader READER = JsonLdReader.of(MAPPING, ControllerDocumentLoader.resources());
    
    static JsonLdWriter WRITER = new JsonLdWriter()
            .scan(ControllerDocument.class)
            .scan(Multikey.class)
            .scan(JsonWebKey.class)
            .scan(VerificationMethod.class)
            // context reducer definitions
            .context("https://www.w3.org/ns/controller/v1",
                        List.of("https://w3id.org/security/jwk/v1",
                                "https://w3id.org/security/multikey/v1"));

    @Test
    void read() throws NodeAdapterError, IOException, URISyntaxException, TreeBuilderError, JsonLdError {

        JsonObject input = resource("doc-1.jsonld");

        ControllerDocument doc = READER.read(
                ControllerDocument.class,
                input);

        assertNotNull(doc);
        assertEquals(URI.create("https://controller.example/1"), doc.id());
        assertTrue(doc.type().isEmpty());
        
        assertEquals(1, doc.controller().size());
        assertEquals(URI.create("https://controller.example/1"), doc.controller().iterator().next());

        assertEquals(3, doc.verification().size());

        Iterator<VerificationMethod> mit = doc.verification().iterator();
        
        VerificationMethod method1 = mit.next();
        VerificationMethod method2 = mit.next();
        
        assertTrue(method1 instanceof JsonWebKey);
        assertTrue(method2 instanceof Multikey);
        
        assertTrue(doc.alsoKnownAs().isEmpty());
        assertTrue(doc.assertion().isEmpty());
        assertTrue(doc.authentication().isEmpty());
        assertTrue(doc.capabilityDelegation().isEmpty());
        assertTrue(doc.capabilityInvocation().isEmpty());

    }

    static final JsonObject resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(ControllerDocTest.class.getResourceAsStream(name))) {
            return reader.readObject();
        }
    }
}
