import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DAOImageText {
    private final Connection con;

    public DAOImageText(Connection con) {
        this.con = con;
    }

    public void addNewRecord(ImageTextRecord record) throws SQLException, IOException {
        try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO ImageText(Picture,NameWork,TextOfPicture) VALUES ( ?,?,? );");) {
            try (FileInputStream fileInputStream = new FileInputStream(record.getPicturePath())) {
                preparedStatement.setBinaryStream(1, fileInputStream);
                preparedStatement.setString(2, record.getName());
                preparedStatement.setString(3, record.getTextForSave());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void lookForAllPicturesByWord(String words) throws SQLException, IOException {
        try (Statement statement = con.createStatement()) {
            words = words.replaceAll(" ", "");
            words = words.replaceAll("'", "''");
            String[] allWord = words.split(",");
            int i = 1;
            ArrayList<Image> images = new ArrayList<>();
            for (String word : allWord) {
                System.out.println("Results with: " + word.toUpperCase());
                String strQuery = "SELECT * FROM ImageText WHERE TextOfPicture LIKE '%" + word.toUpperCase() + "%';";
                ResultSet resultSet = statement.executeQuery(strQuery);
                while (resultSet.next()) {
                    images.add(ImageIO.read(resultSet.getBinaryStream(2)));
                    System.out.println(i + ": Name of work: " + resultSet.getString(3) + " Saved Text: " + resultSet.getString(4));
                    i++;
                }
            }
            if (i != 1) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Choose number of row: ");
                i = sc.nextInt();
                Image newImage;
                if (images.get(i - 1).getWidth(null) < images.get(i - 1).getHeight(null)) {
                    newImage = images.get(i - 1).getScaledInstance(500, 700, Image.SCALE_SMOOTH);
                } else {
                    newImage = images.get(i - 1).getScaledInstance(700, 500, Image.SCALE_SMOOTH);
                }
                ImageIcon newIcon = new ImageIcon(newImage);
                JLabel label = new JLabel(newIcon);
                JOptionPane.showConfirmDialog(null, label);
                System.out.println();
            } else {
                System.out.println("There are no such saved pictures with that words!");
            }
        }
    }

    public void deleteRecord() throws SQLException {
        try (Statement statement = con.createStatement()) {
            String sqlQuery = "SELECT * FROM ImageText;";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            ArrayList<Long> allIds = new ArrayList<>();
            int i = 1;
            while (resultSet.next()) {
                allIds.add(resultSet.getLong(1));
                System.out.println(i + ": Name of work: " + resultSet.getString(3) + " Saved Text: " + resultSet.getString(4));
                i++;
            }
            if (i != 1) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Choose the row to delete:");
                i = sc.nextInt();
                sqlQuery = "DELETE FROM ImageText WHERE id = " + allIds.get(i - 1) + ";";
                statement.executeUpdate(sqlQuery);
            } else {
                System.out.println("0 rows found");
            }
        }
    }

    public void updateRecord() throws SQLException, FileNotFoundException {
        try (Statement statement = con.createStatement()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Write name of work:");
            String nameOfWork = sc.nextLine();
            String sqlQuery = "SELECT * FROM ImageText WHERE NameWork = '"+nameOfWork+"';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            ArrayList<Long> allIds = new ArrayList<>();
            int i = 1;
            while (resultSet.next()) {
                allIds.add(resultSet.getLong(1));
                System.out.println(i + ": Name of work: " + resultSet.getString(3) + " Saved Text: " + resultSet.getString(4));
                i++;
            }
            if(i != 1) {
                System.out.println("Choose record to change:");
                i = Integer.parseInt(sc.nextLine());
                long id = allIds.get(i - 1);
                System.out.println("Choose attribute for change: 1 - Photo, 2 - Name, 3 - Text");
                i = Integer.parseInt(sc.nextLine());
                if (i == 1) {
                    System.out.println("Choose new image:");
                    JFileChooser jFile = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png");
                    jFile.addChoosableFileFilter(filter);
                    int result = jFile.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE ImageText SET Picture = ? WHERE Id = ?;");) {
                            File selectedFile = jFile.getSelectedFile();
                            FileInputStream fileInputStream = new FileInputStream(selectedFile);
                            preparedStatement.setBinaryStream(1, fileInputStream);
                            preparedStatement.setLong(2, id);
                            preparedStatement.executeUpdate();
                        }
                    } else {
                        System.out.println("Choose correct file!");
                    }
                } else if (i == 2) {
                    try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE ImageText SET NameWork = ? WHERE Id = ?;");) {
                        System.out.println("Write new name of work:");
                        String str = sc.nextLine();
                        preparedStatement.setString(1, str);
                        preparedStatement.setLong(2, id);
                        preparedStatement.executeUpdate();
                    }
                } else if (i == 3) {
                    try (PreparedStatement preparedStatement = con.prepareStatement("UPDATE ImageText SET TextOfPicture = ? WHERE Id = ?;");) {
                        System.out.println("Write new text for picture:");
                        String str = sc.nextLine();
                        preparedStatement.setString(1, str.toUpperCase());
                        preparedStatement.setLong(2, id);
                        preparedStatement.executeUpdate();
                    }
                }
            } else {
                System.out.println("There are no records with that name!");
            }
        }
    }

    public void seeAllRecords() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            String strQuery = "SELECT * FROM ImageText";
            ResultSet resultSet = stmt.executeQuery(strQuery);
            int i = 1;
            while (resultSet.next()) {
                System.out.println(i + ": Name of work: " + resultSet.getString(3) + " Saved Text: " + resultSet.getString(4));
                i++;
            }
            if (i == 1) {
                System.out.println("0 rows found");
            }
        }
    }
}
