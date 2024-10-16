package RSA;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Base64;

public class KeyPairGeneratorUtil {
	 public static void main(String[] args) throws Exception {
	        
	        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	        keyGen.initialize(2048);
	        
	        KeyPair pair = keyGen.generateKeyPair();      
	        PrivateKey privateKey = pair.getPrivate();
	        java.security.PublicKey publicKey = pair.getPublic();
	        
	        Base64.Encoder encoder = Base64.getEncoder();
	        
	        String publicKeyString = encoder.encodeToString(publicKey.getEncoded());
	        String privateKeyString = encoder.encodeToString(privateKey.getEncoded());
	       
	        System.out.println("Public key: " + publicKeyString);
	        System.out.println("Private key: " + privateKeyString);
	    }
}
