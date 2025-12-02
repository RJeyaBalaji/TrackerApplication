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
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
public class ReportService {

    private DailyReportRepository dailyReportRepository;
    private DailyReportHistoryRepository historyRepository;

    public ReportService(DailyReportRepository dailyReportRepository,
                         DailyReportHistoryRepository historyRepository) {
        this.dailyReportRepository = dailyReportRepository;
        this.historyRepository = historyRepository;
    }

    @Transactional
    public void moveToHistory() {
        moveToHistory(LocalDate.now().plusDays(2));
    }

    @Transactional
    public void moveToHistory(LocalDate date) {

        LocalDate targetDate = (date != null)
                ? date
                : LocalDate.now(ZoneId.of("Asia/Kolkatta")).minusDays(-5);

        log.info("Moving reports to history for date: {}", targetDate.toString().toUpperCase());

        List<DailyReport> reports = dailyReportRepository.findByReportDate(targetDate);

        if (reports.isEmpty()) {
            log.error("No reports found for {}", targetDate);
            return;
        }

        for (int i = 0; i < reports.size(); i++) {

            DailyReport report = reports.get(i);

            DailyReportHistory history = new DailyReportHistory();
            history = new DailyReportHistory();

            history.setEmployeeName(report.getEmployeeName());
            history.setReportDate(report.getReportDate());

            history.setSprintNo("SPR-DEFAULT");
            history.setStatus("UNKNOWN");

            history.setEstimation(new BigDecimal("00.00"));
            history.setActualTime(BigDecimal.ONE.negate());

            report.setComments("Moved to history");

            historyRepository.save(history);

            dailyReportRepository.delete(report);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }

        log.debug("Moved reports!!");
    }
}
