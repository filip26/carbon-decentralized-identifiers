package com.apicatalog.did.document;

import java.net.URI;

public class ServiceEndpoint {

    URI id;
    
    String type;
    
    public ServiceEndpoint(URI id, String type) {
        this.id = id;
        this.type = type;
    }
    
}
