package com.apicatalog.controller;

import java.net.URI;
import java.util.Set;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Vocab;

@Vocab("https://w3id.org/security#")
public interface ControllerDocument {

    @Id
    URI id();

    Set<URI> controller();

    Set<VerificationMethod> verificationMethod();

    Set<URI> alsoKnownAs();

    Set<VerificationMethod> authentication();

    Set<VerificationMethod> assertionMethod();

    Set<VerificationMethod> keyAgreement();

    Set<VerificationMethod> capabilityInvocation();

    Set<VerificationMethod> capabilityDelegation();

}
