package com.invoices.dailyreport;

import com.invoices.category.Category;

import java.util.List;

public interface DailyReportRepository {

    List<DailyReport> findAll();

    DailyReport save(DailyReport entity);

    DailyReport getById(String uuid);

    boolean existsById(String id);
}
