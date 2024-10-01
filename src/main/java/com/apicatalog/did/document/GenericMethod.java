package com.apicatalog.did.document;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;

public class GenericMethod implements VerificationMethod {

    DidUrl id;
    
    Did controller;
    
    String type;
    
    Instant revoked;
    
    public GenericMethod(
            DidUrl id,
            String type,
            Did controller) {
        this.id = id;
        this.type = type;
        this.controller = controller;
    }

    public URI id() {
        return id.toUri();
    }

    public String type() {
        return type;
    }
    
    public URI controller() {
        return controller.toUri();
    }

    @Override
    public Instant revoked() {
        return revoked;
    }
    
    public void revoked(Instant revoked) {
        this.revoked = revoked;
    }
}
