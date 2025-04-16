package com.directory.treeview.controller;

import com.directory.treeview.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/view-tree")
    public String viewTree(@RequestParam("path") String path, Model model) {
        String tree = directoryService.buildDirectoryTree(path);
        model.addAttribute("tree", tree);
        model.addAttribute("path", path);
        return "treeview";
    }
}
