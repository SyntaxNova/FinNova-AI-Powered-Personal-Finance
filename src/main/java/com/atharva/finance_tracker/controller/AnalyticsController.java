package com.atharva.finance_tracker.controller;

import com.atharva.finance_tracker.dto.AnalyticsSummaryResponse;
import com.atharva.finance_tracker.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atharva.finance_tracker.dto.CategoryBreakdownResponse;
import com.atharva.finance_tracker.dto.MonthlyTrendResponse;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(
            AnalyticsService analyticsService) {

        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public AnalyticsSummaryResponse getSummary() {

        return analyticsService.getSummary();
    }

    @GetMapping("/categories")
    public List<CategoryBreakdownResponse>
    getCategoryBreakdown() {

        return analyticsService
                .getCategoryBreakdown();
    }

    @GetMapping("/monthly")
    public List<MonthlyTrendResponse>
    getMonthlyTrend() {

        return analyticsService
                .getMonthlyTrend();
    }
}