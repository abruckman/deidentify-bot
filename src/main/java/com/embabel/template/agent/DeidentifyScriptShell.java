package com.embabel.template.agent;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@ShellComponent
public class DeidentifyScriptShell {
    private final DeidentifyScriptGeneratorAgent agent;

    @Autowired
    public DeidentifyScriptShell(DeidentifyScriptGeneratorAgent agent) {
        this.agent = agent;
    }

    @ShellMethod("Generate a de-identification script from a local file and write the Python script to a local file.")
    public String deidentifyScriptFromFile(@ShellOption(help = "Path to the input file") String inputFile,
                                           @ShellOption(defaultValue = "deidentify.py", help = "Output Python script filename") String outputFile) {
        String firstRow;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            firstRow = reader.readLine();
            if (firstRow == null) {
                return "Input file is empty.";
            }
        } catch (IOException e) {
            return "Failed to read input file: " + e.getMessage();
        }

        String script = agent.generateScript(firstRow);
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(script);
        } catch (IOException e) {
            return "Failed to write output file: " + e.getMessage();
        }
        return "Python de-identification script written to: " + outputFile;
    }
}
