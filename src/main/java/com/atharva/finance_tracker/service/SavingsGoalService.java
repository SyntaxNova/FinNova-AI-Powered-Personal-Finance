package com.atharva.finance_tracker.service;

import com.atharva.finance_tracker.dto.SavingsGoalRequest;
import com.atharva.finance_tracker.dto.SavingsGoalResponse;
import com.atharva.finance_tracker.entity.SavingsGoal;
import com.atharva.finance_tracker.entity.User;
import com.atharva.finance_tracker.repository.SavingsGoalRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;

    public SavingsGoalService(
            SavingsGoalRepository savingsGoalRepository) {

        this.savingsGoalRepository = savingsGoalRepository;
    }

    public SavingsGoalResponse createGoal(
            SavingsGoalRequest request) {

        User user = (User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        SavingsGoal goal = new SavingsGoal();

        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());
        goal.setUser(user);

        SavingsGoal savedGoal =
                savingsGoalRepository.save(goal);

        return mapToResponse(savedGoal);
    }

    public List<SavingsGoalResponse> getAllGoals() {

        User user = (User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return savingsGoalRepository
                .findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SavingsGoalResponse updateGoal(
            Long id,
            SavingsGoalRequest request) {

        SavingsGoal goal =
                savingsGoalRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Goal not found"));

        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());

        SavingsGoal updatedGoal =
                savingsGoalRepository.save(goal);

        return mapToResponse(updatedGoal);
    }

    public void deleteGoal(Long id) {

        savingsGoalRepository.deleteById(id);
    }

    private SavingsGoalResponse mapToResponse(
            SavingsGoal goal) {

        double progress = 0;

        if (goal.getTargetAmount() != null
                && goal.getTargetAmount() > 0) {

            progress =
                    (goal.getCurrentAmount()
                            / goal.getTargetAmount()) * 100;

            progress = Math.round(progress * 100.0) / 100.0;
        }

        return new SavingsGoalResponse(
                goal.getId(),
                goal.getGoalName(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                progress
        );
    }
}