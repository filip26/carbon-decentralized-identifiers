package com.apicatalog.multicodec.key;

import com.apicatalog.controller.key.RawKeyBytes;
import com.apicatalog.multicodec.Multicodec;

public interface MulticodecKey extends RawKeyBytes {

    Multicodec codec();

    @Override
    default String type() {
        return codec() != null ? codec().name() : null;
    }
}
