package com.atharva.finance_tracker.repository;

import com.atharva.finance_tracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import com.atharva.finance_tracker.entity.User;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);
}