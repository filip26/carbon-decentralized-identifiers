package com.apicatalog.did.document;

import java.net.URI;
import java.util.Set;

import com.apicatalog.controller.ControllerDocument;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Vocab;

/**
 * DID Document
 * 
 * @see <a href="https://www.w3.org/TR/did-core/#did-document-properties">DID
 *      document properties</a>
 */
@Fragment(generic = true)
public interface DidDocument extends ControllerDocument {

    @Override
    @Vocab("https://www.w3.org/ns/activitystreams#")
    Set<URI> alsoKnownAs();

    @Vocab("https://www.w3.org/ns/did#")
    Set<ServiceEndpoint> service();
}
