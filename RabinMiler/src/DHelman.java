import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class DHelman {
    int publicKey1 = 0;
    int publicKey2 = 0;
    int privateKey = 0;
    int fullKey = 0;

    DHelman (int publicKey1, int publicKey2, int privateKey) {
        this.publicKey1 = publicKey1;
        this.publicKey2 = publicKey2;
        this.privateKey = privateKey;
    }

    int generate_partial_key () {
        BigInteger full_key = BigInteger.valueOf(this.publicKey1).pow(this.privateKey);
        full_key = full_key.mod(BigInteger.valueOf(this.publicKey2));

        return full_key.intValue();
    }

    int generateFullKey (int partialKey) {
        BigInteger full_key = BigInteger.valueOf(partialKey).pow(this.privateKey);
        full_key = full_key.mod(BigInteger.valueOf(this.publicKey2));
        this.fullKey = full_key.intValue();

        return full_key.intValue();
    }

    String encryptMessage (String message) {
        int shift = this.fullKey;
        StringBuilder output = new StringBuilder(message);;

        for (int i = 0; i < message.length(); i++) {
//            int k = message.charAt(i);
//            System.out.println("F: " + message.charAt(i) + ":" + k);
//            if ((k >= 65) & (k <= 122)) {
//                int charValue = (int)message.charAt(i) + shift;
//                if (charValue > 122) charValue -= 58;
//
//                output.setCharAt(i, (char)charValue);
//            }
            int charValue = (int)message.charAt(i) + shift;
            output.setCharAt(i, (char)charValue);
        }

        return output.toString();
    }

    String decryptMessage(String encryptedMSG) {
        String message = "";

        for (int i = 0; i < encryptedMSG.length(); i++)
            message = message + (char)((int)encryptedMSG.charAt(i) - this.fullKey);

        return message;
    }
}
