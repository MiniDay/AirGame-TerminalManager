package net.airgame.terminal.manager.data;

public class TerminalConfig {
    private String name;
    private String startCommand;
    private String workspace;

    public TerminalConfig(String name, String startCommand, String workspace) {
        this.name = name;
        this.startCommand = startCommand;
        this.workspace = workspace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartCommand() {
        return startCommand;
    }

    public void setStartCommand(String startCommand) {
        this.startCommand = startCommand;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return "TerminalConfig{" +
                "name='" + name + '\'' +
                ", startCommand='" + startCommand + '\'' +
                ", workspace='" + workspace + '\'' +
                '}';
    }
}
