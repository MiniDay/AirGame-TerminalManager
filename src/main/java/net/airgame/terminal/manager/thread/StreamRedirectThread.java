package net.airgame.terminal.manager.thread;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import net.airgame.terminal.manager.container.TerminalPane;
import net.airgame.terminal.manager.controller.MainController;

import java.io.IOException;
import java.io.InputStream;

public class StreamRedirectThread extends Thread {
    private final MainController controller;

    private final byte[] bytes;
    private volatile boolean stop;

    public StreamRedirectThread(MainController controller) {
        this.controller = controller;
        bytes = new byte[1024 * 1024];
        stop = false;
    }

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        while (!stop) {
            for (int i = 0; i < controller.terminalPanes.size(); i++) {
                TerminalPane terminalPane = controller.terminalPanes.get(i);

                Process process = terminalPane.getProcess();

                TextArea area = terminalPane.getOutputTextArea();
                try {
                    InputStream inputStream = process.getInputStream();
                    if (inputStream.available() > 0) {
                        int read = inputStream.read(bytes);
                        String s = new String(bytes, 0, read, terminalPane.getInputCharset());
                        Platform.runLater(() -> {
                            int subLength = area.getText().length() - 50000;
                            if (subLength > 1000) {
                                area.deleteText(0, subLength);
                            }
                            area.appendText(s);
                        });
                    }

                    InputStream errorStream = process.getErrorStream();
                    if (errorStream.available() > 0) {
                        int read = errorStream.read(bytes);
                        String s = new String(bytes, 0, read, terminalPane.getInputCharset());
                        Platform.runLater(() -> {
                            int subLength = area.getText().length() - 50000;
                            if (subLength > 1000) {
                                area.deleteText(0, subLength);
                            }
                            area.appendText(s);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
