package com.directory.treeview.model;

public class Suggestion {
    private String fileName;
    private int lineNumber;
    private String originalLine;
    private String optimizedLine;
    private String message;
    private String priority;

    public Suggestion(String fileName, int lineNumber, String originalLine, String optimizedLine, String message) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.originalLine = originalLine;
        this.optimizedLine = optimizedLine;
        this.message = message;
        this.priority = determinePriority(message); // Automatically assign priority
    }



    // Priority decision logic
    private String determinePriority(String message) {
        String lower = message.toLowerCase();
        if (lower.contains("security") || lower.contains("vulnerable") || lower.contains("unauthorized")) {
            return "High";
        } else if (lower.contains("performance") || lower.contains("inefficient") || lower.contains("optimize")) {
            return "Medium";
        } else if (lower.contains("code style") || lower.contains("readability") || lower.contains("formatting")
                || lower.contains("exception") || lower.contains("slf4j") || lower.contains("logger")) {
            return "Low";
        } else {
            return "Medium"; // default if no keywords match
        }
    }


    // Getters
    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getOriginalLine() {
        return originalLine;
    }

    public String getOptimizedLine() {
        return optimizedLine;
    }

    public String getMessage() {
        return message;
    }

    public String getPriority() {
        return priority;
    }
}