package ge.tsu.javaprojectdemo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.nio.file.Path;

public class State {
//
//    private Path file;
//    private String content;
//
//    public void clear() {
//        file = null;
//        content = null;
//    }
//
//    public String getFileName() {
//        if (file == null) {
//            return "Untitled";
//        }
//        String fileName = file.getFileName().toString();
//        //removing ".txt" from name
//        return fileName.substring(0, fileName.length() - 4);
//    }
//
//    public String getTitle() {
//        return getFileName() + " - Notepad";
//    }
//
//    //getters and setters for file and content
//    public Path getFile() {
//        return file;
//    }
//
//    public void setFile(Path file) {
//        this.file = file;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
    private ObjectProperty<Path> path = new SimpleObjectProperty<>();
    private StringProperty filename = new SimpleStringProperty("Untitled");
    private StringProperty title = new SimpleStringProperty("Untitled - Notepad");
    private StringProperty content = new SimpleStringProperty("");
    private StringProperty contentParagraph = new SimpleStringProperty("");


    public String getContentParagraph() {
        return contentParagraph.get();
    }

    public StringProperty contentParagraph() {
        return contentParagraph;
    }

    public void setContentParagraph(String contentParagraph) {
        this.content.set(contentParagraph);
    }


    public Path getPath() {
        return path.get();
    }

    public ObjectProperty<Path> pathProperty() {
        return path;
    }

    public void setPath(Path path) {
        this.path.set(path);
    }

    public String getFileName() {
        return filename.get();
    }

    public StringProperty filenameProperty() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename.set(filename);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        //removing .txt from filename
        this.title.set(title.substring(0, title.length() - 4)+" - Notepad");
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void clear() {
        setFilename("Untitled");
        setTitle("Untitled");
        setPath(null);
        setContent("");
    }
}
