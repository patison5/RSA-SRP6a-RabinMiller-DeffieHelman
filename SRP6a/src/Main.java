import javax.naming.InvalidNameException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // where g equals 2

        BigInteger N = BigInteger.valueOf(1349798841);
        BigInteger g = BigInteger.valueOf(2);

        // in SRP6a, k = H(N, g)
        BigInteger k = SHA256.hash(N, g);

        Server server = new Server(N, g, k);

        while (true) {
            System.out.println("Choose:");
            System.out.println("1. Registration");
            System.out.println("2. Login");

            Scanner input = new Scanner(System.in);
            int ch = input.nextInt();
            switch (ch) {

                // Регистрация
                case 1: {
                    System.out.println("Login: ");
                    String login = input.next();

                    System.out.println("password: ");
                    String password = input.next();

                    Client client = new Client(N, g, k, login, password);

                    client.set_SXV();
                    String s = client.get_s();
                    BigInteger v = client.get_v();
                    try {
                        server.set_ISV(login, s, v);
                        //Если в мапе есть имя, то:
                    } catch (InvalidNameException e) {
                        System.out.println("Login is already used!");
                    }
                    break;
                }

                // Вход
                case 2: {
                    System.out.println("Login: ");
                    String login = input.next();

                    System.out.println("Password: ");
                    String password = input.next();

                    Client client = new Client(N, g, k, login, password);


                    BigInteger A = client.gen_A();
                    try {
                        server.set_A(A);

                    } catch (IllegalAccessException e) {
                        System.out.println("A = 0");
                        break;
                    }

                    try {
                        String s = server.get_s(login);
                        BigInteger B = server.create_B();
                        client.receiveSaltAndB(s, B);
                    } catch (IllegalAccessException e) {
                        System.out.println("Incorrect login or password");
                        break;
                    }

                    try {
                        client.gen_u();
                        server.gen_u();
                    } catch (IllegalAccessException e) {
                        System.out.println("Connection lost!");
                        break;
                    }

                    client.generateSessionKey();
                    server.generateSessionKey();

                    BigInteger server_R = server.create_M(client.ClientConfirm());

                    if (client.compare_R_C(server_R))
                        System.out.println("Connected!");
                    else
                        System.out.println("Incorrect login or password");
                    break;
                }
                default:
                    return;
            }
            System.out.println();
        }
    }
}