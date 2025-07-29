package com.apicatalog.did.document;

import com.apicatalog.controller.ControllerDocument;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;

/**
 * DID Document
 * 
 * @see <a href="https://www.w3.org/TR/did-core/#did-document-properties">DID
 *      document properties</a>
 */
@Fragment(generic = true)
@Context(value =  "https://www.w3.org/ns/did/v1", override = true)
public interface DidDocument extends ControllerDocument {

}
