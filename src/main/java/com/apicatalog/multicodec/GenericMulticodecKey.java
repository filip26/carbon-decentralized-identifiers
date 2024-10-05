package com.apicatalog.multicodec;

import com.apicatalog.multicodec.Multicodec;

public record GenericMulticodecKey(Multicodec codec, byte[] raw) implements MulticodecKey {

}
