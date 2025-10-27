package com.dailystatus.dailyupdate.repository;

import com.dailystatus.dailyupdate.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {

    Optional<DailyReport> findByEmployeeNameAndTicketNoAndReportDate(
            String employeeName, String ticketNo, LocalDate reportDate);

    void deleteByReportDate(LocalDate reportDate);

    List<DailyReport> findByReportDate(LocalDate reportDate);
}
