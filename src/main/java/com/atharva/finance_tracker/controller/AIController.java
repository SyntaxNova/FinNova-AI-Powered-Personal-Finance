package com.atharva.finance_tracker.controller;

import com.atharva.finance_tracker.ai.GeminiService;
import com.atharva.finance_tracker.dto.AnalyticsSummaryResponse;
import com.atharva.finance_tracker.dto.CategoryBreakdownResponse;
import com.atharva.finance_tracker.dto.SavingsGoalResponse;
import com.atharva.finance_tracker.service.AnalyticsService;
import com.atharva.finance_tracker.service.SavingsGoalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final GeminiService geminiService;
    private final AnalyticsService analyticsService;
    private final SavingsGoalService savingsGoalService;

    public AIController(
            GeminiService geminiService,
            AnalyticsService analyticsService,
            SavingsGoalService savingsGoalService) {

        this.geminiService = geminiService;
        this.analyticsService = analyticsService;
        this.savingsGoalService = savingsGoalService;
    }

    @PostMapping("/insights")
    public String generateInsights() {

        AnalyticsSummaryResponse summary =
                analyticsService.getSummary();

        List<CategoryBreakdownResponse> categories =
                analyticsService.getCategoryBreakdown();

        List<SavingsGoalResponse> goals =
                savingsGoalService.getAllGoals();

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an expert personal finance advisor.

                Analyze the financial data below and provide:
                
                IMPORTANT:
                - Do NOT use Markdown.
                - Do NOT use ** symbols.
                - Do NOT use --- separators.
                - Do NOT use bullet points using *.
                - Use simple plain text.
                - Use numbered sections only.
                - Keep the response concise and easy to read.
                
                1. Spending analysis
                2. Savings analysis
                3. Financial health score out of 10
                4. Areas of overspending
                5. Goal progress analysis
                6. Estimated time to reach savings goals
                7. Practical recommendations
                
                place the Financial health score out of 10 and
                Estimated time to reach savings goals at the top as in box or 
                container for better visualization. 

                Currency: Indian Rupees (INR)

                Financial Data:

                """);

        prompt.append("Income: ")
                .append(summary.getTotalIncome())
                .append("\n");

        prompt.append("Expense: ")
                .append(summary.getTotalExpense())
                .append("\n");

        prompt.append("Net Savings: ")
                .append(summary.getNetSavings())
                .append("\n\n");

        prompt.append("Expense Categories:\n");

        for (CategoryBreakdownResponse category : categories) {

            prompt.append(category.getCategory())
                    .append(": ")
                    .append(category.getAmount())
                    .append("\n");
        }

        prompt.append("\n");
        prompt.append("Savings Goals:\n\n");

        if (goals.isEmpty()) {

            prompt.append("No savings goals available.\n");

        } else {

            for (SavingsGoalResponse goal : goals) {

                prompt.append("Goal: ")
                        .append(goal.getGoalName())
                        .append("\n");

                prompt.append("Target Amount: ")
                        .append(goal.getTargetAmount())
                        .append("\n");

                prompt.append("Current Amount: ")
                        .append(goal.getCurrentAmount())
                        .append("\n");

                prompt.append("Progress: ")
                        .append(goal.getProgressPercentage())
                        .append("%\n\n");
            }
        }

        return geminiService.generateInsights(
                prompt.toString()
        );
    }
}