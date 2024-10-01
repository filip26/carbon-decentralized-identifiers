package com.apicatalog.did.web;

import java.io.InputStream;
import java.net.URI;

@FunctionalInterface
public interface DidWebFetch {

    InputStream getInputStream(URI uri);
    
}
