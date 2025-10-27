package com.dailystatus.dailyupdate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class ReportAutomationService {

    private final SharePointDownloadService sharePointDownloadService;
    private final ExcelImportService excelImportService;

    public ReportAutomationService(SharePointDownloadService sharePointDownloadService,
                                   ExcelImportService excelImportService) {
        this.sharePointDownloadService = sharePointDownloadService;
        this.excelImportService = excelImportService;
    }

    public void runAutomation() {
        runAutomation(LocalDate.now());
    }

    public void runAutomation(LocalDate date) {
        try {
            log.info("Starting report automation for date: {}", date);

            String downloadedFilePath = sharePointDownloadService.downloadReport();
            log.info("File downloaded successfully at: {}", downloadedFilePath);

            excelImportService.importExcel(downloadedFilePath, date);

            log.info("Report automation completed successfully for {}.", date);

        } catch (Exception e) {
            log.error("Report automation failed for {}: {}", date, e.getMessage(), e);
        }
    }
}
