package com.atharva.finance_tracker.controller;

import com.atharva.finance_tracker.dto.SavingsGoalRequest;
import com.atharva.finance_tracker.dto.SavingsGoalResponse;
import com.atharva.finance_tracker.service.SavingsGoalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class SavingsGoalController {

    private final SavingsGoalService savingsGoalService;

    public SavingsGoalController(
            SavingsGoalService savingsGoalService) {

        this.savingsGoalService = savingsGoalService;
    }

    @PostMapping
    public SavingsGoalResponse createGoal(
            @RequestBody SavingsGoalRequest request) {

        return savingsGoalService.createGoal(request);
    }

    @GetMapping
    public List<SavingsGoalResponse> getAllGoals() {

        return savingsGoalService.getAllGoals();
    }

    @PutMapping("/{id}")
    public SavingsGoalResponse updateGoal(
            @PathVariable Long id,
            @RequestBody SavingsGoalRequest request) {

        return savingsGoalService.updateGoal(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteGoal(
            @PathVariable Long id) {

        savingsGoalService.deleteGoal(id);

        return "Goal Deleted Successfully";
    }

    @GetMapping("/test")
    public String test() {
        return "Savings Goal Controller Working";
    }
}