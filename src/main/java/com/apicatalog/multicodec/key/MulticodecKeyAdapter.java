package com.apicatalog.multicodec.key;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.adapter.DataTypeNormalizer;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multibase.MultibaseAdapter;
import com.apicatalog.multibase.MultibaseLiteral;
import com.apicatalog.multicodec.Multicodec.Tag;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.uvarint.UVarInt;

public class MulticodecKeyAdapter extends MultibaseAdapter implements DataTypeNormalizer<MulticodecKey> {

    protected static final MulticodecDecoder CODECS = MulticodecDecoder.getInstance(Tag.Key);

    @Override
    public LinkedLiteral materialize(String source) throws NodeAdapterError {
        
        Multibase base = decoder.getBase(source).orElseThrow(IllegalArgumentException::new);

        return getKey(source, base, decoder.decode(source));
    }

    @Override
    public Class<? extends LinkedLiteral> typeInterface() {
        return MulticodecKeyLiteral.class;
    }

    protected static final MulticodecKeyLiteral getKey(String source, Multibase base, final byte[] encodedKey) throws NodeAdapterError {

        if (encodedKey == null || encodedKey.length == 0) {
            return null;
        }

        return CODECS.getCodec(encodedKey)
                .map(codec -> new MulticodecKeyLiteral(source, MultibaseLiteral.typeName(), codec, base, codec.decode(encodedKey)))
                .orElseThrow(() -> new NodeAdapterError("Unsupported multicodec code=" + UVarInt.decode(encodedKey) + "."));
    }

    @Override
    public String normalize(MulticodecKey value) {
        
        if (value instanceof MulticodecKeyLiteral literal) {
            return literal.lexicalValue();
        }
        
        if (value == null || value.rawBytes() == null || value.rawBytes().length == 0) {
            return null;
        }
        
        return Multibase.BASE_58_BTC.encode(value.codec().encode(value.rawBytes()));
    }
}
