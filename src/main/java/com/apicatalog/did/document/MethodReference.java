package com.apicatalog.did.document;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.controller.method.VerificationMethod;

public record MethodReference(URI id) implements VerificationMethod {

    @Override
    public String type() {
        return null;
    }

    @Override
    public URI controller() {
        return null;
    }

    @Override
    public Instant revoked() {
        return null;
    }
}
