package net.airgame.terminal.manager.data;

import net.airgame.terminal.manager.core.ConfigManager;

public class TerminalConfig {
    private final String name;
    private final String startCommand;
    private final String workspace;

    private final String inputCharset;
    private final String outputCharset;

    public TerminalConfig(String name, String startCommand, String workspace, String inputCharset, String outputCharset) {
        this.name = name;
        this.startCommand = startCommand;
        this.workspace = workspace;
        this.inputCharset = inputCharset == null ? ConfigManager.getInputCharset() : inputCharset;
        this.outputCharset = outputCharset == null ? ConfigManager.getOutputCharset() : outputCharset;
    }

    public String getName() {
        return name;
    }

    public String getStartCommand() {
        return startCommand;
    }

    public String getWorkspace() {
        return workspace;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public String getOutputCharset() {
        return outputCharset;
    }

    @Override
    public String toString() {
        return "TerminalConfig{" +
                "name='" + name + '\'' +
                ", startCommand='" + startCommand + '\'' +
                ", workspace='" + workspace + '\'' +
                ", inputCharset='" + inputCharset + '\'' +
                ", outputCharset='" + outputCharset + '\'' +
                '}';
    }
}
