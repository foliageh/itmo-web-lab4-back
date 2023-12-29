package web.twillice.rest.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import web.twillice.rest.model.Role;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.List;

@ApplicationScoped
public class JWTManager {
    private static final PrivateKey privateKey;
    private static final int TOKEN_VALIDITY = 60 * 60 * 24 * 1;
    private static final String ISSUER = "lab4-jwt-issuer";
    private static final String AUDIENCE = "jwt-audience";

    static {
        final String configDir = System.getProperty("jboss.server.config.dir");
        final Path keyStore = Path.of(configDir, "jwt.keystore");
        char[] password = "mysuperpassword".toCharArray();
        String alias = "jwt-auth";
        PrivateKey pk = null;
        try (InputStream in = Files.newInputStream(keyStore)) {
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(in, password);
            Key key = ks.getKey(alias, password);
            if (key instanceof PrivateKey) {
                pk = (PrivateKey) key;
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        privateKey = pk;
    }

    // TODO: use less complicated library
    // TODO: add refresh token
    public String createToken(String subject, Role role) throws Exception {
        JsonArrayBuilder rolesBuilder = Json.createArrayBuilder(List.of(role.name()));
        JsonObjectBuilder claimsBuilder = Json.createObjectBuilder()
                .add("sub", subject)
                .add("iss", ISSUER)
                .add("aud", AUDIENCE)
                .add("groups", rolesBuilder.build())
                .add("exp", ((System.currentTimeMillis() / 1000) + TOKEN_VALIDITY));
        JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(new JOSEObjectType("jwt")).build(),
                new Payload(claimsBuilder.build().toString()));
        jwsObject.sign(new RSASSASigner(privateKey));
        return jwsObject.serialize();

//        return JWT.create()
//                .withSubject(subject)
//                .withIssuer(ISSUER)
//                .withAudience(AUDIENCE)
//                .withClaim("groups", List.of(role.name()))
//                .withExpiresAt(Instant.now().plusSeconds(TOKEN_VALIDITY))
//                .sign(Algorithm.RSA256(privateKey));
    }
}
