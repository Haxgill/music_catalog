package app.view.authorizationLayout;

import app.Main;
import app.controller.JDBC;
import app.view.editLayout.EditLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by alex on 18.11.2015.
 */
public class AuthorizationLayoutController {

    @FXML
    public TextField userTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public Button logInButton;
    @FXML
    public Label errorInfoLabel;

    private Main main;
    private Stage stage;

    public AuthorizationLayoutController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.;

    }

    public void setMainApp(Main main) {
        this.main = main;
    }


    public boolean isEmpty(String text) {
        if (text.length()<=0)
            return true;
        else return false;
    }

    public boolean isСorrectLength(String text) {
        if (text.length() < 3  || text.length() > 10)
            return false;
        else return true;
    }

    public boolean isCorrectValue() {
        if (isEmpty(userTextField.getText())) {
          //  errorInfoLabel.setText("Поле Имя администратора должно быть заполненным!");
            return false;
        }
        else if (!isСorrectLength(userTextField.getText())) {
          //  errorInfoLabel.setText("Поле Имя администратора должно содержать  3..10 символов!");
            return false;
        }
        else if (checkOnSymbol(userTextField.getText())) {
           // errorInfoLabel.setText("Поле Имя администратора должно содержать только латинсие буквы или цифры!");
            return false;
        }
        else if (passwordTextField.getText().isEmpty()) {
          //  errorInfoLabel.setText("Поле Пароль должно быть заполненным!");
            return false;
        }
        else if (!isСorrectLength(passwordTextField.getText())) {
           // errorInfoLabel.setText("Поле Пароль должно содержать 3..10 символов!");
            return false;
        }
        else return true;
    }

    @FXML
    public boolean logInButtonAction() {
        errorInfoLabel.setText("");
         if (isCorrectValue()) {
             if (JDBC.checkUser(userTextField.getText(), passwordTextField.getText())) {
                 generateScene();
                 return true;
             } else {
                 errorInfoLabel.setText("Имя администратора или пароль введены неверно!");
                 return false;
             }
         }
        else return false;
    }

    public boolean checkOnSymbol(String text) {
        text = text.toLowerCase();
        char[] trueChar = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int i=0;
        Boolean result = false;
        while(i<text.length()) {
            int j=0;
            Boolean charFind = false;
            while (j < trueChar.length) {
                if (text.charAt(i) == trueChar[j]) {
                        charFind = true;
                    }
                j++;
                }
            if(!charFind) {
                result = true;
                break;
            }
            i++;
        }
        return result;
    }

    private void generateScene() {
        try {
            FXMLLoader loaderEditLayout = new FXMLLoader();
            loaderEditLayout.setLocation(Main.class.getResource("/app/view/editLayout/EditLayout.fxml"));
            Parent EditLayout = (Parent) loaderEditLayout.load();
            EditLayoutController EditLayoutCr = loaderEditLayout.getController();
            EditLayoutCr.setMainApp(main);
            ((AnchorPane) main.scene.getRoot()).getChildren().remove(1);
            ((AnchorPane) main.scene.getRoot()).getChildren().remove(0);
            ((AnchorPane) main.scene.getRoot()).getChildren().add(EditLayout);
            ((AnchorPane) main.scene.getRoot()).setTopAnchor(EditLayout, 0.0);
            ((AnchorPane) main.scene.getRoot()).setLeftAnchor(EditLayout, 0.0);
            ((AnchorPane) main.scene.getRoot()).setRightAnchor(EditLayout, 0.0);
            ((AnchorPane) main.scene.getRoot()).setBottomAnchor(EditLayout, 0.0);
            main.stage.setWidth(846);
            main.stage.setHeight(572);
            main.stage.setResizable(false);
            main.stage.setTitle("Music Catalog Editor");
            stage.close();
        } catch (IOException e) {
        }

    }

    public void setAuthLayoutStage(Stage stage) {
        this.stage = stage;
    }
}
