package com.invoker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TextEditor extends Application {
    private TextArea textArea;
    private File currentFile;

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane root = new BorderPane();
        textArea = new TextArea();
        textArea.setStyle("-fx-font-family: 'Monospace'; -fx-font-size: 14;");
        root.setCenter(textArea);

        // Menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem exitItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);
        root.setTop(menuBar);

        // Menu actions
        newItem.setOnAction(e -> newFile());
        openItem.setOnAction(e -> openFile(primaryStage));
        saveItem.setOnAction(e -> saveFile(primaryStage));
        saveAsItem.setOnAction(e -> saveFileAs(primaryStage));
        exitItem.setOnAction(e -> primaryStage.close());

        // Scene and stage setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Minimalist Text Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void newFile() {
        textArea.clear();
        currentFile = null;
        ((Stage) textArea.getScene().getWindow()).setTitle("Minimalist Text Editor");
    }

    private void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                if (file.getName().endsWith(".txt")) {
                    String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                    textArea.setText(content);
                    currentFile = file;
                    stage.setTitle("Minimalist Text Editor - " + file.getName());
                } else if (file.getName().endsWith(".pdf")) {
                    textArea.setText("PDF viewing not yet implemented. Only text editing supported.");
                    currentFile = null;
                }
            } catch (IOException e) {
                showAlert("Error", "Cannot open file: " + e.getMessage());
            }
        }
    }

    private void saveFile(Stage stage) {
        if (currentFile == null) {
            saveFileAs(stage);
        } else {
            try {
                if (currentFile.getName().endsWith(".txt")) {
                    java.nio.file.Files.write(currentFile.toPath(), textArea.getText().getBytes());
                } else if (currentFile.getName().endsWith(".pdf")) {
                    createPDF(currentFile);
                }
            } catch (IOException e) {
                showAlert("Error", "Cannot save file: " + e.getMessage());
            }
        }
    }

    private void saveFileAs(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".txt") && !file.getName().endsWith(".pdf")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            currentFile = file;
            try {
                if (file.getName().endsWith(".txt")) {
                    java.nio.file.Files.write(file.toPath(), textArea.getText().getBytes());
                } else if (file.getName().endsWith(".pdf")) {
                    createPDF(file);
                }
                stage.setTitle("Minimalist Text Editor - " + file.getName());
            } catch (IOException e) {
                showAlert("Error", "Cannot save file: " + e.getMessage());
            }
        }
    }

    private void createPDF(File file) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                String[] lines = textArea.getText().split("\n");
                for (String line : lines) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -15);
                }
                contentStream.endText();
            }
            document.save(file);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}