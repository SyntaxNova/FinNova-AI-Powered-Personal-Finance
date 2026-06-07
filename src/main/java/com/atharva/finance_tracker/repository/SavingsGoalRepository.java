package com.atharva.finance_tracker.repository;

import com.atharva.finance_tracker.entity.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.atharva.finance_tracker.entity.User;

import java.util.List;

@Repository
public interface SavingsGoalRepository
        extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByUser(User user);
}