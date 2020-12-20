import javax.naming.InvalidNameException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server {
    private BigInteger N; // ���������� �������
    private BigInteger g; // ��������� �� ������ N
    private BigInteger k; // ��������-���������
    private BigInteger v; // �����������
    private BigInteger A; // �� �������
    private BigInteger B; // �� �������
    private BigInteger b; // ��������� �������� �������
    private BigInteger u; // ���������
    private BigInteger K; // ����� ���-���� ������
    private String I; // �����
    private String s; // ����
    private Map < String, Pair < String, BigInteger >> database = new HashMap < >(); //�� �������� � �������

    public Server(BigInteger N, BigInteger g, BigInteger k) {
        this.N = N;
        this.g = g;
        this.k = k;
    }

    //�������� � �� ����� � ����� � ������������� �� �������
    public void set_ISV(String I, String s, BigInteger v) throws InvalidNameException {
        if (!database.containsKey(I)) {
            database.put(I, new Pair < >(s, v));
        } else throw new InvalidNameException();
    }

    //������� �� ������� ��� ��:
    public void set_A(BigInteger A) throws IllegalAccessException {
        // A != 0
        if (A.equals(BigInteger.ZERO)) throw new IllegalAccessException();
        else this.A = A;
    }

    //������ �� �����:
    public BigInteger create_B() {
        // b - ��������� ������� �����
        b = new BigInteger(1024, new Random());

        // B = (k*v + g^b mod N) mod N
        B = (k.multiply(v).add(g.modPow(b, N))).mod(N);
        return B;
    }

    //������� ��������� �� � � �
    public void gen_u() throws IllegalAccessException {
        // u = H(A, B)
        u = SHA256.hash(A, B);
        // u != 0
        if (u.equals(BigInteger.ZERO)) throw new IllegalAccessException();
    }

    //������ ������ ���� � ������������� ��� ������, ����������� ��� �����������
    public String get_s(String I) throws IllegalAccessException {
        if (database.containsKey(I)) {
            this.I = I;
            s = database.get(this.I).first;
            v = database.get(this.I).second;
            return s;
        } else throw new IllegalAccessException();
    }

    //������ �� ����� ������� ��� �� ��������� ����� ���� ������
    public void generateSessionKey() {
        // S = (A*(v^u mod N))^b mod N
        BigInteger S = A.multiply(v.modPow(u, N)).modPow(b, N);
        // K = H(S)
        K = SHA256.hash(S);
    }

    //������ � ���� ��������� M ��������� ���� ����� K, � ��������� ��������� c M_C.
    public BigInteger create_M(BigInteger M_C) {
        // M = H(H(N) xor H(g), H(I), s, A, B, K)
        BigInteger M_S = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(I), s, A, B, K);
        // R = H(A, M, K)
        if (M_S.equals(M_C)) return SHA256.hash(A, M_S, K);
        else return BigInteger.ZERO;
    }

    //������� � ���� ���������: ���� � �������������
    private class Pair < A, B > {
        A first;
        B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }
}