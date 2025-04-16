package com.directory.treeview.controller;

import com.directory.treeview.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

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

}
