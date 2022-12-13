
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:.\\Database\\my");
        System.out.println("Menu:");
        Scanner sc = new Scanner(System.in);
        int i = -1;
        DAOImageText daoMngaText = new DAOImageText(con);
        JFileChooser jFile = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog jDialog = super.createDialog(parent);
                jDialog.setAlwaysOnTop(true);
                return jDialog;
            }
        };
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png");
        jFile.addChoosableFileFilter(filter);
        while (true) {
            System.out.println("1. Add new record;");
            System.out.println("2. Find all records by words;");
            System.out.println("3. Update record;");
            System.out.println("4. Delete record;");
            System.out.println("5. All records;");
            System.out.println("0. Exit;");
            i = Integer.parseInt(sc.nextLine());
            if (i == 1) {
                System.out.println("Write name of work:");
                String name = sc.nextLine();
                System.out.println("Write text from picture:");
                String text = sc.nextLine();
                System.out.println("Choose picture:");
                int result = jFile.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jFile.getSelectedFile();
                    daoMngaText.addNewRecord(new ImageTextRecord(selectedFile.getPath(), name, text));
                } else {
                    System.out.println("Choose correct file!");
                }
            } else if (i == 2) {
                System.out.println("Write all words in that form( word1, word2,...):");
                String words = sc.nextLine();
                daoMngaText.lookForAllPicturesByWord(words);

            } else if (i == 3) {
                daoMngaText.updateRecord();
            } else if (i == 4) {
                daoMngaText.deleteRecord();
            } else if (i == 5) {
                daoMngaText.seeAllRecords();
            } else if (i == 0) {
                sc.close();
                break;
            }
        }
    }
}
