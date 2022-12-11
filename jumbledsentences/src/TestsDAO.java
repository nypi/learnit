import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class TestsDAO {

    private final String JDBC_URL = "jdbc:h2:file:./jumbledsentences/resources/database/testsDB";
    private final String JDBC_CLASSNAME = "org.h2.Driver";
    private final String username = "jumbled";
    private final String password = "tests";


    TestsDAO() throws ClassNotFoundException {
        Class.forName(JDBC_CLASSNAME);

    }

    void createTable() throws SQLException {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = """
                     CREATE TABLE IF NOT EXISTS TESTS
                     (id IDENTITY NOT NULL PRIMARY KEY,
                      theme_id INTEGER NOT NULL,
                      theme VARCHAR2(255) NOT NULL,
                      eng_sentence VARCHAR2(255) NOT NULL,
                      ru_sentence VARCHAR2(255) NOT NULL
                      );
                  """;
            statement.executeUpdate(sql);

        }
    }

    List<Question> getQuestions(int theme_id) throws SQLException {
        List<Question> testQuestions = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM TESTS WHERE theme_id = " + theme_id + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                testQuestions.add(new Question(resultSet.getInt("theme_id"), resultSet.getString("theme"),
                                    resultSet.getString("eng_sentence"), resultSet.getString("ru_sentence")));
            }

        }
        return testQuestions;
    }

    /**
     * imports data from a csv file
     * @param path to a csv file
     */
    void importSalesFromCSV(String path) throws SQLException, IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String currentLine;
            while ((currentLine = in.readLine()) != null){
                String[] values = currentLine.split(",");
                createQuestion(new Question(Integer.parseInt(values[0]), values[1], values[2], values[3]));
            }
        }

    }


    /**
     * adds a new row
     * @param q
     */
    void createQuestion(Question q) throws SQLException {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO TESTS (theme_id, theme, eng_sentence, ru_sentence) VALUES (" + q.theme_id() + ", '"
                    + q.theme() + "', '" + q.engSentence() + "', '" + q.ruSentence() + "');";
            statement.executeUpdate(sql);
        }
    }

}
