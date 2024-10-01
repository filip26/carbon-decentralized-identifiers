package com.apicatalog.did.web;

import java.io.IOException;
import java.io.InputStream;

import com.apicatalog.did.Did;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.resolver.DidResolver;

public class DidWebResolver implements DidResolver {

    protected final DidWebFetch fetch;
    
    protected DidWebResolver(DidWebFetch fetch) {
        this.fetch = fetch;
    }
    
    @Override
    public DidDocument resolve(Did did) {
        
        final DidWeb didWeb = DidWeb.of(did);
        
        try (InputStream is = fetch.getInputStream(didWeb.getUrl())) {
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // TODO Auto-generated method stub
        return null;
    }
}
