module ge.tsu.javaprojectdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.fxmisc.richtext;
  //  requires javafx.graphics;

    opens ge.tsu.javaprojectdemo to javafx.fxml;
    exports ge.tsu.javaprojectdemo;
}