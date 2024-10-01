package com.apicatalog.did.document;

import java.util.Set;

import com.apicatalog.controller.ControllerDocument;

/**
 * DID Document
 * 
 * @see <a href="https://www.w3.org/TR/did-core/#did-document-properties">DID
 *      document properties</a>
 */
public interface DidDocument extends ControllerDocument {

    Set<ServiceEndpoint> service();
}
