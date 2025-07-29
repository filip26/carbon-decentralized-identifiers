package com.apicatalog.controller.resolver;

import java.net.URI;

import com.apicatalog.controller.ControllerDocument;

public interface ControllerResolver {

    boolean isAccepted(URI id);
    
    ControllerDocument resolve(URI id);
    
}
