package Server.Utilities;
import java.io.UnsupportedEncodingException;
import java.util.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;

import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;




/**
 * Contains helper methods for validating a session token, issuing a new session token, and hashing a value (for
 * creating a new user, and checking a provided password hash)
 * */
public class UserAuthentication {

    //The time out period for the token
    private static int timeoutperiod = 1;


    /** A map of <accessToken, unhashed value>
     * Unhashed value: userID, uuid, ISO time of token creation where userID is an int, salt is
     * a random int, and ISO time was obtained from LocalDateTime*/
    private static HashMap<String, Long> currentSessions = new HashMap<>();



    /**
     * Gets the user id of the person to whom the access token was issued
     * @param token  the token to extract the userID from
     * @return  an int that is an exact match for the database userID or -1 if the token is
     * expired or invalid
     */
    public static int extractUserIDFromToken(String token)
    {
        String[] id = token.split("-");
        int idoutput =  Integer.parseInt(id[5]);

        if (idoutput >= 0 && isValidSessionToken(token) >= 0) {
            System.out.println(idoutput);
            return(idoutput);
        }
        else {
            return -1;
        }
    }

    /**
     * Checks if the provided token is valid
     * @param token  the token to check
     * @return  the userID of the user who requested the token or -1 if it is invalid
     */
    public static int isValidSessionToken(String token){
        long tokkentimeexpiretime = currentSessions.get(token) + timeoutperiod;
        Date date = new Date();
        long  diff = date.getTime();
        long currenttime = TimeUnit.MILLISECONDS.toMinutes(diff);

        if(tokkentimeexpiretime > currenttime) {
            String[] id = token.split("-");

            return(Integer.parseInt(id[5]));
        }
        else {
            purgeExpiredTokens();
            return(-1);
        }
    }

    /**
     * Generates a session token and stores it for validation
     * @param userid  the username of the user requesting the token
     * @return  an access token
     */
    public static String generateSessionToken(int userid){

        int useridtokenoutput =-1;
        String useridtoken = "";
        Date date = new Date();
        long  diff = date.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

        String token = UUID.randomUUID().toString().toUpperCase()
                + "-" + userid  ;

        for(Map.Entry<String, Long> entry: currentSessions.entrySet()) {
            useridtoken = (entry.getKey());
            useridtokenoutput = extractUserIDFromToken(useridtoken);
        }

        if(useridtokenoutput == userid) {
            currentSessions.remove(useridtoken);
            currentSessions.put(token, minutes);
        }

        else {
            currentSessions.put(token, minutes);
        }


        System.out.println(currentSessions);
        return token;

    }

    /**Generates a salt value
     *
     */
    public static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Arrays.toString(salt);
    }

    /**
     * Generates a hash using the provided values
     * @param password  the value to hash
     * @param salt  the salt to keep it cryptographically secure
     * @return  returns the resulting hash
     */
    public static String generateHash(String password, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {

        int iterations = 500;
        char[] chars = password.toCharArray();
        byte[] byteArr = salt.getBytes("IBM01140");

        PBEKeySpec spec = new PBEKeySpec(chars, byteArr, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(hash);
    }

    /**converts the byte array to a hex to make it easier to store
     *
     * @param array the value to be hexed
     * @return hex value
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }
        else {
            return hex;
        }
    }

    /**
     * Compares two hashs
     * @param hashA  the first hash to compare
     * @param hashB  the second hash to compare
     * @return  true if the hashes match, false otherwise
     */
    public static boolean compareHashes(String hashA, String hashB){

        if (hashA.equals(hashB)) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Removes the user's token from the list of active sessions
     * Will also remove any expired tokens from the list.
     * @param token the access token to invalidate
     */
    public static void invalidateSessionToken(String token){

        currentSessions.remove(token);
        purgeExpiredTokens();
    }


    /**
     * Removes expired tokens from the list of active sessions
     */
    private static void purgeExpiredTokens()
    {
        for(Map.Entry<String, Long> entry: currentSessions.entrySet()) {
            long expiretime = entry.getValue() + timeoutperiod ;
            String token = entry.getKey();
            Date date = new Date();
            long  diff = date.getTime();
            long currenttime = TimeUnit.MILLISECONDS.toMinutes(diff);

            if (expiretime > currenttime) {
                System.out.println("valid");
            }
            else {
                currentSessions.remove(token);
            }
        }
    }

    public static String listusers()
    {
        StringBuilder users = new StringBuilder();
        for(Map.Entry<String, Long> entry: currentSessions.entrySet()) {
            String token = entry.getKey();
            int userids = extractUserIDFromToken(token);
            users.append(userids);
        }
        return(users.toString());
    }

}
