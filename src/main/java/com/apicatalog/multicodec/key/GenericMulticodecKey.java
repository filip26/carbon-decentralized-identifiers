package com.apicatalog.multicodec.key;

import com.apicatalog.multicodec.Multicodec;

public record GenericMulticodecKey(Multicodec codec, byte[] rawBytes) implements MulticodecKey {

}
