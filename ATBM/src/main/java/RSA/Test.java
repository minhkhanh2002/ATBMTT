package RSA;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Nhập private key từ bàn phím
            System.out.println("Nhập private key dưới dạng chuỗi Base64:");
            String privateKeyInput = scanner.nextLine();
            privateKeyInput = privateKeyInput.replace("-----BEGIN PRIVATE KEY-----", "")
                                             .replace("-----END PRIVATE KEY-----", "");

            // Chuyển đổi từ chuỗi Base64 sang mảng byte
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyInput.getBytes(StandardCharsets.UTF_8));

            // Tạo private key từ mảng byte
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Nhập hóa đơn từ bàn phím
            System.out.println("Nhập hóa đơn:");
            String documentToSign = scanner.nextLine();
            byte[] documentBytes = documentToSign.getBytes(StandardCharsets.UTF_8);

            // Ký hóa đơn
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(documentBytes);
            byte[] signatureBytes = signature.sign();

            // Hiển thị chữ ký dưới dạng chuỗi Base64
            String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);
            System.out.println("Chữ ký của hóa đơn: " + signatureBase64);

            // Lưu trữ chữ ký (signatureBytes) để sau này giải mã

            // Nhập public key từ bàn phím
            System.out.println("Nhập public key dưới dạng chuỗi Base64:");
            String publicKeyInput = scanner.nextLine();
            publicKeyInput = publicKeyInput.replace("-----BEGIN PUBLIC KEY-----", "")
                                          .replace("-----END PUBLIC KEY-----", "");

            // Chuyển đổi từ chuỗi Base64 sang mảng byte
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyInput.getBytes(StandardCharsets.UTF_8));

            // Tạo public key từ mảng byte
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = (PublicKey) keyFactory.generatePublic(publicKeySpec);

            // Nhập chữ ký từ bàn phím
            System.out.println("Nhập chữ ký dưới dạng chuỗi Base64:");
            String inputSignature = scanner.nextLine();
            byte[] inputSignatureBytes = Base64.getDecoder().decode(inputSignature.getBytes(StandardCharsets.UTF_8));

            // Xác minh chữ ký
            Signature verifySignature = Signature.getInstance("SHA256withRSA");
            verifySignature.initVerify((java.security.PublicKey) publicKey);
            verifySignature.update(documentBytes);
            boolean verified = verifySignature.verify(inputSignatureBytes);

            if (verified) {
                System.out.println("Chữ ký hợp lệ. Hóa đơn đã được xác minh.");
            } else {
                System.out.println("Chữ ký không hợp lệ. Hóa đơn không được xác minh.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi.");
        } finally {
            scanner.close();
        }
}
}
