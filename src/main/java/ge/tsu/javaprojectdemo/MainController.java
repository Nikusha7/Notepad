package ge.tsu.javaprojectdemo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    //MainController Attributes:
    private FileChooser fileChooser;

    private Stage stage;
    private Alert aboutAlert;
    private Alert askBeforeAlert;

    public TextArea textArea;
    public Separator separator;
    public HBox hbox;

    public Text countText;
    public Text countLines;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;
    public MenuItem deleteMenuItem;
    public MenuItem undoMenuItem;
    public MenuItem cutMenuItem;
    public MenuItem copyMenuItem;
    public MenuItem pasteMenuItem;

    //setter method for stage
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.titleProperty().bindBidirectional(state.titleProperty());
    }

    //object of state
    private State state = new State();

    public MainController() {
    }

    /*
    it is invoked only once, and it launches only after your main controller and its object are created
    it is something like constructor but for your controller
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser = new FileChooser();
        //set filter for open dialog, meaning: it can only open txt files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Documents", "*.txt")
        );
        askBeforeAlert = new Alert(Alert.AlertType.WARNING, "",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        askBeforeAlert.setTitle("Notepad");

        //bindings:
        textArea.textProperty().bindBidirectional(state.contentProperty());
        //disabling File:save, saveAs. Edit:Undo, Cut, Copy, Delete if there is no content
        saveMenuItem.disableProperty().bind(textArea.textProperty().isEmpty());
        saveAsMenuItem.disableProperty().bind(textArea.textProperty().isEmpty());
        undoMenuItem.disableProperty().bind(textArea.textProperty().isEmpty());
        cutMenuItem.disableProperty().bind(textArea.selectedTextProperty().isEmpty());
        copyMenuItem.disableProperty().bind(textArea.selectedTextProperty().isEmpty());
        deleteMenuItem.disableProperty().bind(textArea.selectedTextProperty().isEmpty());
        //counting inputted text
        countText.textProperty().bind(state.contentProperty().length().asString());
        //setting initial font and size of text
        textArea.setFont(Font.font("", FontWeight.NORMAL, FontPosture.REGULAR, 12));

        /*
        TODO: 1) we are counting inputted text now we need to count paragraphs
         */

//        countLines.textProperty().bind(state.contentProperty());
//        countLines.setText(String.valueOf(textArea.getParagraphs().size()));
//        countLines.setText(String.format("ln: ",textArea.getParagraphs().size()));
//        cssTextArea.setTextInsertionStyle(textArea.getText());
//        cssTextArea.getParagraphs().sizeProperty().addListener((observable, oldValue, newValue) -> {
//        countLines.setText(String.format("Ln: %d", textArea.getParagraphs().size()));
//        });
    }

