import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Test {

    public static String chooseAnswer(String text, String answer){
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

    public static int result(File question, File result, File correct) {
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
                                String thisAnswer = chooseAnswer(text, answer);
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
                        Out.outFile(text);
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

    public static void main(String[] args){
        File question = new File(args[0]);
        File result = new File(args[1]);
        File correct = new File(args[2]);

        int amountFails = result(question, result, correct);
        Out.outputResult(amountFails);
        Out.delete();
    }
}
