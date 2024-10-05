package com.apicatalog.did.document;

import java.net.URI;

public record GenericServiceEndpoint(URI id, String type) implements ServiceEndpoint {
    
}
