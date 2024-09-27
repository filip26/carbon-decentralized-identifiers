package com.apicatalog.did.key;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.document.DidVerificationMethod;
import com.apicatalog.did.resolver.DidResolver;
import com.apicatalog.multibase.MultibaseDecoder;

public class DidKeyResolver implements DidResolver {

    protected final MultibaseDecoder bases;
    
    public DidKeyResolver(final MultibaseDecoder bases) {
        this.bases = bases;
    }
    
    @Override
    public DidDocument resolve(final Did did) {

        if (!DidKey.isDidKey(did)) {
            throw new IllegalArgumentException();
        }

        final DidKey didKey = DidKey.of(did, bases);

        final DidDocumentBuilder builder = DidDocumentBuilder.create();

        // 4.
        DidVerificationMethod signatureMethod = DidKeyResolver.createSignatureMethod(didKey);
        builder.add(signatureMethod);

        // 5.
        builder.add(DidKeyResolver.createEncryptionMethod(didKey));

        // 6.
        builder.id(did);

        // 7.

        // 8.

        // 9.

        return builder.build();
    }

    /**
     * Creates a new verification key by expanding the given DID key.
     *
     * @param didKey
     *
     * @return The new verification key
     */
    public static DidVerificationMethod createSignatureMethod(DidKey didKey) {
        return new DidVerificationMethod(
                    DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()),
                    DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()),
                    didKey.getKey()
                    );
     }

    public static DidVerificationMethod createEncryptionMethod(final DidKey didKey) {

        // 3.

        // 5.
//        String encodingType = "MultiKey";

        // 6.

        // 7.

        // 9.
        return new DidVerificationMethod(
                    DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()),
                    DidUrl.of(didKey, null, null, didKey.getMethodSpecificId()),
                    didKey.getKey()
                    );
    }
}
