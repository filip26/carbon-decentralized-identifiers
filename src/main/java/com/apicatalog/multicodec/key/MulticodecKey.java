package com.apicatalog.multicodec.key;

import java.util.Arrays;
import java.util.Objects;

import com.apicatalog.controller.key.RawByteKey;
import com.apicatalog.multicodec.Multicodec;

public interface MulticodecKey extends RawByteKey {

    Multicodec codec();

    @Override
    default String type() {
        return codec() != null ? codec().name() : null;
    }

    static boolean equals(MulticodecKey k1, MulticodecKey k2) {
        if (k1 == null || k2 == null) {
            return k1 == k2;

        }
        return Objects.equals(k1.codec(), k2.codec())
                && Objects.equals(k1.type(), k2.type())
                && Arrays.equals(k1.rawBytes(), k2.rawBytes());
    }

}
