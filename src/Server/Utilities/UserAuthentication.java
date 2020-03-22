package Server.Utilities;

/*
* Contains helper methods for validating a session token, issuing a new session token, and hashing a value (for
* creating a new user, and checking a provided password hash)
* */
public class UserAuthentication {
    public boolean isValidSessionToken(String token){
        return false;
    }
    public String generateSessionToken(String userName){
        return "FakeToken";
    }
    public String generateHash(String value, int salt){
        return "FakeHash";
    }
    public boolean compareHashes(String hashA, String hashB){
        return false;
    }
}
