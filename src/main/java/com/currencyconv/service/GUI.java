package com.currencyconv.service;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class GUI extends Application {

    private static Scene scene; // Declare a Scene variable
    
        @Override
        public void start(Stage primaryStage) {
            // Load the background image
            Image backgroundImage = new Image("com/currencyconv/images/photo-1526304640581-d334cdbbf45e.jpg"); // Update the path to your image
            BackgroundImage background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
    
            // Main layout container
            VBox mainLayout = new VBox(30);
            mainLayout.setPadding(new Insets(20));
            mainLayout.setAlignment(Pos.CENTER);
            mainLayout.setBackground(new Background(background)); // Set the background image
    
            // Create a white VBox for the entire content
            VBox contentBox = new VBox(25);
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setPadding(new Insets(20));
            contentBox.setStyle("-fx-background-color: rgba(255, 255, 255, 1); -fx-background-radius: 10; -fx-padding: 20;"); // White background with opacity
    
            // Title Label
            Label title = new Label("Currency Converter");
            title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 36));
            title.setTextFill(Color.BLACK);  // Set title color to black
    
            // Create input form
            HBox inputBox = new HBox(15);
            inputBox.setAlignment(Pos.CENTER);
    
            Label amountLabel = new Label("Amount:");
            amountLabel.setTextFill(Color.BLACK);
            TextField amountField = new TextField();
            amountField.setPromptText("Enter amount");
    
            Label fromCurrencyLabel = new Label("Convert From:");
            fromCurrencyLabel.setTextFill(Color.BLACK);
            ComboBox<String> fromCurrencyBox = new ComboBox<>();
            fromCurrencyBox.getItems().addAll("USD", "EUR", "INR", "GBP", "AUD");
            fromCurrencyBox.setPromptText("Select currency");
    
            Label toCurrencyLabel = new Label("Convert To:");
            toCurrencyLabel.setTextFill(Color.BLACK);
            ComboBox<String> toCurrencyBox = new ComboBox<>();
            toCurrencyBox.getItems().addAll("USD", "EUR", "INR", "GBP", "AUD");
            toCurrencyBox.setPromptText("Select currency");
    
            inputBox.getChildren().addAll(amountLabel, amountField, fromCurrencyLabel, fromCurrencyBox, toCurrencyLabel, toCurrencyBox);
    
            // Convert button
            Button convertButton = new Button("Convert");
            convertButton.setStyle("-fx-background-color: #2E8B57; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");
            convertButton.setCursor(Cursor.HAND);
            DropShadow shadow = new DropShadow();
            convertButton.setOnMouseEntered(e -> convertButton.setEffect(shadow));
            convertButton.setOnMouseExited(e -> convertButton.setEffect(null));
    
            // Result label
            Label resultLabel = new Label();
            resultLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 16));
            resultLabel.setTextFill(Color.BLACK);
    
            convertButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        String amountText = amountField.getText().trim();
                        if (amountText.isEmpty()) {
                            showAlert("Invalid Input", "Please enter an amount.");
                            return;
                        }
    
                        int amount = Integer.parseInt(amountText);
                        String fromCurrency = fromCurrencyBox.getValue();
                        String toCurrency = toCurrencyBox.getValue();
    
                        if (fromCurrency == null || toCurrency == null) {
                            showAlert("Invalid Input", "Please select both currencies.");
                            return;
                        }
    
                        APIService.amt = amount;
                        APIService.fromcurr = fromCurrency;
                        APIService.tocurr = toCurrency;
    
                        DecimalFormat df = new DecimalFormat("0.00");
                    String formattedValue = df.format(APIService.getResult());
    
                        resultLabel.setText("Converted Amount: " + formattedValue);
                    } catch (NumberFormatException e) {
                        showAlert("Invalid Input", "Please enter a valid number for the amount.");
                    } catch (Exception e) {
                        showAlert("Error", "An error occurred: " + e.getMessage());
                    }
                }
            });
    
            // Add elements to the content box
            contentBox.getChildren().addAll(title, inputBox, convertButton, resultLabel);
    
            // Add content box to the main layout
            mainLayout.getChildren().add(contentBox);
    
            // Create scene and set to primary stage
            scene = new Scene(mainLayout, 600, 400); // Save the scene to the class variable
            primaryStage.setTitle("Enhanced Currency Converter");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    
        // Method to return the current scene
        public static Scene getScene() {
                return scene;
        }
    
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(GUI.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
    }
    
    }




