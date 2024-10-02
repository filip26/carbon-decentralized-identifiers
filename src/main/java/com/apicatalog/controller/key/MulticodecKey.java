package com.apicatalog.controller.key;

import com.apicatalog.multicodec.Multicodec;

public interface MulticodecKey extends RawKey {

    Multicodec codec();

}
