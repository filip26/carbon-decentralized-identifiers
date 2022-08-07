package com.apicatalog.did.document;

import java.util.Objects;
import java.util.Set;

import com.apicatalog.did.Did;

/**
 * DID Document
 * 
 * @param id 
 * @param controller 
 * @param verificationMethod 
 *
 * @see <a href="https://www.w3.org/TR/did-core/#did-document-properties">DID document properties</a>
 */

public record DidDocument(
            Did id,
            Set<Did> controller, 
            Set<DidVerificationMethod> verificationMethod
            ) {

    public DidDocument {
        Objects.requireNonNull(id);
    }
}
