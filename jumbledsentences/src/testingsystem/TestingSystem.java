package testingsystem;

import testingsystem.data.Question;
import testingsystem.data.dao.TestsDAO;
import testingsystem.data.User;

import java.sql.SQLException;
import java.util.*;

public class TestingSystem {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Консольное демо теста, где нужно расставить смешанные слова.
     * @param student Проверяемый студент
     * @throws ClassNotFoundException Проблемы с доступом к БД
     * @throws SQLException Ошибка в SQL в DAO классе
     */
    public static void jumbledSentencesTestConsole(User student) throws ClassNotFoundException, SQLException {
        System.out.println("Привет, " + student.getUserName() + "! Удачи в прохождении теста!\n" +
                            "Тебе нужно расставить слова в предложении в правильном порядке.\n");
        TestsDAO testsDAO = new TestsDAO();
        List<Question> questions = testsDAO.getQuestions(student.getTestLessonId());
        if (questions.isEmpty()) {
            System.out.println("Раздел находится в разработке:)");
            return;
        }
        int correctAnswers = 0;
        // Для успешного прохождения теста нужно 90% правильных ответов
        final int passingScore = (int) (0.9 * questions.size());

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
        }

        if(correctAnswers >= passingScore) {
            System.out.println("Поздравляем с успешным прохождением теста!");
            student.setTestLessonId(student.getTestLessonId() + 1);
        }
        else
            System.out.println("К сожалению, вы не прошли:( Удачи в следующий раз!");
    }

    /**
     * Перемешивает слова в строке
     * @param sentence Строка, в которой нужно перемешать слова
     * @return Строка с перемешанными словами
     */
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
