package com.directory.treeview.util;

import com.directory.treeview.model.RuleViolation;

import java.util.ArrayList;
import java.util.List;

public class ControllerRuleEngine {

    public static List<RuleViolation> analyzeLine(String line, int lineNumber) {
        List<RuleViolation> violations = new ArrayList<>();

        String trimmed = line.trim();

        // Rule 1: Business Logic in Controller (High Priority)
        if (trimmed.contains("@PostMapping") || trimmed.contains("@PutMapping")) {
            if (trimmed.contains("orderService") || trimmed.contains("userService")) {
                violations.add(new RuleViolation(
                        lineNumber,
                        line,
                        "// Move business logic to a service class\n" + line,
                        "Too much business logic in controller"
                ));
            }
        }

        // Rule 2: Lack of Security Measures (High Priority)
        if (trimmed.contains("@GetMapping") && !trimmed.contains("@PreAuthorize")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "@PreAuthorize(\"hasRole('USER')\")\n" + line,
                    "Missing security: Add @PreAuthorize for authorization"
            ));
        }

        // Rule 3: Missing @Valid for RequestBody (High Priority)
        if (trimmed.contains("@RequestBody") && !trimmed.contains("@Valid")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("@RequestBody", "@Valid @RequestBody"),
                    "Missing validation: Add @Valid to request body"
            ));
        }

        // Rule 4: Exception handling not delegated to @ControllerAdvice (High Priority)
        if (trimmed.contains("throw new RuntimeException") || trimmed.contains("Exception")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Consider using @ControllerAdvice to handle exceptions\n" + line,
                    "No centralized exception handling"
            ));
        }

        // Rule 5: Missing or Hardcoded CORS Config (High Priority)
        if (trimmed.contains("@GetMapping") && !trimmed.contains("@CrossOrigin")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "@CrossOrigin(origins = \"*\")\n" + line,
                    "Missing or hardcoded CORS configuration"
            ));
        }

        // Rule 6: Improper ResponseEntity (Medium Priority)
        if (trimmed.contains("return ResponseEntity") && !trimmed.contains("HttpStatus")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("ResponseEntity.ok", "ResponseEntity.status(HttpStatus.OK)"),
                    "Use proper HttpStatus with ResponseEntity"
            ));
        }

        // Rule 7: Returning views via @RestController (Medium Priority)
        if (trimmed.contains("return") && trimmed.contains("home page") && trimmed.contains("@RestController")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Use @Controller instead of @RestController for views\n" + line,
                    "Returning views from a @RestController"
            ));
        }

        // Rule 8: Repetitive Null Checks or Validation (Medium Priority)
        if (trimmed.contains("== null") || trimmed.contains(".isEmpty()")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Move validation logic to service or use @Validated\n" + line,
                    "Repeated validation logic in controller"
            ));
        }

        // Rule 9: No logger usage (Low Priority)
        if (trimmed.contains("System.out.println")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    line.replace("System.out.println", "logger.info"),
                    "Use SLF4J logger instead of System.out"
            ));
        }

        // Rule 10: Unclear endpoints with no meaningful return (Low Priority)
        if (trimmed.contains("@GetMapping") && trimmed.contains("unused-endpoint")) {
            violations.add(new RuleViolation(
                    lineNumber,
                    line,
                    "// Remove or repurpose this endpoint\n" + line,
                    "Unclear or unused endpoint"
            ));
        }

        return violations;
    }
}
