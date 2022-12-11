import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TestAPI {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        System.out.println("hi");
        TestsDAO testsDAO = new TestsDAO();
        //testsDAO.createTable();
        //testsDAO.importSalesFromCSV("C:\\Users\\Xenia\\Desktop\\Java croc school\\learnit\\jumbledsentences\\resources\\tempCSV\\temp.csv");
        User u = new User(1, "xen", 1);
        TestingSystem.jumbledSentencesTest(u);
        System.out.println(u.getTestThemeId());
    }
}
