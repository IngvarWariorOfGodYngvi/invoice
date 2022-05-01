package com.invoices.adapters;

import com.invoices.dailyreport.DailyReportProduct;
import com.invoices.dailyreport.DailyReportProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface DailyReportProductSQLRepository extends DailyReportProductRepository, JpaRepository<DailyReportProduct,String> {
}
