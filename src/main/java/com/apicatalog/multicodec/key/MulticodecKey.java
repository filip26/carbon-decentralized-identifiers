package com.apicatalog.multicodec.key;

import com.apicatalog.controller.key.RawKey;
import com.apicatalog.multicodec.Multicodec;

public interface MulticodecKey extends RawKey {

    Multicodec codec();
    
    @Override
    default String type() {
        return codec() != null ? codec().name() : null;
    }

}
