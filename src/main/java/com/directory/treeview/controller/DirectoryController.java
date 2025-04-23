package com.directory.treeview.controller;

import com.directory.treeview.model.ComparisonMetrics;
import com.directory.treeview.model.Suggestion;
import com.directory.treeview.service.CodeAnalyzerService;
import com.directory.treeview.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DirectoryController {



    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private CodeAnalyzerService codeAnalyzerService;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("folders", new String[]{"controller", "service", "repository", "model"});
        return "index";
    }

    @PostMapping("/view-folder")
    public String viewFolder(@RequestParam("path") String path,
                             @RequestParam("selectedFolders") List<String> selectedFolders,
                             Model model) {
        String folderTree = directoryService.filterByFolders(path, selectedFolders);
        model.addAttribute("tree", folderTree);
        model.addAttribute("path", path);
        return "treeview";
    }


    @GetMapping("/file-content")
    @ResponseBody
    public String getFileContent(@RequestParam("filePath") String filePath) throws IOException {
        return directoryService.getFileContent(filePath).replaceAll("\n", "<br>").replaceAll(" ", "&nbsp;");
    }
    //to load all the folders
    @GetMapping("/load-folders")
    @ResponseBody
    public List<String> loadFolders(@RequestParam("path") String path) {
        return directoryService.getAllFolders(path);
    }

    @GetMapping("/analyze-controller")
    public String analyzeControllerCode(@RequestParam("path") String projectPath, Model model) {
        long start = System.currentTimeMillis();
        List<Suggestion> suggestions = codeAnalyzerService.analyzeControllersInProject(projectPath);
        long end = System.currentTimeMillis();

        ComparisonMetrics metrics = codeAnalyzerService.generateComparisonMetrics(suggestions, end - start);

        model.addAttribute("suggestions", suggestions);
        model.addAttribute("metrics", metrics);
        return "suggestions";
    }



}
