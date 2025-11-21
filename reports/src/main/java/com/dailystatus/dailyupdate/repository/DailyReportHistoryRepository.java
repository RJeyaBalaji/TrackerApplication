package com.dailystatus.dailyupdate.repository;

import com.dailystatus.dailyupdate.entity.DailyReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface
DailyReportHistoryRepository extends JpaRepository<DailyReportHistory, Long> {

    List<DailyReportHistory> findByEmployeeNameIgnoreCase(String employeeName);
    List<DailyReportHistory> findByEmployeeNameIgnoreCaseAndReportDateBetween(String employeeName, LocalDate start, LocalDate end);
    List<DailyReportHistory> findByReportDateBetween(LocalDate start, LocalDate end);

}


