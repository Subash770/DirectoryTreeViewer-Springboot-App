package com.directory.treeview.util;

import com.directory.treeview.model.ControllerRule;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RuleLoader {

    public static List<ControllerRule> loadRules(String filePath) {
        List<ControllerRule> rules = new ArrayList<>();
        try (InputStream inputStream = RuleLoader.class.getResourceAsStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirst = true;
            for (Row row : sheet) {
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                ControllerRule rule = new ControllerRule();
                rule.setRuleId(row.getCell(0).getStringCellValue());
                rule.setDescription(row.getCell(1).getStringCellValue());
                rule.setCategory(row.getCell(2).getStringCellValue());
                rule.setSeverity(row.getCell(3).getStringCellValue());
                rule.setPriority((int) row.getCell(4).getNumericCellValue());
                rule.setPattern(row.getCell(5).getStringCellValue());
                rule.setSuggestion(row.getCell(6).getStringCellValue());
                rule.setMessage(row.getCell(7).getStringCellValue());
                rules.add(rule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
    }
}

