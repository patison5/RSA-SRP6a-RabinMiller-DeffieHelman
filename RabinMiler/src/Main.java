import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        rabinMiller(); // Рабин миллер
        defihelman();  // деффи хельман
    }

    static void rabinMiller () {
        RabinMiller rabinMiller = new RabinMiller();
    }

    static void defihelman() {
        System.out.println("---------------------------");
        System.out.println("Инициализация алгоритма Диффи Хельмана");
        // DHelman
        DHelman dh1 = new DHelman(197, 151, 199);
        DHelman dh2 = new DHelman(197, 151, 157);

        System.out.println("Создаем двух человек");

        System.out.println("Инициализируем приватные и публичные ключи для каждого");


        System.out.println("Публичный ключ первого: " + dh1.publicKey1);
        System.out.println("Публичный ключ второго: " + dh1.publicKey2);
        System.out.println("приватный ключ первого: " + dh1.privateKey);
        System.out.println("приватный ключ второго: " + dh2.privateKey);

        System.out.println("Создаем частичне ключи");
        int partKey1 = dh1.generate_partial_key();
        int partKey2 = dh2.generate_partial_key();

        System.out.println("частичный ключ первого: " + partKey1); // 147
        System.out.println("частичный ключ второго: " + partKey2); // 66

        int key1 = dh1.generateFullKey(partKey2);
        int key2 = dh2.generateFullKey(partKey1);

        System.out.println("Ключ дешифрования для обоих: " + key1); //75


        System.out.println("Отправляем сообщение");
        System.out.println("Зашифрованное сообщение: " + dh1.encryptMessage("hello world"));
        System.out.println("Дешифрованное сообщение: " + dh1.decryptMessage(dh1.encryptMessage("hello world")));
        System.out.println("---------------------------\n");
    }
}
