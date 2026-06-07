package com.atharva.finance_tracker.dto;

public class SavingsGoalResponse {

    private Long id;
    private String goalName;
    private Double targetAmount;
    private Double currentAmount;
    private Double progressPercentage;

    public SavingsGoalResponse() {
    }

    public SavingsGoalResponse(
            Long id,
            String goalName,
            Double targetAmount,
            Double currentAmount,
            Double progressPercentage) {

        this.id = id;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.progressPercentage = progressPercentage;
    }

    public Long getId() {
        return id;
    }

    public String getGoalName() {
        return goalName;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }
}