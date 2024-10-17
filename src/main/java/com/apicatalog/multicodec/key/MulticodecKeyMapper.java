package com.apicatalog.multicodec.key;

import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.ByteArrayValue;
import com.apicatalog.linkedtree.orm.mapper.LiteralMapper;
import com.apicatalog.multicodec.Multicodec.Tag;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.uvarint.UVarInt;

public class MulticodecKeyMapper implements LiteralMapper<ByteArrayValue, MulticodecKey> {

    static final MulticodecDecoder CODECS = MulticodecDecoder.getInstance(Tag.Key);

    @Override
    public MulticodecKey map(ByteArrayValue literal) throws NodeAdapterError {
        return getKey(literal.byteArrayValue(), CODECS);
    }

    static final MulticodecKey getKey(final byte[] encodedKey, MulticodecDecoder decoder) throws NodeAdapterError {

        if (encodedKey == null || encodedKey.length == 0) {
            return null;
        }

        return decoder.getCodec(encodedKey)
                .map(codec -> new MulticodecKeyLiteral(null, null, codec, codec.decode(encodedKey))) //FIXME
                .orElseThrow(() -> new NodeAdapterError("Unsupported multicodec code=" + UVarInt.decode(encodedKey) + "."));
    }
}
