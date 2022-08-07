package com.apicatalog.did.key;

import java.util.HashSet;
import java.util.Set;

import com.apicatalog.did.Did;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.document.DidVerificationMethod;

final class DidDocumentBuilder {

    private Did id;
    private Set<DidVerificationMethod> verificationMethod;

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

    public DidDocumentBuilder add(DidVerificationMethod verificationMethod) {
        this.verificationMethod.add(verificationMethod);
        return this;
    }

    public DidDocument build() {
        return new DidDocument(id, null, verificationMethod);
    }
}
