package com.apicatalog.multibase;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.adapter.DatatypeAdapter;

public class MultibaseAdapter implements DatatypeAdapter {

    protected final MultibaseDecoder decoder;

    public MultibaseAdapter() {
        this(MultibaseDecoder.getInstance());
    }

    public MultibaseAdapter(MultibaseDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public LinkedLiteral materialize(String source) throws NodeAdapterError {
        return MultibaseLiteral.of(decoder, source);
    }

    @Override
    public String datatype() {
        return MultibaseLiteral.TYPE;
    }

    @Override
    public Class<? extends LinkedLiteral> typeInterface() {
        return MultibaseLiteral.class;
    }
}