/*
        TODO: logic for word wrap(: a word processing feature that
        TODO: automatically transfers a word for which there is insufficient space from the end of one line of text to the beginning of the next.)
        Marked-if it's marked it means that when texts reaches the end then should be shifted down to the new line
        Unmarked-it should be left as it is now, because it works now as unmarked by default.
*/


    /*
    asToSave is the method which is used in onNew, onOpen and onExit methods
    */
    private void askToSave(Runnable onYes, Runnable onNo) throws IOException {
        String alertContent = String.format("Do you want to save changes to %s", state.getFileName());
        askBeforeAlert.setContentText(alertContent);
        Optional<ButtonType> buttonType = askBeforeAlert.showAndWait();

        if (buttonType.isPresent()) {
            ButtonType type = buttonType.get();
            if (type.equals(ButtonType.YES)) {
                onSaveAs(null);
                onYes.run();
            } else if (type.equals(ButtonType.NO)) {
                onNo.run();
            }
        }

    }

    /*
    setAboutAlert: We are putting our scene of about controller in aboutAlert
     */
    public void setAboutAlert(Parent aboutNode) {
        aboutAlert = new Alert(Alert.AlertType.NONE);
        aboutAlert.setTitle("About Notepad");
        aboutAlert.getDialogPane().setContent(aboutNode);
        aboutAlert.getButtonTypes().addAll(ButtonType.CLOSE);
    }

     /*
    Main Controllers methods:

    FILE: Methods of file are given below!
     */

    public void onNew(ActionEvent actionEvent) throws IOException {
        if (state.getPath() != null || (state.getContent() != null && !textArea.getText().isEmpty())) {
            Runnable action = () -> {
                state.clear();
            };
            askToSave(action, action);
        }
    }

    public void onOpen(ActionEvent actionEvent) throws IOException {
        if (!state.getContent().isEmpty()) {
            Runnable actionOpen = () -> {
                fileChooser.setTitle("Open Text File");
                File selectedFile = fileChooser.showOpenDialog(stage);
                //checking if selected file is null and if it's not then we are setting to text area a new content of selected file
                //and setting title of opened file
                if (selectedFile != null) {
                    String openedFileContent = null;
                    try {
                        openedFileContent = Files.readString(selectedFile.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    state.setPath(selectedFile.toPath());
                    state.setContent(openedFileContent);
                    state.setTitle(selectedFile.getName());
                }
            };
            askToSave(actionOpen, actionOpen);
            return;
        }
        fileChooser.setTitle("Open Text File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        String openedFileContent = Files.readString(selectedFile.toPath());
        state.setPath(selectedFile.toPath());
        state.setContent(openedFileContent);
        textArea.setText(state.getContent());
        state.setTitle(selectedFile.getName());
        stage.setTitle(state.getTitle());
    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        if (state.getPath() == null) {
            onSaveAs(null);
        } else {
            Files.writeString(state.getPath(), textArea.getText());
        }
    }

    public void onSaveAs(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Save As");
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            Files.writeString(selectedFile.toPath(), textArea.getText());
            state.setPath(selectedFile.toPath());
            state.setContent(textArea.getText());
            state.setTitle(selectedFile.getName());
        }
    }

    public void onExit(ActionEvent actionEvent) throws IOException {
        //platform exit is better than system.exit
        if ((state.getPath() == null && !textArea.getText().isEmpty())
                || (state.getContent() != null && !state.getContent().equals(textArea.getText()))) {
            askToSave(Platform::exit, Platform::exit);
        } else {
            Platform.exit();
        }
    }



    /*
    EDIT: Methods of edit are given below!
     */

    /*
    TODO: property binding doesnot allow textarea.redo(). because we do redo when tet is empty
     */
    public void onUndo(ActionEvent actionEvent) {
        if (!state.getContent().isEmpty()) {
            textArea.undo();
        } else if (state.getContent().isEmpty()) {
            textArea.redo();
        }
    }

    public void onCut(ActionEvent actionEvent) {
        textArea.cut();
    }

    public void onCopy(ActionEvent actionEvent) {
        textArea.copy();
    }

    public void onPaste(ActionEvent actionEvent) {
        textArea.paste();
    }

    public void onDelete(ActionEvent actionEvent) {
        textArea.clear();
    }

    public void onSearchWithBing(ActionEvent actionEvent) throws IOException {

    }

    public void onSelectAll(ActionEvent actionEvent) {
        textArea.selectAll();
    }

    public void onTimeDate(ActionEvent actionEvent) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        textArea.appendText(localDateTime.format(dateTimeFormatter) + "\n");
    }



    /*
    FORMAT: Methods of format are given below!
     */

    public void onWordWrap(ActionEvent actionEvent) {
    }



    /*
    VIEW: Methods of view are given below!
     */

    double FontSizeOfTextArea = 12.0;

    public void onZoomIn(ActionEvent actionEvent) {
        FontSizeOfTextArea++;
        textArea.setFont(Font.font("System", FontWeight.BOLD, FontSizeOfTextArea));
    }

    public void onZoomOut(ActionEvent actionEvent) {
        FontSizeOfTextArea--;
        textArea.setFont(Font.font("System", FontWeight.NORMAL, FontSizeOfTextArea));
    }

    public void OnRestoreDefaultZoom(ActionEvent actionEvent) {
        textArea.setFont(Font.font("System", FontWeight.NORMAL, 12.0));
        FontSizeOfTextArea = 12.0;
    }

    public void onStatusBar(ActionEvent actionEvent) {
        if (!hbox.isDisabled()) {
            //disables Hbox
            hbox.setVisible(false);
            hbox.setDisable(true);
            //disables separator line
            separator.setVisible(false);
            separator.setDisable(true);
        } else if (hbox.isDisabled()) {
            hbox.setVisible(true);
            hbox.setDisable(false);

            separator.setVisible(true);
            separator.setDisable(false);
        }
    }



    /*
    HELP: Methods of help are given below!
     */

    public void onViewHelp(ActionEvent actionEvent) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse((new URI("https://www.bing.com/search?q=get+help+with+notepad+in+windows&filters=guid:%224466414-en-dia%22%20lang:%22en%22&form=T00032&ocid=HelpPane-BingIA")));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void onAboutNotepad(ActionEvent actionEvent) throws IOException {
        aboutAlert.showAndWait();
    }


}