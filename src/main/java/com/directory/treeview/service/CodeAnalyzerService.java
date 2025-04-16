package com.directory.treeview.service;

import com.directory.treeview.model.RuleViolation;
import com.directory.treeview.model.Suggestion;
import com.directory.treeview.util.ControllerRuleEngine;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeAnalyzerService {

    public List<Suggestion> analyzeFile(File file) {
        List<Suggestion> suggestions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                List<RuleViolation> violations = ControllerRuleEngine.analyzeLine(line, lineNumber);

                for (RuleViolation violation : violations) {
                    suggestions.add(new Suggestion(
                            file.getName(), lineNumber, line.trim(), violation.optimizedLine, violation.message
                    ));
                }

                lineNumber++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }
    public List<Suggestion> analyzeControllersInProject(String projectPath) {
        List<Suggestion> allSuggestions = new ArrayList<>();
        File root = new File(projectPath);

        if (!root.exists() || !root.isDirectory()) {
            return allSuggestions; // Return empty if path is invalid
        }

        scanAndAnalyze(root, allSuggestions);
        return allSuggestions;
    }

    private void scanAndAnalyze(File dir, List<Suggestion> allSuggestions) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanAndAnalyze(file, allSuggestions); // Recursively check subfolders
            } else if (file.getName().endsWith(".java") && file.getName().contains("Controller")) {
                allSuggestions.addAll(analyzeFile(file)); // Only analyze controller files
            }
        }
    }

}
