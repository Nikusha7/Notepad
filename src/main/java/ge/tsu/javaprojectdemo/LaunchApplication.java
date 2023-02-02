package ge.tsu.javaprojectdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class LaunchApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent aboutNode = loadAboutNode(stage);

        Parent mainNode = createContent(stage, aboutNode);

        Scene mainScene = new Scene(mainNode);
        addStyle(mainScene);

        stage.setScene(mainScene);

        stage.getIcons().add(iconImage());
        stage.setTitle("Untitled - Notepad");

        stage.setWidth(600);
        stage.setHeight(400);

        stage.show();
    }

    private void addStyle(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
    }

    private Parent loadAboutNode(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent;
        try (var inputStream = LaunchApplication.class.getResourceAsStream("/views/about.fxml")) {
            parent = fxmlLoader.load(inputStream);
        }
        stage.setScene(new Scene(parent));

        AboutController aboutController = fxmlLoader.getController();

        aboutController.setStage(stage);

        return parent;
    }

    public Parent createContent(Stage stage, Parent aboutNode) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parentNode;
        try (var inputStream = LaunchApplication.class.getResourceAsStream("/views/main.fxml")) {
            parentNode = fxmlLoader.load(inputStream);
        }
        //passing stage to our controller class
        MainController mainController = fxmlLoader.getController();

        mainController.setStage(stage);

        //passing aboutNode to our controller class
        mainController.setAboutAlert(aboutNode);

        return parentNode;
    }

    public Image iconImage() throws IOException {
        try (InputStream imageInputStream = LaunchApplication.class.getResourceAsStream("/images/Notepad-icon.png")) {
            //if imageInputStream does not equal null code will continue, otherwise Java runtime system throws an AssertionError
            assert imageInputStream != null;
            return new Image(imageInputStream);
        }
    }


    public static void main(String[] args) {

        //launch method invokes start(Stage stage) method
        launch();
    }

}