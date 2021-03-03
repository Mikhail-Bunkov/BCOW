package client;

import commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegController {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nicknameField;
    @FXML private TextArea textArea;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void addMsg(String command){
        if(command.equals(Command.REGISTRATION_IS_OK)){
            textArea.appendText("Регистрация прошла успешно\n");
        }
        if(command.equals(Command.REGISTRATION_IS_NOT_OK)){
            textArea.appendText("Логин или никнейм уже заняты\n");
        }
    }

    public void tryToRegistration(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String nickname = nicknameField.getText().trim();

        if(login.length()*password.length()*nickname.length() == 0){
            return;
        }

        controller.registration(login, password, nickname);
    }
}
