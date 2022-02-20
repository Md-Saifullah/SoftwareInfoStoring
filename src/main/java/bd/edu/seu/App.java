package bd.edu.seu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static String currentUser;
    private static String currentProject;
    private static boolean Subscriber;
    private static boolean Developer;
    private static Boolean ownedCheck = false;

    public static boolean isDeveloper() {
        return Developer;
    }
    public static void setDeveloper(boolean developer) {
        Developer = developer;
    }
    public static boolean isSubscriber() {
        return Subscriber;
    }

    public static void setSubscriber(boolean subscriber) {
        App.Subscriber = subscriber;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        App.currentUser = currentUser;
    }

    public static Boolean getOwnedCheck() {
        return ownedCheck;
    }

    public static void setOwnedCheck(Boolean ownedCheck) {
        App.ownedCheck = ownedCheck;
    }

    public static String getCurrentProject() {
        return currentProject;
    }

    public static void setCurrentProject(String currentProject) {
        App.currentProject = currentProject;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("logIn"));
        stage.setTitle("Software Project APP");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}