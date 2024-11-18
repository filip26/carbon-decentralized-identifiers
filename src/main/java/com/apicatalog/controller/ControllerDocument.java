package com.apicatalog.controller;

import java.net.URI;
import java.util.Set;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Compaction;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.linkedtree.type.FragmentType;

@Fragment(generic = true)
@Vocab("https://w3id.org/security#")
@Context("https://www.w3.org/ns/controller/v1")
public interface ControllerDocument {

    @Id
    URI id();

    /**
     * An optional set of controller document types.
     * 
     * @return a selector of document types, never <code>null</code>.
     */
    FragmentType type();

    @Compaction(order = 10)
    Set<URI> controller();

    @Term("verificationMethod")
    @Compaction(keepArray = true, order = 20)
    Set<VerificationMethod> verification();

    @Vocab("https://www.w3.org/ns/activitystreams#")
    @Compaction(keepArray = true)
    Set<URI> alsoKnownAs();

    @Term(value = "authenticationMethod", compact = false)
    @Compaction(keepArray = true)
    Set<VerificationMethod> authentication();

    @Term("assertionMethod")
    @Compaction(keepArray = true)
    Set<VerificationMethod> assertion();

    @Term(value = "keyAgreementMethod", compact = false)
    @Compaction(keepArray = true)
    Set<VerificationMethod> keyAgreement();

    @Term(value = "capabilityInvocationMethod", compact = false)
    @Compaction(keepArray = true)
    Set<VerificationMethod> capabilityInvocation();

    @Term(value = "capabilityDelegationMethod", compact = false)
    @Compaction(keepArray = true)
    Set<VerificationMethod> capabilityDelegation();

    @Vocab("https://www.w3.org/ns/did#")
    @Compaction(keepArray = true)
    Set<Service> service();    
}
