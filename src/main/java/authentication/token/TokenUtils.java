package authentication.token;

import io.smallrye.jwt.util.KeyUtils;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

public class TokenUtils {

    public static String generateTokenString(JwtClaims claims) throws Exception {
        //use the private key associated with the public key for a valid signature
        PrivateKey privateKey = KeyUtils.readPrivateKey("/PrivateKey.pem");
        return generateTokenString(privateKey, "/PrivateKey.pem", claims);

    }

    private static String generateTokenString(PrivateKey privateKey, String kid, JwtClaims claims) throws Exception {
        long currentTimeInSecs = currentTimeInSecs();
        claims.setIssuedAt(NumericDate.fromSeconds(currentTimeInSecs));
        claims.setClaim(Claims.auth_time.name(), NumericDate.fromSeconds(currentTimeInSecs));
        for (Map.Entry<String, Object> entry : claims.getClaimsMap().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(privateKey);
        jws.setKeyIdHeaderValue(kid);
        jws.setHeader("type", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_PSS_USING_SHA256);

        return jws.getCompactSerialization();
    }

    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----","");
        pem = pem.replaceAll("-----END (.*)-----","");
        pem = pem.replaceAll("\\r\\n","");
        pem = pem.replaceAll("\\n","");
        return pem.trim();
    }

    /**
     * @return the current time in seconds since epoch
     */
    public static int currentTimeInSecs() {
        long currentTimeMs = System.currentTimeMillis();
        return (int) (currentTimeMs / 1000);
    }
}