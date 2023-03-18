package ge.tsu.javaprojectdemo;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    /*
    AboutController class controls about.fxml which is "about notepad" window.
     */
    public ImageView windowsLogo;
    public ImageView notepadLogo;

    public Stage stage;
    public Text txt;

    public AboutController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting pictures to about notepad window
//        File windowsLogoFile = new File("Images/Windows-logo.png");
//        Image windowsLogoImage = new Image(windowsLogoFile.toURI().toString());
//        windowsLogo.setImage(windowsLogoImage);
//
//        File notepadLogoFile = new File("Images/Notepad-icon.png");
//        Image notepadLogoImage = new Image(notepadLogoFile.toURI().toString());
//        notepadLogo.setImage(notepadLogoImage);

        try (InputStream imageInputStream = AboutController.class.getResourceAsStream("/images/Windows-logo.png")) {
            //if imageInputStream does not equal null code will continue, otherwise Java runtime system throws an AssertionError
            assert imageInputStream != null;
            Image windowsLogoImage = new Image(imageInputStream);
            windowsLogo.setImage(windowsLogoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream imageInputStream = AboutController.class.getResourceAsStream("/images/Notepad-icon.png")) {
            //if imageInputStream does not equal null code will continue, otherwise Java runtime system throws an AssertionError
            assert imageInputStream != null;
            Image notepadLogoImage = new Image(imageInputStream);
            notepadLogo.setImage(notepadLogoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;

        stage.setTitle("prove that stage is passed to aboutController class");
        txt.setText(stage.getTitle().toUpperCase());
    }

}
