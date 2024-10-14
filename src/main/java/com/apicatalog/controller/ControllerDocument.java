package com.apicatalog.controller;

import java.net.URI;
import java.util.Set;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.linkedtree.type.Type;

@Fragment(generic = true)
@Vocab("https://w3id.org/security#")
public interface ControllerDocument {

    @Id
    URI id();

    /**
     * An optional set of controller document types.
     * 
     * @return a selector of document types, never <code>null</code>.
     */
    Type type();

    Set<URI> controller();

    @Term("verificationMethod")
    Set<VerificationMethod> verification();

    Set<URI> alsoKnownAs();

    @Term("authenticationMethod")
    Set<VerificationMethod> authentication();

    @Term("assertionMethod")
    Set<VerificationMethod> assertion();

    @Term("keyAgreementMethod")
    Set<VerificationMethod> keyAgreement();

    @Term("capabilityInvocationMethod")
    Set<VerificationMethod> capabilityInvocation();

    @Term("capabilityDelegationMethod")
    Set<VerificationMethod> capabilityDelegation();
}
