package com.apicatalog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.TestCase;
import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.json.JsonLdComparison;
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
        VerificationMethod method3 = mit.next();
        
        assertTrue(method1 instanceof JsonWebKey);
        assertTrue(method2 instanceof Multikey);

        assertEquals(URI.create("https://controller.example/123456789abcdefghi#keys-2"), method3.id());
        assertEquals("https://example.com/superkey", method3.type());
        
        assertTrue(doc.alsoKnownAs().isEmpty());
        
        assertEquals(2, doc.assertion().size());
        
        Iterator<VerificationMethod> ait = doc.assertion().iterator();
        
        VerificationMethod assertion1 = ait.next();
        VerificationMethod assertion2 = ait.next();
        assertNotNull(assertion1);
        assertNotNull(assertion2);
        assertTrue(assertion1 instanceof JsonWebKey);
        assertTrue(assertion2 instanceof Multikey);
        
        assertEquals(1, doc.authentication().size());

        VerificationMethod authentication = doc.authentication().iterator().next();
        assertNotNull(authentication);
        assertTrue(authentication instanceof Multikey);
        
        assertTrue(doc.capabilityDelegation().isEmpty());
        assertTrue(doc.capabilityInvocation().isEmpty());
    }

    @DisplayName("Read & Compact")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "testResources" })
    void readAndCompact(String name, JsonObject expected) throws TreeBuilderError, NodeAdapterError, JsonLdError {

        var doc = READER.read(ControllerDocument.class, expected);

        var compacted = WRITER.compacted(doc);

        if (!JsonLdComparison.equals(compacted, expected)) {
            assertTrue(TestCase.compareJson(name, null, compacted, expected));
            fail();
        }
    }

    static final Stream<Object[]> testResources() throws IOException, URISyntaxException {
        return TestCase.resources("controller", ".jsonld");
    }

    static final JsonObject resource(String name) throws IOException, URISyntaxException {
        try (var reader = Json.createReader(ControllerDocTest.class.getResourceAsStream(name))) {
            return reader.readObject();
        }
    }
}
