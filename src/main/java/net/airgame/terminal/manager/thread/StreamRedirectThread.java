package net.airgame.terminal.manager.thread;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.InputStream;

public class StreamRedirectThread extends Thread {
    private final InputStream inputStream;
    private final TextArea area;
    private final byte[] bytes;
    private final String charset;
    private volatile boolean stop;

    private Process process;

    public StreamRedirectThread(InputStream inputStream, TextArea area, String charset) {
        this.inputStream = inputStream;
        this.area = area;
        this.charset = charset;
        bytes = new byte[1024 * 1024];
        stop = false;
    }

    public StreamRedirectThread(Process process, InputStream inputStream, TextArea area, String charset) {
        this.process = process;
        this.inputStream = inputStream;
        this.area = area;
        this.charset = charset;

        bytes = new byte[1024 * 1024];
        stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                int read = inputStream.read(bytes);
                if (read <= 0) {
                    printExit();
                    break;
                }
                String output = new String(bytes, 0, read, charset);
                Platform.runLater(() -> {
                    area.appendText(output);
                    String text = area.getText();
                    int subLength = text.length() - 50000;
                    if (subLength > 1000) {
                        area.setText(
                                text.substring(subLength)
                        );
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    private void printExit() {
        if (process == null) {
            return;
        }
        int exitValue = process.exitValue();
        Platform.runLater(() -> {
            area.appendText("\n\n程序已结束，退出代码: " + exitValue + "\n");
            String text = area.getText();
            int subLength = text.length() - 50000;
            if (subLength > 1000) {
                area.setText(
                        text.substring(subLength)
                );
            }
        });
    }
}
