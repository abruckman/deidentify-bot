package com.embabel.template.web;

import com.embabel.template.agent.DeidentifyScriptGeneratorAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class DeidentifyWebController {
    private final DeidentifyScriptGeneratorAgent agent;

    @Autowired
    public DeidentifyWebController(DeidentifyScriptGeneratorAgent agent) {
        this.agent = agent;
    }

    @GetMapping("/deidentify")
    public String showForm() {
        return "deidentify-form";
    }

    @PostMapping(value = "/deidentify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String firstRow;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            firstRow = reader.readLine();
        }
        if (firstRow == null) {
            model.addAttribute("message", "Input file is empty.");
            return "deidentify-form";
        }
        String script = agent.generateScript(firstRow);
        model.addAttribute("script", script);
        return "deidentify-result";
    }
}
