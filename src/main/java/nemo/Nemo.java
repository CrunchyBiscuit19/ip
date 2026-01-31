package nemo;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import nemo.gui.Gui;

/**
 * The main class that encompasses the whole program.
 */
public class Nemo {
    public static void main(String[] args1) {
        Path saveFilePath = Paths.get(System.getProperty("user.home")).resolve("NEMO").resolve("data.txt");
        Application.launch(Gui.class, saveFilePath.toString());
    }
}
