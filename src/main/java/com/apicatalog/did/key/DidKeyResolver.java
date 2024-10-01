package com.apicatalog.did.key;

import java.net.URI;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.resolver.DidResolver;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.multikey.MultiKey;

public class DidKeyResolver implements DidResolver {

    protected final MulticodecDecoder codecs;

    public DidKeyResolver(MulticodecDecoder codecs) {
        this.codecs = codecs;
    }

    @Override
    public DidDocument resolve(final Did did) {

        if (!DidKey.isDidKey(did)) {
            throw new IllegalArgumentException();
        }

        final DidKey didKey = DidKey.of(did, codecs);

        return DidDocumentBuilder.create()
                .id(did)
                .add(DidKeyResolver.createMethod(didKey))
                .build();
    }

    public static VerificationMethod createMethod(final DidKey didKey) {

        final URI uri = DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()).toUri();

        return MultiKey.of(
                uri,
                DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()).toUri(),
                didKey);
    }
}
