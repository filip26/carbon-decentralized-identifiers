package com.apicatalog.did.document;

import java.net.URI;
import java.util.Set;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.did.Did;

public class GenericDocument implements DidDocument {

    protected URI id;

    protected Set<URI> controller;

    protected Set<URI> alsoKnownAs;

    protected Set<VerificationMethod> verificationMethod;

//  protected Set<DidUrl> authentication;
//  protected Set<DidUrl> assertionMethod;
//  protected Set<DidUrl> keyAgreement;
//  protected Set<DidUrl> capabilityInvocation;
//  protected Set<DidUrl> capabilityDelegation;

    
    protected Set<ServiceEndpoint> service;
    

    public GenericDocument(URI id) {
        this.id = id;
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
        return controller;
    }
    
    public void controller(Set<URI> controller) {
        this.controller = controller;
    }

    public Set<VerificationMethod> verificationMethod() {
        return verificationMethod;
    }
    
    public void verificationMethod(Set<VerificationMethod> verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public Set<URI> alsoKnownAs() {
        return alsoKnownAs;
    }
    
    public Set<ServiceEndpoint> service() {
        return service;
    }

    @Override
    public Set<VerificationMethod> authentication() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<VerificationMethod> assertionMethod() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<VerificationMethod> keyAgreement() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<VerificationMethod> capabilityInvocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<VerificationMethod> capabilityDelegation() {
        // TODO Auto-generated method stub
        return null;
    }
}
