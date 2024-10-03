package com.currencyconv;

import javafx.application.Application;  // Ensure this import exists
import com.currencyconv.service.GUI;

public class Main {
    public static void main(String[] args) {
        Application.launch(GUI.class, args);  // This line starts the JavaFX application
    }
}
