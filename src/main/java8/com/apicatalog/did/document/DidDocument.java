package com.apicatalog.did.document;

import java.util.Set;

import com.apicatalog.did.Did;

/**
 * DID Document
 * 
 * @see <a href="https://www.w3.org/TR/did-core/#did-document-properties">DID document properties</a>
 */

public class DidDocument {

    protected final Did id;

    protected final Set<Did> controller;

    protected final Set<DidVerificationMethod> verificationMethod;

//    protected Set<URI> alsoKnownAs;
//    protected Set<DidUrl> assertionMethod;
//    protected Set<DidUrl> authentication;
//    protected Set<DidUrl> capabilityInvocation;
//    protected Set<DidUrl> capabilityDelegation;
//    protected Set<DidUrl> keyAgreement;

    public DidDocument(
            Did id,
            Set<Did> controller, 
            Set<DidVerificationMethod> verificationMethod
            ) {
        this.id = id;
        this.controller = controller;
        this.verificationMethod = verificationMethod;
    }
 
    public Did id() {
        return id;
    }
    
    public Set<Did> controller() {
        return controller;
    }
    
    public Set<DidVerificationMethod> verificationMethod() {
        return verificationMethod;
    }
}
