package com.apicatalog.controller.method;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;

/**
 * Represents a verification method declaration.
 * 
 * https://www.w3.org/TR/controller-document/#verification-methods
 */
@Fragment
@Vocab("https://w3id.org/security#")
public interface VerificationMethod {

    URI id();

    String type();

    URI controller();
    
    Instant revoked();

    @Term("expiration")
    Instant expires();

}