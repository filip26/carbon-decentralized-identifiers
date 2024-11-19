package com.apicatalog.controller.method;

import java.net.URI;
import java.time.Instant;

public record GenericMethodUri(
        URI id
        ) implements VerificationMethod {

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

    @Override
    public Instant expires() {
        return null;
    }
}
