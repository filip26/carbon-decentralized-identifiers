package com.apicatalog.did.resolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.apicatalog.controller.ControllerDocument;
import com.apicatalog.did.Did;
import com.apicatalog.did.document.DidDocument;

public class DidMethodResolver implements DidResolver {

    protected final Map<String, DidResolver> resolvers;

    protected DidMethodResolver(Map<String, DidResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public DidResolver get(final String method) {
        return resolvers.get(method);
    }

    @Override
    public DidDocument resolve(Did did) {

        Objects.requireNonNull(did);

        final DidResolver resolver = resolvers.get(did.getMethod());

        if (resolver != null) {
            return resolver.resolve(did);
        }

        throw new IllegalArgumentException("The " + did.toString() + " method cannot be resolved.");
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {

        final Map<String, DidResolver> resolvers;

        public Builder() {
            resolvers = new HashMap<>();
        }

        public Builder add(String method, DidResolver resolver) {
            resolvers.put(method, resolver);
            return this;
        }

        public DidMethodResolver build() {
            return new DidMethodResolver(Collections.unmodifiableMap(resolvers));
        }
    }
}
