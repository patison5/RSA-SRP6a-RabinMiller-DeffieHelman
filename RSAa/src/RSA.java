import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger[] O_K = new BigInteger[2];
    private BigInteger[] S_K = new BigInteger[2];

    RSA() {
        System.out.println("Инициализация протокола RSA");

        //Генерируем 2 простых числа длиной 1024
        Random rnd = new Random();
        int bitLength = 1024;

        BigInteger p = BigInteger.probablePrime(bitLength, rnd);
        BigInteger g = BigInteger.probablePrime(bitLength, rnd);

        // n = p*g
        BigInteger modulo = p.multiply(g);

        //Ф-ия Эйлера: Ф(modulo) = (p-1) * (g-1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(g.subtract(BigInteger.ONE));

        // e - открытая экспонента, простое число в диапазоне: 1<e<phi, причём НОД с phi = 1
        BigInteger e = BigInteger.valueOf(2);
        while ((e.compareTo(phi) < 0) & !phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }

        // g - секретная экспонента, равная обратному к числу e по модулю phi(modulo)
        // т.е. d*e = 1 (mod phi(modulo)) или d = e^(-1) (mod phi(modulo))
        BigInteger d = e.modInverse(phi);

        O_K[0] = e;
        O_K[1] = modulo;

        S_K[0] = d;
        S_K[1] = modulo;
    }

    public BigInteger[] getOK() {
        return new BigInteger[] {
                O_K[0],
                O_K[1]
        };
    }

    public BigInteger[] encrypt(BigInteger[] O_K, String str) {
        System.out.println("Шифруем сообщение: " + str);

        char[] str_chars = str.toCharArray();
        BigInteger[] encrypted_struct = new BigInteger[str_chars.length];

        for (int i = 0; i < str_chars.length; i++) {
            // c = str^e mod n
            encrypted_struct[i] = (BigInteger.valueOf((int) str_chars[i])).modPow(O_K[0], O_K[1]);
        }
        return encrypted_struct;
    }

    public String decrypt(BigInteger[] encryptedStr) {
        char[] strChars = new char[encryptedStr.length];
        StringBuilder decrypted_struct = new StringBuilder();

        for (int i = 0; i < encryptedStr.length; i++) {
            // m = c^d mod n
            strChars[i] = (char) encryptedStr[i].modPow(S_K[0], S_K[1]).intValue();
            decrypted_struct.append(strChars[i]);
        }

        return decrypted_struct.toString();
    }
}
