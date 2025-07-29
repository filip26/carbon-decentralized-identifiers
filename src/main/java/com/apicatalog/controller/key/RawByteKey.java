package com.apicatalog.controller.key;

import java.util.Arrays;
import java.util.Objects;

public interface RawByteKey {

    String type();

    /**
     * Get raw key value.
     * 
     * @return raw key value as byte array
     */
    byte[] rawBytes();

    static boolean equals(RawByteKey k1, RawByteKey k2) {
        if (k1 == null || k2 == null) {
            return k1 == k2;
        }
        return Objects.equals(k1.type(), k2.type())
                && Arrays.equals(k1.rawBytes(), k2.rawBytes());
    }
}
