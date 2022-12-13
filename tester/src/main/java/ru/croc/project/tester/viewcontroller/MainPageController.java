package ru.croc.project.tester.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.croc.project.tester.LearnitApplication;
import ru.croc.project.tester.model.dao.tests.TestDao;
import ru.croc.project.tester.model.pojo.users.User;
import ru.croc.project.tester.model.pojo.users.UserTestInformation;

public class MainPageController {
    @FXML
    public VBox tileBox;
    private User user;
    private TestDao testDao = new TestDao();

    public MainPageController() throws IOException {
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        List<Button> buttons = new ArrayList<>();
        List<Label> labels = new ArrayList<>();

        for (UserTestInformation information : user.getTestsInformation()) {
            buttons.add(new Button(information.getTestName()));
            labels.add(new Label( "Пройдено:\t" + information.getRightAnswers() + "/" + information.getAnsCount()));
        }
        
        for (int i = 0; i < buttons.size(); ++i) {
            tileBox.getChildren().add(buttons.get(i));
            tileBox.getChildren().add(labels.get(i));
            String name = buttons.get(i).getText();
            buttons.get(i).setOnAction(event -> {
                try {
                    sendData(event, name);
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void sendData(ActionEvent event, String name) throws IOException, SQLException, ClassNotFoundException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(LearnitApplication.class.getResource("test.fxml"));
        TestPageController controller = new TestPageController();
        controller.setTest(testDao.getTestByName(name));
        controller.setUser(user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
