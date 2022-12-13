package ru.croc.project.tester.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.croc.project.tester.LearnitApplication;
import ru.croc.project.tester.model.dao.users.UserDao;
import ru.croc.project.tester.model.pojo.users.User;

import java.io.IOException;
import java.sql.SQLException;

public class AuthPageController {
    private UserDao userDao = new UserDao();
    private User user = new User();
    private AlertView alertView = new AlertView();
    @FXML
    private TextField userId;

    public AuthPageController() throws IOException {
    }

    @FXML
    public void signInButtonClick(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        String id = userId.getText();
        if (id.equals("")) {
            alertView.showAlertWithHeaderText("Идентификатор не может быть пустым");
        }
        else {
            user = userDao.isExists(id);
            if (user != null) {
                sendData(event);

            }
            else {
                alertView.showAlertWithHeaderText("Пользователя с таким идентификатор не существует!");
            }
        }
    }

    @FXML
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
