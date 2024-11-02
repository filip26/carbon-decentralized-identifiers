package com.apicatalog.controller;

import java.net.URI;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.type.FragmentType;

@Fragment(generic = true)
public interface ServiceEndpoint {

    @Id
    URI id();

    FragmentType type();

}
