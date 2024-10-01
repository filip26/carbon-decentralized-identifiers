package com.apicatalog.controller.method;

import java.net.URI;
import java.time.Instant;

/**
 * Represents a verification method declaration.
 * 
 * https://www.w3.org/TR/controller-document/#verification-methods
 */
public interface VerificationMethod {

    URI id();

    String type();

    URI controller();
    
    Instant revoked();

}