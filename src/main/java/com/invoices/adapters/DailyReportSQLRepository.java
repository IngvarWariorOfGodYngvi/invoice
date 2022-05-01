package com.invoices.adapters;

import com.invoices.dailyreport.DailyReport;
import com.invoices.dailyreport.DailyReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface DailyReportSQLRepository extends DailyReportRepository, JpaRepository<DailyReport,String> {
}
