package com.directory.treeview.util;

import com.directory.treeview.model.ControllerRule;
import com.directory.treeview.model.RuleViolation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ControllerRuleEngine {

    private static final List<ControllerRule> rules;

    static {
        rules = RuleLoader.loadRules("/controller_rules.xlsx");
    }

    public static List<RuleViolation> analyzeLine(String line, int lineNumber) {
        List<RuleViolation> violations = new ArrayList<>();
        String trimmed = line.trim();

        for (ControllerRule rule : rules) {
            try {
                Pattern compiledPattern = Pattern.compile(rule.getPattern());
                if (compiledPattern.matcher(trimmed).find()) {
                    violations.add(new RuleViolation(
                            lineNumber,
                            line,
                            rule.getSuggestion() + "\n" + line,
                            rule.getMessage()
                    ));
                }
            } catch (Exception e) {
                System.err.println("Invalid regex in rule: " + rule.getRuleId());
            }
        }

        return violations;
    }
}
