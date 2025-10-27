package com.dailystatus.dailyupdate.controller;

import com.dailystatus.dailyupdate.service.ReportAutomationService;
import com.dailystatus.dailyupdate.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/automation")
@Slf4j
public class AutomationController {

    private final ReportAutomationService automationService;
    private final ReportService reportService;

    public AutomationController(ReportAutomationService automationService, ReportService reportService) {
        this.automationService = automationService;
        this.reportService = reportService;
    }

    @PostMapping("/run")
    public String runAutomationManually(@RequestParam(required = false) String date) {
        try {
            LocalDate targetDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
            log.info("Manual trigger: Running automation for {}", targetDate);
            automationService.runAutomation(targetDate);
            return "Report automation executed successfully for " + targetDate + "!";
        } catch (Exception e) {
            log.error("Error running automation manually: {}", e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/move-to-history")
    public String moveToHistoryManually(@RequestParam(required = false) String date) {
        try {
            LocalDate targetDate = (date != null) ? LocalDate.parse(date) : null;
            log.info("Manual trigger: Moving data to history for {}",
                    (targetDate != null ? targetDate : "yesterday"));
            reportService.moveToHistory(targetDate);
            return "Moved data to history for " +
                    (targetDate != null ? targetDate : "yesterday") + "!";
        } catch (Exception e) {
            log.error("Error moving data to history manually: {}", e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/run-and-move")
    public String runAndMoveHistory(@RequestParam(required = false) String date) {
        try {
            LocalDate targetDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
            log.info("Manual trigger: Running full automation for {}", targetDate);

            automationService.runAutomation(targetDate);

            reportService.moveToHistory(targetDate);

            return "Full automation (import + history) completed successfully for " + targetDate + "!";
        } catch (Exception e) {
            log.error("Error in full automation flow: {}", e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }
}
