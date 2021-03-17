package net.airgame.terminal.manager.thread;

import javafx.application.Platform;
import net.airgame.terminal.manager.container.TerminalPane;

import java.util.ArrayList;

public class ExitMonitorThread extends Thread {
    private final ArrayList<TerminalPane> terminalPanes;

    private volatile boolean stop;

    public ExitMonitorThread() {
        terminalPanes = new ArrayList<>();
        stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            for (int i = 0; i < terminalPanes.size(); i++) {
                TerminalPane terminalPane = terminalPanes.get(i);
                Process process = terminalPane.getProcess();
                if (process.isAlive()) {
                    continue;
                }
                int exitCode = process.exitValue();
                Platform.runLater(() -> terminalPane.getOutputTextArea().appendText("程序已结束，退出代码: " + exitCode + "\n"));
                terminalPanes.remove(i);
                i--;
            }
        }
    }

    public void addTerminalPane(TerminalPane pane) {
        terminalPanes.add(pane);
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
