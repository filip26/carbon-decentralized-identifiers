package com.apicatalog.did.key;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidResolver;
import com.apicatalog.did.DidUrl;
import com.apicatalog.did.document.DidDocument;
import com.apicatalog.did.document.DidVerificationMethod;

public class DidKeyResolver implements DidResolver {

//    private static final String ED25519_VERIFICATION_KEY_2020_TYPE =  "https://w3id.org/security#Ed25519VerificationKey2020";

    @Override
    public DidDocument resolve(final Did did) {

        if (!DidKey.isDidKey(did)) {
            throw new IllegalArgumentException();
        }

        final DidKey didKey = DidKey.from(did);

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
     * @see <a href="https://pr-preview.s3.amazonaws.com/w3c-ccg/did-method-key/pull/51.html#signature-method-creation-algorithm">Signature Method Algorithm</a>
     *
     * @return The new verification key
     */
    public static DidVerificationMethod createSignatureMethod(DidKey didKey) {

//        if (!Multicodec.Codec.Ed25519PublicKey.equals(didKey.getCodec())) {
//            throw new IllegalArgumentException();
//        }
        // 5.
//        String encodingType = ED25519_VERIFICATION_KEY_2020_TYPE;

        return new DidVerificationMethod(
                    DidUrl.from(didKey, null, null, didKey.getMethodSpecificId()),
                    DidUrl.from(didKey, null, null, didKey.getMethodSpecificId()),
                    didKey.getCodec().name(),
                    didKey.getRawKey()
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
                    DidUrl.from(didKey, null, null, didKey.getMethodSpecificId()),
                    DidUrl.from(didKey, null, null, didKey.getMethodSpecificId()),
                    didKey.getCodec().name(),
                    didKey.getRawKey()
                    );
    }
}
