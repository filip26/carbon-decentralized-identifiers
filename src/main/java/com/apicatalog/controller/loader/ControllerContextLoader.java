package com.apicatalog.controller.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.apicatalog.controller.ControllerDocument;
import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.loader.DocumentLoader;
import com.apicatalog.jsonld.loader.DocumentLoaderOptions;

public class ControllerContextLoader implements DocumentLoader {

    protected static final ControllerContextLoader INSTANCE = new ControllerContextLoader(null);

    protected static final Map<String, Document> staticCache = defaultValues();

    protected final DocumentLoader defaultLoader;

    public ControllerContextLoader(final DocumentLoader defaultLoader) {
        this.defaultLoader = defaultLoader;
    }

    @Override
    public Document loadDocument(final URI url, final DocumentLoaderOptions options) throws JsonLdError {

        final Document document = staticCache.get(url.toString());

        if (document != null) {
            return document;
        }

        if (defaultLoader != null) {
            return defaultLoader.loadDocument(url, options);
        }
        return null;
    }

    public static Map<String, Document> defaultValues() {

        Map<String, Document> staticCache = new LinkedHashMap<>();

        staticCache.put("https://www.w3.org/ns/controller/v1", get("context-v1.jsonld"));
        staticCache.put("https://w3id.org/security/multikey/v1", get("multikey-v1.jsonld"));
        staticCache.put("https://w3id.org/security/jwk/v1", get("jwk-v1.jsonld"));
        staticCache.put("https://www.w3.org/ns/did/v1", get("did-v1.jsonld"));

        return Collections.unmodifiableMap(staticCache);
    }

    protected static JsonDocument get(final String name) {
        return get(ControllerDocument.class, name);
    }

    protected static JsonDocument get(Class<?> root, final String name) {
        try (final InputStream is = root.getResourceAsStream(name)) {
            return JsonDocument.of(is);

        } catch (IOException | JsonLdError e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ControllerContextLoader resources() {
        return INSTANCE;
    }
}
