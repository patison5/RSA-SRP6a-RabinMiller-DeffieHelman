import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class EnctyptionAndDecryption {
    private float[] inputAlphabet = new float[64];
    private float[] outputAlphabet = new float[64];

    public void caesarEncrypt() throws Exception {
        int letterIndex = 0;
        int shift = 14;

        FileInputStream inputFile   = new FileInputStream("inputFile.txt");
        FileOutputStream outputFile = new FileOutputStream("outputFile.txt");

        InputStreamReader text      = new InputStreamReader(inputFile, "UTF-8");
        OutputStreamWriter writer   = new OutputStreamWriter(outputFile, StandardCharsets.UTF_8);


        while ((letterIndex = text.read()) != -1) {
//            System.out.println(letterIndex);
            if (letterIndex >= 1040 & letterIndex <= 1103) {
                this.inputAlphabet[letterIndex - 1040]++;
                letterIndex += shift;
                if (letterIndex > 1103) letterIndex -= 64;
                outputAlphabet[letterIndex - 1040]++;
            }
            writer.write(letterIndex);
        }

        inputFile.close();
//        outputFile.close();
        text.close();
        writer.close();
    }

    public void decrypt() throws Exception {
        Map<Character, Double> encryptedSymbolsStat = getSymbolsStat("outputFile.txt");
        Map<Character, Double> templateSymbolsStat  = getSymbolsStat("template.txt");

        Character maxTemplateSymbolElement = templateSymbolsStat.entrySet().iterator().next().getKey();
        Double maxTemplateSymbolFrequency  = templateSymbolsStat.get(maxTemplateSymbolElement);

        Character maxEncryptedSymbolElement  = encryptedSymbolsStat.entrySet().iterator().next().getKey();
        Double maxEncyptedSymbolFrequency    = encryptedSymbolsStat.get(maxTemplateSymbolElement);


//        Map<Integer, String> sortedMap =
//                encryptedSymbolsStat.entrySet().stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                                (e1, e2) -> e1, LinkedHashMap::new));


        for(Character c : encryptedSymbolsStat.keySet()) {
            System.out.print(c + ":" + encryptedSymbolsStat.get(c) + ", ");
        }
        System.out.println("");

//        Map<Character, Double> templateSymbolsStatSorted = new HashMap<>();


        List<Character> templateSymbolsStatSorted = new ArrayList<Character>();
        List<Character> encryptedSymbolsStatSorted = new ArrayList<Character>();

        encryptedSymbolsStat.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> encryptedSymbolsStatSorted.add(k.getKey()));
//                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));

        templateSymbolsStat.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> templateSymbolsStatSorted.add(k.getKey()));

//        for(Character c : encryptedSymbolsStat.keySet()) {
//            System.out.print(c + ":" + encryptedSymbolsStat.get(c) + ", ");
//        }

        System.out.println(encryptedSymbolsStatSorted);
        System.out.println(templateSymbolsStatSorted);


        Map<Character, Double> deCryptMap = new HashMap<>();
        for (int i = 0; i < encryptedSymbolsStatSorted.size(); i++) {
//            System.out.println("fuck");
            System.out.print((encryptedSymbolsStatSorted.get(i) - templateSymbolsStatSorted.get(i)) + " ");
            deCryptMap.put(encryptedSymbolsStatSorted.get(i), (double)(encryptedSymbolsStatSorted.get(i) - templateSymbolsStatSorted.get(i)));
        }
        System.out.println("");
        System.out.println(deCryptMap);





        for(Character c : encryptedSymbolsStat.keySet()) {
//            System.out.println(c + ": " + encryptedSymbolsStat.get(c));
            if(encryptedSymbolsStat.get(c) > maxEncyptedSymbolFrequency){
                maxEncryptedSymbolElement = c;
                maxEncyptedSymbolFrequency = encryptedSymbolsStat.get(c);
            }
        }
        for(Character c : templateSymbolsStat.keySet()) {
//            System.out.println(c + ": " + templateSymbolsStat.get(c));
            if(templateSymbolsStat.get(c) > maxTemplateSymbolFrequency){
                maxTemplateSymbolElement = c;
                maxTemplateSymbolFrequency = templateSymbolsStat.get(c);
            }
        }

        System.out.println("Элементы с похожей частотой (максимальной): ");
        System.out.println( maxEncryptedSymbolElement + " (" + (int)maxEncryptedSymbolElement +") : " + maxEncyptedSymbolFrequency );
        System.out.println( maxTemplateSymbolElement + " (" + (int)maxTemplateSymbolElement +") : " + maxTemplateSymbolFrequency );


        System.out.println("\nСмещение символа: " + ((int)maxEncryptedSymbolElement - (int)maxTemplateSymbolElement));

        int letterIndex = 0;
        int shift = ((int)maxEncryptedSymbolElement - (int)maxTemplateSymbolElement);

        System.out.println(shift);

        FileInputStream inputFile   = new FileInputStream("outputFile.txt");
        FileOutputStream outputFile = new FileOutputStream("result.txt");

        InputStreamReader text      = new InputStreamReader(inputFile, "UTF-8");
        OutputStreamWriter writer   = new OutputStreamWriter(outputFile, StandardCharsets.UTF_8);

        System.out.println("\nДешифрованный текст:");
        while ((letterIndex = text.read()) != -1) {
//            System.out.println(letterIndex);
            if (letterIndex >= 1040 & letterIndex <= 1103) {
                this.inputAlphabet[letterIndex - 1040]++;
                letterIndex -= deCryptMap.get((char)letterIndex);
                if (letterIndex < 1040) letterIndex += 64;
                outputAlphabet[letterIndex - 1040]++;
            }
            System.out.print((char)letterIndex);
            writer.write(letterIndex);
        }


        inputFile.close();
//        outputFile.close();
        text.close();
        writer.close();
    }

    Map<Character, Double> getSymbolsStat(String filename) throws IOException {

        FileInputStream file = new FileInputStream(filename);
        InputStreamReader text = new InputStreamReader(file, "UTF-8");

        int letter = 0;
        int count = 0;
        Map<Character, Double> cont = new HashMap<>();

        while ((letter = text.read()) != -1) {
            if (letter >= 1040 & letter <= 1103) {
                if(cont.containsKey((char)letter)){
                    cont.put((char)letter, cont.get((char)letter) + 1);
                } else {
                    cont.put((char)letter, 1.0);
                }
                count++;
            }
        }

        for(Character c : cont.keySet()) {
            cont.put(c, cont.get(c) / count);
        }
//        System.out.println(cont);
//        System.out.println(count);
        text.close();
        file.close();
        return cont;
    }
}


