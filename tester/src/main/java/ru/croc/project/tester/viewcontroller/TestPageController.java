package ru.croc.project.tester.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

import ru.croc.project.tester.LearnitApplication;
import ru.croc.project.tester.model.dao.users.UserDao;
import ru.croc.project.tester.model.pojo.tests.Test;
import ru.croc.project.tester.model.pojo.users.User;

public class TestPageController {
    @FXML
    public Label testName;

    @FXML
    public VBox questionBox;

    @FXML
    public Button exitButton;
    private Test test;

    private User user;

    private UserDao userDao = new UserDao();

    int rightAnswersCount = 0;

    public TestPageController() throws IOException {
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        testName.setText(test.getName());
        List<String> questionTexts = test.getQuestionTexts();
        List<List<String>> answersVariants = test.getAnswersVariants();
        List<ToggleGroup> groups = new ArrayList<>();
        List<String> rightAnswers = test.getRightAnswers();


        for (int i = 0; i < questionTexts.size(); ++i) {
            questionBox.getChildren().add(new Label(questionTexts.get(i)));
            groups.add(new ToggleGroup());
            for (String variant : answersVariants.get(i)) {
                RadioButton rb = new RadioButton(variant);
                rb.setToggleGroup(groups.get(i));
                questionBox.getChildren().add(rb);
            }

            int finalI = i;
            groups.get(i).selectedToggleProperty().addListener((changed, oldValue, newValue) -> {
                RadioButton selectedBtn = (RadioButton) newValue;
                if (selectedBtn.getText().equals(rightAnswers.get(finalI))) {
                    ++rightAnswersCount;
                }
            });
        }
        exitButton.setOnAction(event -> {
            try {
                saveResultAndExit(event);
            } catch (IOException | SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveResultAndExit(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        user.findTestInformationByTestName(test.getName()).setRightAnswers(rightAnswersCount);
        userDao.updateInfo(user.getId(), user.getTestsInformation());
        sendData(event);
    }

    private void sendData(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(LearnitApplication.class.getResource("main.fxml"));
        MainPageController controller = new MainPageController();
        controller.setUser(user);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

