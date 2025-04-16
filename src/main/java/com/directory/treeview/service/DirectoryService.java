package com.directory.treeview.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

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
    public String filterByFolders(String path, List<String> folders) {
        File root = new File(path);
        if (root.exists() && root.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            for (String folder : folders) {
                sb.append(traverseAndFilter(root, "", folder));
            }
            return sb.toString();
        } else {
            return "Invalid path!";
        }
    }


    private String traverseAndFilter(File folder, String indent, String filter) {
        StringBuilder result = new StringBuilder();

        if (folder.isDirectory()) {
            if (folder.getName().equalsIgnoreCase(filter)) {
                // Only render this folder and its children (once)
                result.append("<div class='folder-toggle'>📁 ").append(folder.getName()).append("</div>");
                result.append("<div class='nested'>");

                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".java")) {
                            result.append("<div class='file' data-path='")
                                    .append(file.getAbsolutePath())
                                    .append("'>📄 ")
                                    .append(file.getName())
                                    .append("</div>");
                        } else if (file.isDirectory()) {
                            result.append(traverseAllJavaFiles(file));
                        }
                    }
                }

                result.append("</div>");

                return result.toString(); // ⚠️ Important: Stop here if matched
            } else {
                // Continue recursion if no match yet
                File[] subFolders = folder.listFiles(File::isDirectory);
                if (subFolders != null) {
                    for (File sub : subFolders) {
                        result.append(traverseAndFilter(sub, indent, filter));
                    }
                }
            }
        }

        return result.toString();
    }

    private String traverseAllJavaFiles(File folder) {
        StringBuilder result = new StringBuilder();

        if (folder.isDirectory()) {
            result.append("<div class='folder-toggle'>📁 ").append(folder.getName()).append("</div>");
            result.append("<div class='nested'>");

            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        result.append(traverseAllJavaFiles(file));
                    } else if (file.getName().endsWith(".java")) {
                        result.append("<div class='file' data-path='")
                                .append(file.getAbsolutePath())
                                .append("'>📄 ")
                                .append(file.getName())
                                .append("</div>");
                    }
                }
            }

            result.append("</div>");
        }

        return result.toString();
    }


    public String getFileContent(String fullPath) throws IOException {
        File file = new File(fullPath);
        if (!file.exists() || file.isDirectory()) return "Invalid file!";
        return Files.readString(file.toPath());
    }

    public List<String> getAllFolders(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return List.of(); // return empty list
        }

        File[] folders = dir.listFiles(File::isDirectory);
        if (folders != null) {
            return Arrays.stream(folders)
                    .map(File::getName)
                    .toList();
        }
        return List.of();
    }


}
