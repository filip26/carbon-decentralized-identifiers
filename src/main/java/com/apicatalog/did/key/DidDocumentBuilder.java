package com.apicatalog.did.key;

import java.util.HashSet;
import java.util.Set;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.did.Did;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.document.GenericDidDocument;

final class DidDocumentBuilder {

    private Did id;
    private Set<VerificationMethod> verificationMethod;

    protected DidDocumentBuilder() {
        this.verificationMethod = new HashSet<>();
    }

    public static DidDocumentBuilder create() {
        return new DidDocumentBuilder();
    }

    public DidDocumentBuilder id(Did did) {
        this.id = did;
        return this;
    }

    public DidDocumentBuilder add(VerificationMethod verificationMethod) {
        this.verificationMethod.add(verificationMethod);
        return this;
    }

    public DidDocument build() {
        final GenericDidDocument doc = new GenericDidDocument(id != null ? id.toUri() : null);
        doc.verificationMethod(verificationMethod);
        return doc;
    }
}
