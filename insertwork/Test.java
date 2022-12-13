import java.io.*;
import java.util.Objects;
import java.util.Scanner;
public class Test {

    public static void OutFile(File out, String text) {
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

    public static String ChooseAnswer(String text, String answer){
        String[] answerMas = answer.split(",");
        Scanner in = new Scanner(System.in);
        System.out.println(text);
        System.out.println("Choose one of the correct options: ");
        System.out.println("Type 1, 2 or 3 to select an answer and then press Enter");
        System.out.println("1." + answerMas[0] + " 2." + answerMas[1] + " 3."+ answerMas[2]);
        String correctAnswer = "";
        int chooseAnswer = in.nextInt();
        switch (chooseAnswer) {
            case 1 -> correctAnswer = answerMas[0];
            case 2 -> correctAnswer = answerMas[1];
            case 3 -> correctAnswer = answerMas[2];
        }
        return correctAnswer;
    }

    public static int Result(File out, File question, File result, File correct) {
        int amountFalse = 0;
        try (BufferedReader br_quest = new BufferedReader(new FileReader(question)))  {
            try (BufferedReader br_res = new BufferedReader(new FileReader(result))) {
                try (BufferedReader br_cor = new BufferedReader(new FileReader(correct))) {
                    String text;
                    String answer;
                    String correctAnswer;
                    while ((text = br_quest.readLine()) != null && (answer = br_res.readLine()) != null && (correctAnswer = br_cor.readLine()) != null) {
                        String[] strings = text.split(" ");
                        for (String word : strings) {
                            if (word.matches("____")) {
                                String thisAnswer = ChooseAnswer(text, answer);
                                if(Objects.equals(thisAnswer, correctAnswer)) {
                                    text = text.replace("____", thisAnswer);
                                    text += "    CORRECT!";
                                }
                                else {
                                    text = text.replace("____", "|" + thisAnswer + "|");
                                    text += "    FALSE!";
                                    amountFalse++;
                                }
                            }
                        }
                        OutFile(out, text);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return amountFalse;
    }

    public static void OutputResult(File out, int fail){
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

    public static void main(String[] args) {
        File question = new File(args[0]);
        File result = new File(args[1]);
        File correct = new File(args[2]);
        File out = new File("output.txt");
        int amountFails = Result(out, question, result, correct);
        OutputResult(out, amountFails);
        out.deleteOnExit();
    }
}
