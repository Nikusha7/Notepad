package ge.tsu.javaprojectdemo;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    /*
    AboutController class controls about.fxml which is about notepad window.
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
        File windowsLogoFile = new File("images/Windows-logo.png");
        Image windowsLogoImage = new Image(windowsLogoFile.toURI().toString());
        windowsLogo.setImage(windowsLogoImage);

        File notepadLogoFile = new File("images/Notepad-icon.png");
        Image notepadLogoImage = new Image(notepadLogoFile.toURI().toString());
        notepadLogo.setImage(notepadLogoImage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        stage.setTitle("prove that stage is passed to aboutController class");
        txt.setText(stage.getTitle().toUpperCase());
    }

}
