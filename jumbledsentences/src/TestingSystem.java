import java.sql.SQLException;
import java.util.*;

class TestingSystem {

    static void jumbledSentencesTest(User student) throws ClassNotFoundException, SQLException {
        System.out.println("Привет! Удачи в прохождении теста!\n" +
                "Тебе нужно расставить слова в предложении в правильном порядке.");
        try (Scanner scanner = new Scanner(System.in)){
            TestsDAO testsDAO = new TestsDAO();
            List<Question> questions = testsDAO.getQuestions(student.getTestThemeId());
            int correctAnswers = 0;
            for (Question q : questions) {
                System.out.println(q.ruSentence());
                System.out.println(getJumbledSentence(q.engSentence()));
                String answer = scanner.nextLine().trim();
                if(answer.equals(q.engSentence())){
                    System.out.println("Правильно!");
                    ++correctAnswers;
                }
                else
                    System.out.println("Неправильно:( Правильный ответ:\n" + q.engSentence());
                System.out.println();

                if(correctAnswers >= 0.9 * questions.size())
                    student.setTestThemeId(student.getTestThemeId() + 1);
            }
        }
    }

    private static String getJumbledSentence(String sentence){
        List<String> words = Arrays.asList(sentence.split(" "));
        Collections.shuffle(words);
        StringBuilder jumbled = new StringBuilder();
        for (String word : words) {
            jumbled.append(word).append(" ");
        }
        return jumbled.toString().trim();
    }

}
