import java.math.BigInteger;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RSA user1 = new RSA();
        RSA user2 = new RSA();

        String message = "Hello world !";

        System.out.println("Передаем открытый ключ второму пользователю: " + user1.getOK()[1]);

        BigInteger[] Crypted = user2.encrypt(user1.getOK(), message);
        StringBuilder str = new StringBuilder();

        for (BigInteger a : Crypted) {
            str.append(a);
        }
        String EncryptedMessage = str.toString();
        System.out.println("Зашифрованное сообщение: " + EncryptedMessage);

        System.out.println();

        System.out.println("Расшифровка переданное хуеты вторым пользователем...:");

        String Decryption = user1.decrypt(Crypted);

        System.out.println("Расшифрованное сообщение: " + Decryption);

    }
}
