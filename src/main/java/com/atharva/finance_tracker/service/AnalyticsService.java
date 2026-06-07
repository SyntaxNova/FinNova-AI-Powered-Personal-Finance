package com.atharva.finance_tracker.service;

import com.atharva.finance_tracker.dto.AnalyticsSummaryResponse;
import com.atharva.finance_tracker.entity.Transaction;
import com.atharva.finance_tracker.entity.TransactionType;
import com.atharva.finance_tracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import com.atharva.finance_tracker.dto.MonthlyTrendResponse;
import com.atharva.finance_tracker.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Month;
import java.util.LinkedHashMap;
import com.atharva.finance_tracker.dto.CategoryBreakdownResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalyticsService {

    private final TransactionRepository transactionRepository;

    public AnalyticsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public AnalyticsSummaryResponse getSummary() {

        User user = (User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        List<Transaction> transactions =
                transactionRepository.findByUser(user);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {

            if (transaction.getType() == TransactionType.INCOME) {

                totalIncome =
                        totalIncome.add(transaction.getAmount());

            } else {

                totalExpense =
                        totalExpense.add(transaction.getAmount());
            }
        }

        BigDecimal netSavings =
                totalIncome.subtract(totalExpense);

        return new AnalyticsSummaryResponse(
                totalIncome,
                totalExpense,
                netSavings
        );
    }

    public List<CategoryBreakdownResponse> getCategoryBreakdown() {

        User user = (User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        List<Transaction> transactions =
                transactionRepository.findByUser(user);

        Map<String, BigDecimal> categoryMap =
                new HashMap<>();

        for (Transaction transaction : transactions) {

            if (transaction.getType() ==
                    TransactionType.EXPENSE) {

                categoryMap.put(
                        transaction.getCategory(),
                        categoryMap.getOrDefault(
                                transaction.getCategory(),
                                BigDecimal.ZERO
                        ).add(transaction.getAmount())
                );
            }
        }

        List<CategoryBreakdownResponse> response =
                new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry :
                categoryMap.entrySet()) {

            response.add(
                    new CategoryBreakdownResponse(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        return response;
    }

    public List<MonthlyTrendResponse> getMonthlyTrend() {

        User user = (User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        List<Transaction> transactions =
                transactionRepository.findByUser(user);

        Map<String, BigDecimal> incomeMap =
                new LinkedHashMap<>();

        Map<String, BigDecimal> expenseMap =
                new LinkedHashMap<>();

        for (Transaction transaction : transactions) {

            String month =
                    transaction.getTransactionDate()
                            .getMonth()
                            .toString();

            if (transaction.getType() ==
                    TransactionType.INCOME) {

                incomeMap.put(
                        month,
                        incomeMap.getOrDefault(
                                month,
                                BigDecimal.ZERO
                        ).add(transaction.getAmount())
                );

            } else {

                expenseMap.put(
                        month,
                        expenseMap.getOrDefault(
                                month,
                                BigDecimal.ZERO
                        ).add(transaction.getAmount())
                );
            }
        }

        List<MonthlyTrendResponse> response =
                new ArrayList<>();

        for (String month : incomeMap.keySet()) {

            response.add(
                    new MonthlyTrendResponse(
                            month,
                            incomeMap.getOrDefault(
                                    month,
                                    BigDecimal.ZERO
                            ),
                            expenseMap.getOrDefault(
                                    month,
                                    BigDecimal.ZERO
                            )
                    )
            );
        }

        return response;
    }


}