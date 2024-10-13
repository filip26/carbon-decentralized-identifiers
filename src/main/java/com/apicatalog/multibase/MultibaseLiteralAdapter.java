package com.apicatalog.multibase;

import java.util.Objects;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.LinkedTree;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.ByteArrayValue;
import com.apicatalog.linkedtree.literal.adapter.GenericLiteralAdapter;
import com.apicatalog.linkedtree.literal.adapter.DataTypeAdapter;

public class MultibaseLiteralAdapter implements DataTypeAdapter {

    @Override
    public void setup(String[] params) throws NodeAdapterError {
        // TODO Auto-generated method stub
        DataTypeAdapter.super.setup(params);
    }

    @Override
    public LinkedLiteral materialize(String source, LinkedTree root) throws NodeAdapterError {
        // TODO Auto-generated method stub

//            MultibaseLiteral.of(base, value, root)
//            @Override
//            public DataTypeAdapter adapter() {
//                return MultibaseLiteral.typeAdapter(Multibase.BASE_58_BTC);
//            }

        return null;
    }

    @Override
    public String datatype() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<? extends LinkedLiteral> typeInterface() {
        // TODO Auto-generated method stub
        return null;
    }

}
