package com.apicatalog.controller;

import java.net.URI;
import java.util.Collection;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.type.FragmentType;

@Fragment(generic = true)
public interface Service {

    @Id
    URI id();

    FragmentType type();

    @Term(value =  "serviceEndpoint", vocab = "https://www.w3.org/ns/did#")
    Collection<ServiceEndpoint> endpoint();
}
