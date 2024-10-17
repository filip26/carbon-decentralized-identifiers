package com.apicatalog.multicodec.key;

import com.apicatalog.controller.key.RawByteKey;
import com.apicatalog.multicodec.Multicodec;

public interface MulticodecKey extends RawByteKey {

    Multicodec codec();

    @Override
    default String type() {
        return codec() != null ? codec().name() : null;
    }
}
