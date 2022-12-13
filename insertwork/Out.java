import java.io.*;

public class Out {
    private static final File out = new File("output.txt");

    Out(){}

    protected static void outFile(String text) {
        try {
            FileWriter writer = new FileWriter(out, true);
            BufferedWriter bufferWrite = new BufferedWriter(writer);
            bufferWrite.write(text);
            bufferWrite.newLine();
            bufferWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void outputResult(int fail){
        System.out.println("Your test result:");
        try (BufferedReader br = new BufferedReader(new FileReader(out))) {
            String text;
            while ((text = br.readLine()) != null) {
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Number of mistakes: " + fail);
    }

    protected static void delete(){
        out.deleteOnExit();
    }
}
