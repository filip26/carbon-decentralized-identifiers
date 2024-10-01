package com.apicatalog.controller;

import java.net.URI;
import java.util.Set;

import com.apicatalog.controller.method.VerificationMethod;

public interface ControllerDocument {

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
