package com.apicatalog.controller;

import java.net.URI;
import java.util.Collection;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.linkedtree.type.Type;

@Fragment(generic = true)
public interface Service {

    @Id
    URI id();

    Type type();

    @Term("serviceEndpoint")
    @Vocab("https://www.w3.org/ns/did#")
    Collection<ServiceEndpoint> endpoint();
}
