package net.airgame.terminal.manager.core;

import net.airgame.terminal.manager.data.TerminalConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static String inputCharset;
    private static String outputCharset;

    private static ArrayList<TerminalConfig> terminalConfigs;

    @SuppressWarnings("unchecked")
    public static void init() throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream("config.yml"), StandardCharsets.UTF_8);
        HashMap<String, Object> config = new Yaml().load(reader);
        reader.close();
        inputCharset = config.get("inputCharset").toString();
        outputCharset = config.get("outputCharset").toString();

        HashMap<String, HashMap<String, String>> terminals = (HashMap<String, HashMap<String, String>>) config.get("terminals");

        terminalConfigs = new ArrayList<>();
        for (Map.Entry<String, HashMap<String, String>> entry : terminals.entrySet()) {
            HashMap<String, String> value = entry.getValue();
            terminalConfigs.add(new TerminalConfig(
                    entry.getKey(),
                    value.get("startCommand"),
                    value.get("workspace")
            ));
        }
    }

    public static String getInputCharset() {
        return inputCharset;
    }

    public static String getOutputCharset() {
        return outputCharset;
    }

    public static ArrayList<TerminalConfig> getTerminalConfigs() {
        return terminalConfigs;
    }
}
