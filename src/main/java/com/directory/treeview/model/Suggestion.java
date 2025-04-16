package com.directory.treeview.model;

public class Suggestion {
    private String fileName;
    private int lineNumber;
    private String originalLine;
    private String optimizedLine;
    private String message;

    public Suggestion(String fileName, int lineNumber, String originalLine, String optimizedLine, String message) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.originalLine = originalLine;
        this.optimizedLine = optimizedLine;
        this.message = message;
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
}
