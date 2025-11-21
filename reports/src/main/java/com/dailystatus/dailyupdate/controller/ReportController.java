package com.dailystatus.dailyupdate.controller;

import com.dailystatus.dailyupdate.entity.DailyReportHistory;
import com.dailystatus.dailyupdate.entity.Employee;
import com.dailystatus.dailyupdate.repository.DailyReportHistoryRepository;
import com.dailystatus.dailyupdate.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    @Autowired
    private DailyReportHistoryRepository historyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DailyReportHistory>> getReports(
            @RequestParam(value = "employee", required = false) String employee,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate != null && endDate == null) {
            endDate = startDate;
        }
        if (endDate != null && startDate == null) {
            startDate = endDate;
        }

        List<DailyReportHistory> results;

        if (startDate != null && endDate != null) {
            if (employee != null && !employee.isBlank()) {
                results = historyRepository.findByEmployeeNameIgnoreCaseAndReportDateBetween(employee, startDate, endDate);
            } else {
                results = historyRepository.findByReportDateBetween(startDate, endDate);
            }
        } else {
            if (employee != null && !employee.isBlank()) {
                results = historyRepository.findByEmployeeNameIgnoreCase(employee);
            } else {
                results = historyRepository.findAll();
            }
        }

        return ResponseEntity.ok(results);
    }
}
