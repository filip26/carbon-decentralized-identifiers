package com.apicatalog.controller.method;

import java.net.URI;
import java.time.Instant;

public class GenericMethodUri implements VerificationMethod {

    protected final URI id;
    
    public GenericMethodUri(URI id) {
        this.id = id;
    }
    
    @Override
    public URI id() {
        return id;
    }
    
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
