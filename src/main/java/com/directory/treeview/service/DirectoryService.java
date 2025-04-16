package com.directory.treeview.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DirectoryService {

    public String buildDirectoryTree(String path) {
        File root = new File(path);
        if (root.exists() && root.isDirectory()) {
            return traverse(root, "");
        } else {
            return "Invalid path!";
        }
    }

    private String traverse(File folder, String indent) {
        StringBuilder result = new StringBuilder();
        if (folder.isDirectory()) {
            result.append(indent).append("📁 ").append(folder.getName()).append("<br>");
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    result.append(traverse(file, indent + "&nbsp;&nbsp;&nbsp;&nbsp;"));
                }
            }
        } else {
            result.append(indent).append("📄 ").append(folder.getName()).append("<br>");
        }
        return result.toString();
    }
}
