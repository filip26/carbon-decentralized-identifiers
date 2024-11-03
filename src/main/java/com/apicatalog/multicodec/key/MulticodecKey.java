package com.apicatalog.multicodec.key;

import java.util.Objects;

import com.apicatalog.controller.key.RawByteKey;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.Multicodec;

public interface MulticodecKey extends RawByteKey {

    Multicodec codec();

    Multibase base();

    @Override
    default String type() {
        return codec() != null ? codec().name() : null;
    }

    static boolean equals(MulticodecKey k1, MulticodecKey k2) {
        if (k1 == null || k2 == null) {
            return k1 == k2;
        }
        return Objects.equals(k1.codec(), k2.codec())
                && RawByteKey.equals(k1, k2);
    }
}
