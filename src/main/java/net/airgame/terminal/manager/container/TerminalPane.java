package net.airgame.terminal.manager.container;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.airgame.terminal.manager.core.ConfigManager;

import java.io.File;
import java.io.IOException;

public class TerminalPane extends AnchorPane {
    private final TextArea outputTextArea;
    private final TextField inputField;
    private final Process process;

    private String name;

    @SuppressWarnings("unused")
    public TerminalPane() throws IOException {
        this("", "powershell", new File(""));
    }

    public TerminalPane(String name, String command, File workspace) throws IOException {
        this.name = name;
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setWrapText(true);
        getChildren().add(outputTextArea);
        setTopAnchor(outputTextArea, 1D);
        setBottomAnchor(outputTextArea, 31D);
        setLeftAnchor(outputTextArea, 1D);
        setRightAnchor(outputTextArea, 1D);


        inputField = new TextField();
        getChildren().add(inputField);
        setBottomAnchor(inputField, 1D);
        setLeftAnchor(inputField, 1D);
        setRightAnchor(inputField, 1D);

        process = new ProcessBuilder().directory(workspace).command(command.split(" ")).start();

        inputField.setOnAction(event -> {
            if (!process.isAlive()) {
                return;
            }
            String text = inputField.getText() + "\n";
            try {
                process.getOutputStream().write(text.getBytes(ConfigManager.getOutputCharset()));
                process.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(inputField::clear);
        });

        visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                inputField.requestFocus();
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Process getProcess() {
        return process;
    }

    public TextArea getOutputTextArea() {
        return outputTextArea;
    }

    public void closeProcess() {
        if (process.isAlive()) {
            process.destroy();
        }
    }
}
