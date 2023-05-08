package com.armaninvestment.parsparandreporter.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportItemService {
    @PersistenceContext
    public EntityManager entityManager;

    List<Object[]> getReportItems(LocalDate startDate, LocalDate endDate) {
        List<Object[]> resultList = new ArrayList<>();
        List<Object[]> reportItemList = entityManager.createQuery(
                        "SELECT ri.quantity, r.date, ri.unitPrice, c.name " +
                                "FROM ReportItem ri " +
                                "JOIN ri.report r " +
                                "JOIN ri.customer c " +
                                "WHERE r.date BETWEEN :startDate AND :endDate", Object[].class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

        YearMonth startMonth = YearMonth.from(startDate);
        YearMonth endMonth = YearMonth.from(endDate);
        int currentMonthCount = 0;
        int annualCount = 0;
        long totalSales = 0L;
        double totalRevenue = 0D;

        for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
            int daysInMonth = month.lengthOfMonth();
            LocalDate monthStartDate = month.atDay(1);
            LocalDate monthEndDate = month.atEndOfMonth();
            long monthlySales = 0L;
            double monthlyRevenue = 0D;
            for (Object[] row : reportItemList) {
                LocalDate reportDate = (LocalDate) row[1];
                if (reportDate.isBefore(monthStartDate) || reportDate.isAfter(monthEndDate)) {
                    continue;
                }
                Integer quantity = (Integer) row[0];
                Long unitPrice = (Long) row[2];
                monthlySales += quantity;
                monthlyRevenue += quantity * unitPrice.doubleValue() / 1000;
            }
            annualCount += daysInMonth;
            currentMonthCount += month.equals(startMonth) ? daysInMonth - startDate.getDayOfMonth() + 1 : daysInMonth;
            totalSales += monthlySales;
            totalRevenue += monthlyRevenue;
            double averagePrice = monthlySales > 0 ? monthlyRevenue / monthlySales : 0;
            double percentage = annualCount > 0 ? (double) currentMonthCount / annualCount : 0;
            Object[] resultRow = new Object[] {
                    month.getMonth().getValue(),
                    currentMonthCount,
                    annualCount,
                    averagePrice,
                    totalSales,
                    percentage
            };
            resultList.add(resultRow);
        }
        return resultList;
    }
}
