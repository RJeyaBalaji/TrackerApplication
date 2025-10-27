package com.dailystatus.dailyupdate.service;

import com.dailystatus.dailyupdate.entity.DailyReport;
import com.dailystatus.dailyupdate.entity.DailyReportHistory;
import com.dailystatus.dailyupdate.repository.DailyReportHistoryRepository;
import com.dailystatus.dailyupdate.repository.DailyReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReportService {

    private final DailyReportRepository dailyReportRepository;
    private final DailyReportHistoryRepository historyRepository;

    public ReportService(DailyReportRepository dailyReportRepository,
                         DailyReportHistoryRepository historyRepository) {
        this.dailyReportRepository = dailyReportRepository;
        this.historyRepository = historyRepository;
    }

    @Transactional
    public void moveToHistory() {
        moveToHistory(null);
    }

    @Transactional
    public void moveToHistory(LocalDate date) {
        LocalDate targetDate = (date != null) ? date : LocalDate.now().minusDays(1);

        log.info("Moving reports to history for date: {}", targetDate);

        List<DailyReport> reports = dailyReportRepository.findByReportDate(targetDate);

        if (reports.isEmpty()) {
            log.info("No reports found for {}", targetDate);
            return;
        }

        for (DailyReport report : reports) {
            DailyReportHistory history = new DailyReportHistory();

            history.setEmployeeName(report.getEmployeeName());
            history.setReportDate(report.getReportDate());
            history.setSprintNo(report.getSprintNo() != null ? report.getSprintNo() : "");
            history.setTicketNo(report.getTicketNo() != null ? report.getTicketNo() : "");
            history.setParentPc(report.getParentPc() != null ? report.getParentPc() : "");
            history.setWorkPlanned(report.getWorkPlanned() != null ? report.getWorkPlanned() : "");
            history.setEstimation(report.getEstimation() != null ? report.getEstimation() : BigDecimal.ZERO);
            history.setStatus(report.getStatus() != null ? report.getStatus() : "");
            history.setActualTime(report.getActualTime() != null ? report.getActualTime() : BigDecimal.ZERO);
            history.setReasonForDelay(report.getReasonForDelay() != null ? report.getReasonForDelay() : "");
            history.setComments(report.getComments() != null ? report.getComments() : "");

            historyRepository.save(history);
            dailyReportRepository.delete(report);
        }

        log.info("Moved {} records to history for {}", reports.size(), targetDate);
    }
}
