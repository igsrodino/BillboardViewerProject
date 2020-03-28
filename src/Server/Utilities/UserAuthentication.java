package Server.Utilities;

import java.util.Map;

/**
* Contains helper methods for validating a session token, issuing a new session token, and hashing a value (for
* creating a new user, and checking a provided password hash)
* */
public class UserAuthentication {
    private static Map<String, String> currentSessions;

    /**
     * Checks if the provided token is valid
     * @param token  the token to check
     * @return  True if the session is valid, false otherwise
     */
    public static boolean isValidSessionToken(String token){
        // Calls this.purgeExpiredTokens

        return false;
    }

    /**
     * Generates a session token and stores it for validation
     * @param userName  the username of the user requesting the token
     * @return  an access token
     */
    public static String generateSessionToken(String userName){
        return "FakeToken";
    }

    /**
     * Generates a hash using the provided values
     * @param value  the value to hash
     * @param salt  the salt to keep it cryptographically secure
     * @return  returns the resulting hash
     */
    public static String generateHash(String value, int salt){
        return "FakeHash";
    }

    /**
     * Compares two hashs
     * @param hashA  the first hash to compare
     * @param hashB  the second hash to compare
     * @return  true if the hashes match, false otherwise
     */
    public static boolean compareHashes(String hashA, String hashB){
        return false;
    }

    /**
     * Removes the user's token from the list of active sessions
     * Will also remove any expired tokens from the list.
     * @param token
     */
    public static void invalidateSessionToken(String token){
        // Calls this.purgeExpiredTokens
    }

    /**
     * Removes expired tokens from the list of active sessions
     */
    private void purgeExpiredTokens(){

    }
}
