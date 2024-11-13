package com.apicatalog.did.key;

import java.net.URI;
import java.util.Objects;

import com.apicatalog.controller.ControllerDocument;
import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.controller.resolver.ControllerResolver;
import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.resolver.DidResolver;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.multikey.GenericMultikey;

public class DidKeyResolver implements DidResolver, ControllerResolver {

    protected final MulticodecDecoder codecs;

    public DidKeyResolver(MulticodecDecoder codecs) {
        this.codecs = codecs;
    }

    @Override
    public DidDocument resolve(final Did did) {

        Objects.nonNull(did);
        
        if (!DidKey.isDidKey(did)) {
            throw new IllegalArgumentException();
        }

        final DidKey didKey = DidKey.of(did, codecs);

        return DidKeyDocument.of(
                did != null ? did.toUri() : null,
                DidKeyResolver.createMethod(didKey));
    }

    public static VerificationMethod createMethod(final DidKey didKey) {

        final URI uri = DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()).toUri();

        return GenericMultikey.of(
                uri,
                DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()).toUri(),
                didKey);
    }

    @Override
    public boolean isAccepted(URI id) {
        return DidKey.isDidKey(id);
    }

    @Override
    public ControllerDocument resolve(URI id) {
        return resolve(Did.of(id));
    }
}
