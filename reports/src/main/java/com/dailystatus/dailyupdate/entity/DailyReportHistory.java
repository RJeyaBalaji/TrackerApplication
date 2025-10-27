package com.dailystatus.dailyupdate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DailyReportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private DailyReport report;

    private String employeeName;

    private LocalDate reportDate;
    private String sprintNo;
    private String ticketNo;
    private String parentPc;
    private String workPlanned;
    private BigDecimal estimation;
    private String status;
    private BigDecimal actualTime;
    private String reasonForDelay;
    @Lob
    private String comments;
    private LocalDateTime versionTimestamp = LocalDateTime.now();

    @Transient
    private String resourceName;
}
