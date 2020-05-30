package Server.Utilities;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

public class Userausthenticationtests {

    @Test
    public void testPasswordhash() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        String password = "String";


        String stalt = UserAuthentication.getSalt() ;

        String hassedpass1 =  UserAuthentication.generateHash(password, stalt);
        String hassedpass2 =  UserAuthentication.generateHash(password, stalt);


        assertTrue(UserAuthentication.compareHashes(hassedpass1,hassedpass2));



    }

    @Test
    public void testPasswordhashdiffrentpasswords() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        String password = "String";
        String password1 = "String2";

        String stalt = UserAuthentication.getSalt() ;
        String stalt1 = UserAuthentication.getSalt() ;

        String hassedpass1 =  UserAuthentication.generateHash(password, stalt);
        String hassedpass2 =  UserAuthentication.generateHash(password1, stalt1);


        assertFalse(UserAuthentication.compareHashes(hassedpass1,hassedpass2));



    }

    @Test
    public void testPasswordhashsamepasswordsdfiirentsalt() throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        String password = "String";


        String stalt = UserAuthentication.getSalt() ;
        String stalt1 = UserAuthentication.getSalt() ;



        String hassedpass1 =  UserAuthentication.generateHash(password, stalt);
        String hassedpass2 =  UserAuthentication.generateHash(password, stalt1);
        System.out.println(hassedpass1);
        System.out.println(hassedpass2);
        assertFalse(UserAuthentication.compareHashes(hassedpass1,hassedpass2));

    }
//this is just to see if a it created a session token 
    @Test
    public void testsessiontoken()
    {
        String  token = UserAuthentication.generateSessionToken(1);
        System.out.println(token);
    }

}
