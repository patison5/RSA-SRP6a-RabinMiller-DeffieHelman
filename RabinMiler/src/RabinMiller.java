import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RabinMiller {


    private Random random = new Random();
    int num = 12345;
    int a = 5;


    RabinMiller() {
        System.out.println("---------------------------");
        System.out.println("Инициализация Рабина Миллера");

//        int d = num - 1;
//        int s = 0;
//
//        while (d % 2 == 0) {
//            s++;
//            d /= 2;
//        }
//
//        int x = (int) Math.pow(a, d);
//        x = x % num;
//
//        if ((x == 1) || (x == num - 1)) {
//            System.out.println("That's probably prime number");
//        }
//
//        int r = 1;
//        while (r < (s - 1)) {
//            x = (int) Math.pow(x, 2);
//            x = x % num;
//
//            if (x == 1) System.out.println("Composite");
//            if (x == (num - 1)) System.out.println("probably prime");
//        }
//
//        System.out.println("if nothin above was printed - Num is prime");

//        System.out.println(MillerRabinTest(BigInteger.valueOf(11), 12));
        System.out.println("Проверяеем число 312 на простоту 12 раз: " + myRabin(312, 12));

        int k = getPrime();
        System.out.println("Получим простое число с помощью генератора простых чисел: " + k);
        System.out.println("Проверяеем число " + k + " на простоту 5 раз: " + myRabin(k, 5));
        System.out.println("---------------------------\n");
    }



    public boolean MillerRabinTest(BigInteger n, int k)
    {
        // если n == 2 или n == 3 - эти числа простые, возвращаем true
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3)))
            return true;

        // если n < 2 или n четное - возвращаем false
        if (n.compareTo(BigInteger.valueOf(2))<0 || n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            return false;

        // представим n − 1 в виде (2^s)·t, где t нечётно, это можно сделать последовательным делением n - 1 на 2
        BigInteger t = n.subtract(BigInteger.ONE);

        int s = 0;

        while (t.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))

        {
            t = t.divide(BigInteger.valueOf(2));
            s += 1;
        }

        // повторить k раз
        for (int i = 0; i < k; i++)
        {
            // выберем случайное целое число a в отрезке [2, n − 2]
            SecureRandom rng = new SecureRandom();

            byte[] _a = new byte[n.toByteArray().length];

            BigInteger a;

            do
            {
                rng.nextBytes(_a);
                a = new BigInteger(_a);
            }
            while (a.compareTo(BigInteger.valueOf(2)) <0 || a.compareTo(n.subtract(BigInteger.valueOf(2))) >= 0);

            // x ← a^t mod n, вычислим с помощью возведения в степень по модулю
            BigInteger x = a.modPow(t, n);

            // если x == 1 или x == n − 1, то перейти на следующую итерацию цикла
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;

            // повторить s − 1 раз
            for (int r = 1; r < s; r++)
            {
                // x ← x^2 mod n
                x = x.modPow(BigInteger.valueOf(2), n);

                // если x == 1, то вернуть "составное"
                if (x.equals(BigInteger.ONE))
                    return false;

                // если x == n − 1, то перейти на следующую итерацию внешнего цикла
                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            if (!Objects.equals(x, n.subtract(BigInteger.ONE)))
                return false;
        }

        // вернуть "вероятно простое"
        return true;
    }

    public boolean myRabin (int n, int k) {
        if (n == 2 || n == 3) return  true;
        if ((n < 2) || (n % 2) == 0) return false;

        int t = n - 1;
        int s = 0;

        while (t % 2 == 0) {
            t /= 2;
            s += 1;
        }

        for (int i = 0; i < k; i++) {
            int rng  = 2 + (int)(Math.random() * n - 2);

            int a = rng;
            int x = (int) (Math.pow(a, t) % n);

            if ((x == 1) || (x == n - 1)) continue;

            for (int r = 1; r < s; r++) {
                x = (int) (Math.pow(x, 2) % n);

                if (x == 1) return false;
                if (x == (n - 1)) break;
            }

            if (x == (n - 1)) return false;
        }

        return true;
    }

    private Integer getPrime(){
        List<Integer> PrimeList = new ArrayList<>();
        int n = 1000;

        boolean [] isPrime = new boolean[n];
        isPrime[0] = isPrime[1] = false; //0 и 1 - не простые числа

        for(int i = 2; i < n; i++){
            isPrime[i] = true; // пусть изначально все просте
        }

        //Если число простое, то любое произведение с ним даёт не простое число, такие отсеиваются
        for(int i = 2; i < isPrime.length; i++)
            if (isPrime[i])
                for (int j = 2; j * i < isPrime.length; j++)
                    isPrime[i * j] = false;

        for (int i = 2; i < n; i++)
            if (isPrime[i])
                PrimeList.add(i);

        return PrimeList.get(random.nextInt(PrimeList.size()));
    }
}
