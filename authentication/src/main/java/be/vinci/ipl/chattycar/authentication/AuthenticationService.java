package be.vinci.ipl.chattycar.authentication;

import be.vinci.ipl.chattycar.authentication.models.Credentials;
import be.vinci.ipl.chattycar.authentication.models.InsecureCredentials;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository repository;
    private final Algorithm jwtAlgorithm;
    private final JWTVerifier jwtVerifier;

    public AuthenticationService(AuthenticationRepository repository, AuthenticationProperties properties) {
        this.repository = repository;
        this.jwtAlgorithm = Algorithm.HMAC512(properties.getSecret());
        this.jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
    }


    /**
     * Connects user with credentials
     * @param insecureCredentials The credentials with insecure password
     * @return The JWT token, or null if the user couldn't be connected
     */
    public String connect(InsecureCredentials insecureCredentials) {
        Credentials credentials = repository.findById(insecureCredentials.getEmail()).orElse(null);
        if (credentials == null) return null;
        if (!BCrypt.checkpw(insecureCredentials.getPassword(), credentials.getHashedPassword())) return null;
        return JWT.create().withIssuer("auth0").withClaim("email", credentials.getEmail()).sign(jwtAlgorithm);
    }

    /**
     * Verifies JWT token
     * @param token The JWT token
     * @return The email of the user, or null if the token couldn't be verified
     */
    public String verify(String token) {
        try {
            String email = jwtVerifier.verify(token).getClaim("email").asString();
            if (!repository.existsById(email)) return null;
            return email;
        } catch (JWTVerificationException e) {
            return null;
        }
    }


    /**
     * Creates credentials in repository
     * @param insecureCredentials The credentials with insecure password
     * @return True if the credentials were created, or false if they already exist
     */
    public boolean createOne(InsecureCredentials insecureCredentials) {
        if (repository.existsById(insecureCredentials.getEmail())) return false;
        String hashedPassword = BCrypt.hashpw(insecureCredentials.getPassword(), BCrypt.gensalt());
        repository.save(insecureCredentials.toCredentials(hashedPassword));
        return true;
    }

    /**
     * Updates credentials in repository
     * @param insecureCredentials The credentials with insecure password
     * @return True if the credentials were updated, or false if they couldn't be found
     */
    public boolean updateOne(InsecureCredentials insecureCredentials) {
        if (!repository.existsById(insecureCredentials.getEmail())) return false;
        String hashedPassword = BCrypt.hashpw(insecureCredentials.getPassword(), BCrypt.gensalt());
        repository.save(insecureCredentials.toCredentials(hashedPassword));
        return true;
    }

    /**
     * Deletes credentials
     * in repository
     * @param email The email of the user
     * @return True if the credentials were deleted, or false if they couldn't be found
     */
    public boolean deleteOne(String email) {
        if (!repository.existsById(email)) return false;
        repository.deleteById(email);
        return true;
    }
}
