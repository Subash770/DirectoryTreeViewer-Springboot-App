package com.directory.treeview.model;

public class RuleViolation {
    public int lineNumber;
    public String originalLine;
    public String optimizedLine;
    public String message;

    public RuleViolation(int lineNumber, String originalLine, String optimizedLine, String message) {
        this.lineNumber = lineNumber;
        this.originalLine = originalLine;
        this.optimizedLine = optimizedLine;
        this.message = message;
    }
}
