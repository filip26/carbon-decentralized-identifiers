package com.apicatalog.controller.key;

import com.apicatalog.multicodec.Multicodec;

public interface MultihashKey extends Key {

    Multicodec codec();
    
}
