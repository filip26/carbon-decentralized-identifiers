package com.apicatalog.did.key;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import com.apicatalog.controller.Service;
import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.linkedtree.type.FragmentType;

class DidKeyDocument implements DidDocument {

    protected URI id;

    protected Set<VerificationMethod> method;

    protected DidKeyDocument(URI id, Set<VerificationMethod> method) {
        this.id = id;
        this.method = method;
    }

    public static DidKeyDocument of(URI id, VerificationMethod method) {
        return new DidKeyDocument(id, Set.of(method));
    }
    
    /**
     * Get DID for a particular DID subject
     * 
     * @return a DID
     */
    public URI id() {
        return id;
    }

    public Set<URI> controller() {
        return Collections.emptySet();
    }
    
    public Set<VerificationMethod> verification() {
        return method;
    }
    
    public Set<URI> alsoKnownAs() {
        return Collections.emptySet();
    }
    
    public Set<Service> service() {
        return Collections.emptySet();
    }

    @Override
    public Set<VerificationMethod> authentication() {
        return method;
    }

    @Override
    public Set<VerificationMethod> assertion() {
        return method;
    }

    @Override
    public Set<VerificationMethod> keyAgreement() {
        return Collections.emptySet();
    }

    @Override
    public Set<VerificationMethod> capabilityInvocation() {
        return method;
    }

    @Override
    public Set<VerificationMethod> capabilityDelegation() {
        return method;
    }

    @Override
    public FragmentType type() {
        return FragmentType.empty();
    }
}
