import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File input = new File("inputFile.txt");
        File output = new File("outputFile.txt");

        if (input.createNewFile())
            System.out.println("File created: " + input.getName());

        if (output.createNewFile())
            System.out.println("File created: " + output.getName());

        try {
            EnctyptionAndDecryption endec = new EnctyptionAndDecryption();
            endec.caesarEncrypt();
            endec.decrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
