package com.invoices.dailyreport;

import java.util.List;

public interface DailyReportProductRepository {

    List<DailyReportProduct> findAll();

    DailyReportProduct save(DailyReportProduct entity);

    DailyReportProduct getById(String uuid);

    boolean existsById(String id);
}
