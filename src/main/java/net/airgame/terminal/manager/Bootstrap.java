package net.airgame.terminal.manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.airgame.terminal.manager.controller.MainController;
import net.airgame.terminal.manager.core.ConfigManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Bootstrap extends Application {
    public static void main(String[] args) {
        File file = new File("config.yml");
        if (!file.exists()) {
            try {
                Files.copy(Bootstrap.class.getResourceAsStream("/config.yml"), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
                return;
            }
        }

        try {
            ConfigManager.init();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return;
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Main.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setTitle("AirGame-TerminalManager");

        stage.show();

        stage.setOnCloseRequest(event -> {
            MainController controller = fxmlLoader.getController();
            controller.getStreamRedirectThread().setStop(true);
            controller.getExitMonitorThread().setStop(true);
        });

    }
}
