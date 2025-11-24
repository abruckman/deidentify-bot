package com.embabel.template.agent;

import com.embabel.agent.api.common.Ai;
import org.springframework.stereotype.Component;

/**
 * Agent that generates a de-identification script for a given row of de-identified data using Embabel LLM.
 */
@Component
public class DeidentifyScriptGeneratorAgent {
    private final Ai ai;

    public DeidentifyScriptGeneratorAgent(Ai ai) {
        this.ai = ai;
    }

    public String generateScript(String deidentifiedRow) {
        String prompt = "Given the following row of de-identified data, generate a complete Python script that:\n" +
                "- Reads an input CSV file (input.csv) with the same format as the row below,\n" +
                "- De-identifies each row by generating fake data of the same kind as that field, if there is data in the field\n" +
                "- Writes the de-identified rows to a new CSV file (output.csv).\n" +
                "Assume the first row of the CSV is a header.\n" +
                "Only output the Python code.\n" +
                "Row: " + deidentifiedRow;
        return ai.withDefaultLlm().withId("deidentify-script").generateText(prompt);
    }
}
