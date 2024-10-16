package RSA;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SignInvoice {
	public static File signInvoice(String path, String privateKeyBase64) throws Exception {
	    // Đọc nội dung file hóa đơn gốc
	    byte[] documentBytes = Files.readAllBytes(Paths.get(path));
	    
	    // Chuyển private key Base64 sang đối tượng PrivateKey
	    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64.getBytes(StandardCharsets.UTF_8));
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
	    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

	    // Ký file
	    Signature signature = Signature.getInstance("SHA256withRSA");
	    signature.initSign(privateKey);
	    signature.update(documentBytes);
	    byte[] signatureBytes = signature.sign();

	    // Ghi chữ ký vào file mới cùng thư mục
	    File dir = new File(path).getParentFile();
	    String fileName = new File(path).getName();
	    String signedFileName = fileName.replace(".pdf", "") + "-signed.pdf"; 
	    File signedFile = new File(dir, signedFileName);
	    
	    Files.write(signedFile.toPath(), signatureBytes);
	    
	    return signedFile;
	}
	
	public static void main(String[] args) throws Exception {
		String prKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzdSPYtx4Woo2FBJIf9bbjs0xTUUrnMqAR2N6XWDU28xxNCMmpNgJdlU3T6YtnB6eCgz3b1riq4mYOuUUKeP2wSRpOLrYsB36hpLdubvc4xPHT2P96AhCuwvGrzYrzeCoYEcpdFCPn6SKKEhr37lYGc+nHcXvKCc+5HDmEADCV00bJCaG95mfJm93szCuWRg53+/XDKLxELPhPInPN6aePwOwUhOR9lpfmFS34Hio3zlcL1kxdwaCRD+dQrEoIXzbMll1ZgBji9WdU7a0tcLePHI7ROe02+DAhF8oIypZtEVUvlP1i+WT1GPFcjScd0PueaKi0pjgb5Szv8Tefh07RAgMBAAECggEAHfyZ89GNdozNBwgffrWpTN0eCOIRmRZthu3tK+7yXW88MGcig2Lh/E9BhQKGZ73wsK9Le9uiWfOgyB8POVPEWFuuyJenmy3ZbBtBB3QFXXgTTKn67qE/tmQey+15pXtT1VfLr8f4w/zB575kwT8TItrlmo0M+VKwYickOTlq5O3z4MI4o2NawNrLNK3OTRPFKo3kR0eTnJ9X5CcPyDSyeneqHXVWW/nHKFmjN0phUJzP17JPT3ZH0Bq+ZnipIYFvYV4azYoeli4XpOIdsRmiC5lfKlWb2mjwVfnoTEWAoR8uvg6S6tIzaeUV51H/fR1Uss9j6opv+l3vTEmZTmg+bQKBgQDAopq7ZtQS4a3Gz6gUn6Ccc5xIDcKPkRdEnT1FyqtwuzZcqWr1Z62VvczD27ykLgoz6aeuzCl4f6dR0i2kKscutPmLr9nJFPF1538VoOflRb5DMout4TJmhOxAqXwDxqcijJnDswHXe9SkNm5NUT6iAJqMl2Eu2worTn+ncKkMuwKBgQDufODmQXi4ENOmTzbErf1EPNPXkpIsIkQjxn3JEKxriozWaTh35y04D7f0pNFYJkhwi7mdOTFQthvfaB8jrQzfL/W9lx0WLH0iAVHTM5/qFC0dsPF5kP21/7P7HrKsxVLM/QuFBZH/gPgw5nG56RKBomKJMT+luCBTm6wDzg0/4wKBgEIHeoJB/O9769T52OM41BTMjAUmskoDsbYTL618isdZZIjeiYsUvqtVI5r+9upP3a9tp8yCrrRfDTs+j+Z1w1B/OAppfH0wcJO5hhYj0VkFTvI5oSc4yc3aQiDn91pT/XYaO5qZAUibaYfgIG7gKlfdXRxTxzwIWLP/dEIwm8GBAoGBAK4DKmp3vSi5BmWkCPdLaLAAniX+SW+wdP2RmDg8VUKGrqrHza8XXBQ6y+PS2vkxvct4+C9zCru0lPPW2lnCq9CyAuGaQFvsjEVipxyu33EB1cWDVT55f9XrRJzn0kFkHT17KYue+99tcCrmCnc3TAyQussVxzYY+mVnAgUotNw3AoGAWOQL4Au203xRMQTqK6KZ3wE1MPmbvQokF6Fo9Szt9oQjPlu3F8zaT208Uo7Pv3Fk/wKGrUF2hquiH2tpjB/N9ujy1SRUYg0LSuT/80r7i5iZvPQSLTJ6l5yObvVG6ECAwCZWUERtLnpzmmE+LxBpG93QaRXpowlk1O6cSpM9StM=\r\n"
				+ "";
		String path1 = "D:\\TEST\\Invoice\\invoice1.txt";
		signInvoice(path1, prKey);
	}
}
