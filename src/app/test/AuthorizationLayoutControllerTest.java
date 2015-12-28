package app.test;
import app.Main;
import app.view.authorizationLayout.AuthorizationLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * Created by alex on 12.12.2015.
 */
public class AuthorizationLayoutControllerTest extends Assert {
    static AuthorizationLayoutController authorizationLayoutController;

    public static class AppForTest extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            FXMLLoader loaderAuthLayout = new FXMLLoader();
            loaderAuthLayout.setLocation(Main.class.getResource("/app/view/authorizationLayout/AuthorizationLayout.fxml"));
            Parent root = loaderAuthLayout.load();
            authorizationLayoutController = loaderAuthLayout.getController();
            primaryStage.setTitle("Authentication");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    @BeforeClass
    public static void initAuthorizationLayoutController() throws InterruptedException {
        Thread t = new Thread() {
            public void run() {

            Application.launch(AppForTest.class, new String[0]);

        }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);

    }

    @Test
    public void testIsEmpty() {
        //a-b-d
        Boolean actual1 =  authorizationLayoutController.isEmpty("");
        assertTrue(actual1);
        //a-c-d
        Boolean actual2 =  authorizationLayoutController.isEmpty("a");
        assertFalse(actual2);
    }

    @Test
    public void testIsСorrectLength() {
        //a-b-d
        Boolean actual1 =  authorizationLayoutController.isСorrectLength("ab");
        assertFalse(actual1);
        //a-c-d
        Boolean actual2 =  authorizationLayoutController.isСorrectLength("admin");
        assertTrue(actual2);
    }

    @Test
    public void testIsСorrectValue() {
        //a-b-h
        authorizationLayoutController.userTextField.setText("");
        authorizationLayoutController.passwordTextField.setText("");
        Boolean actual1 =  authorizationLayoutController.isCorrectValue();
        assertFalse(actual1);
        //a-c-h
        authorizationLayoutController.userTextField.setText("cd");
        authorizationLayoutController.passwordTextField.setText("");
        Boolean actual2 =  authorizationLayoutController.isCorrectValue();
        assertFalse(actual2);
        //a-d-h
        authorizationLayoutController.userTextField.setText("@dmin");
        authorizationLayoutController.passwordTextField.setText("");
        Boolean actual3 =  authorizationLayoutController.isCorrectValue();
        assertFalse(actual3);
        //a-e-h
        authorizationLayoutController.userTextField.setText("admin");
        authorizationLayoutController.passwordTextField.setText("");
        Boolean actual4 =  authorizationLayoutController.isCorrectValue();
        assertFalse(actual4);
        //a-f-h
        authorizationLayoutController.userTextField.setText("admin");
        authorizationLayoutController.passwordTextField.setText("w&");
        Boolean actual5 =  authorizationLayoutController.isCorrectValue();
        assertFalse(actual5);
        //a-g-h
        authorizationLayoutController.userTextField.setText("admin");
        authorizationLayoutController.passwordTextField.setText("admin");
        Boolean actual6 =  authorizationLayoutController.isCorrectValue();
        assertTrue(actual6);
    }

}
