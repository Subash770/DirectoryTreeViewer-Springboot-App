package com.directory.treeview.util;

import com.directory.treeview.model.RuleViolation;

import java.util.ArrayList;
import java.util.List;

public class ControllerRuleEngine {

    public static List<RuleViolation> analyzeLine(String line, int lineNumber) {
        List<RuleViolation> violations = new ArrayList<>();

        // Rule 1: Too Much Business Logic in Controller (High Priority)
        if (line.contains("public") && line.contains("@PostMapping") && line.contains("orderService")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Extract business logic to the service layer\n" + line,
                    "Too much business logic in controller"
            ));
        }

        // Rule 2: Lack of Security Measures (High Priority)
        if (line.contains("@GetMapping") && !line.contains("@PreAuthorize")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("@GetMapping", "@PreAuthorize(\"hasRole('ADMIN')\") @GetMapping"),
                    "Lack of security measures: Add authentication and authorization"
            ));
        }

        // Rule 3: No Validation for Request Bodies (High Priority)
        if (line.contains("@RequestBody") && !line.contains("@Valid")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("@RequestBody", "@Valid @RequestBody"),
                    "No validation for request body: Add @Valid annotation"
            ));
        }

        // Rule 4: Improper Exception Handling (High Priority)
        if (line.contains("Exception") && !line.contains("@ControllerAdvice")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Add proper global exception handling via @ControllerAdvice\n" + line,
                    "Improper exception handling"
            ));
        }

        // Rule 5: Missing or Hardcoded CORS Configuration (High Priority)
        if (line.contains("@RequestMapping") && !line.contains("@CrossOrigin")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line + "\n// Add CORS configuration\n",
                    "Missing or hardcoded CORS configuration"
            ));
        }

        // Rule 6: Improper Use of ResponseEntity (Medium Priority)
        if (line.contains("return ResponseEntity") && !line.contains("HttpStatus")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("return ResponseEntity", "return ResponseEntity.status(HttpStatus.CREATED)"),
                    "Improper use of ResponseEntity: Use appropriate HTTP status codes"
            ));
        }

        // Rule 7: Overusing @RestController for Everything (Medium Priority)
        if (line.contains("@RestController") && line.contains("return") && !line.contains("ModelAndView")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Use @Controller for HTML views\n" + line,
                    "Overusing @RestController: Use @Controller for views"
            ));
        }

        // Rule 8: Repetitive Code in Controllers (Medium Priority)
        if (line.contains("new Date()") || line.contains("new SimpleDateFormat")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Extract repetitive code into utility methods\n" + line,
                    "Repetitive code detected: Extract to utility class"
            ));
        }

        // Rule 9: Unused or Unclear Endpoints (Low Priority)
        if (line.contains("@GetMapping") && !line.contains("return")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Clarify or remove unused endpoint\n" + line,
                    "Unused or unclear endpoint"
            ));
        }

        // Rule 10: No Logging or Too Much Logging (Low Priority)
        if (line.contains("System.out.println")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("System.out.println", "logger.info"),
                    "No logging or improper logging: Use SLF4J logger"
            ));
        }

        return violations;
    }
}
