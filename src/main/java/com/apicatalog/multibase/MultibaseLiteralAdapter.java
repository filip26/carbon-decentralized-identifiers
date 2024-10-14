package com.apicatalog.multibase;

import java.util.Arrays;
import java.util.Objects;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.LinkedTree;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.adapter.DataTypeAdapter;

public class MultibaseLiteralAdapter implements DataTypeAdapter {

    MultibaseDecoder decoder = MultibaseDecoder.getInstance();

    @Override
    public void setup(String[] params) throws NodeAdapterError {

        if (params == null || params.length == 0) {
            return;
        }

        decoder = MultibaseDecoder.getInstance(
                Arrays.stream(params)
                        .map(param -> switch (param) {
                        case "base58btc" -> Multibase.BASE_58_BTC;
                        case "base64url" -> Multibase.BASE_64_URL;
                        default -> null;
                        })
                        .filter(Objects::nonNull)
                        .toArray(Multibase[]::new));
    }

    @Override
    public LinkedLiteral materialize(String source, LinkedTree root) throws NodeAdapterError {
        return MultibaseLiteral.of(decoder, source, root);        
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
