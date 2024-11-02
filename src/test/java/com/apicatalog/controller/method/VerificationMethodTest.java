package com.apicatalog.controller.method;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.TestCase;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jwk.JsonWebKey;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.builder.TreeBuilderError;
import com.apicatalog.linkedtree.jsonld.io.JsonLdReader;
import com.apicatalog.linkedtree.jsonld.io.JsonLdWriter;
import com.apicatalog.linkedtree.orm.mapper.TreeMapping;
import com.apicatalog.multikey.Multikey;

import jakarta.json.JsonObject;

@TestMethodOrder(OrderAnnotation.class)
class VerificationMethodTest {

    static TreeMapping MAPPING = TreeMapping
            .createBuilder()
            .scan(JsonWebKey.class)
            .scan(Multikey.class)
            .scan(VerificationMethod.class)
            .build();

    static JsonLdReader READER = JsonLdReader.of(MAPPING);

    static JsonLdWriter WRITER = new JsonLdWriter().scan(VerificationMethod.class);

    @DisplayName("Read")
    @ParameterizedTest(name = "{0}")
    @MethodSource({ "multikeys", "jwks" })
    void readAndCompact(String name, JsonObject doc) throws TreeBuilderError, NodeAdapterError, JsonLdError {

        var method = READER.read(VerificationMethod.class, doc);

        assertNotNull(method);
        assertNotNull(method.id());
        assertNotNull(method.type());
        assertNotNull(method.controller());
    }

    static final Stream<Object[]> multikeys() throws IOException, URISyntaxException {
        return TestCase.resources("multikey", ".jsonld");
    }

    static final Stream<Object[]> jwks() throws IOException, URISyntaxException {
        return TestCase.resources("jwk", ".jsonld");
    }
}
