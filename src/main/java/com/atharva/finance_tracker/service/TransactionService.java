package com.atharva.finance_tracker.service;

import com.atharva.finance_tracker.dto.TransactionRequest;
import com.atharva.finance_tracker.entity.Transaction;
import com.atharva.finance_tracker.entity.User;
import com.atharva.finance_tracker.repository.TransactionRepository;
import com.atharva.finance_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction addTransaction(TransactionRequest request) {

        User user = getCurrentUser();

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .category(request.getCategory())
                .type(request.getType())
                .description(request.getDescription())
                .transactionDate(request.getTransactionDate())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {

        User user = getCurrentUser();

        return transactionRepository.findByUser(user);
    }

    public void deleteTransaction(Long id) {

        User user = getCurrentUser();

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        transactionRepository.delete(transaction);
    }

    public Transaction updateTransaction(
            Long id,
            TransactionRequest request) {

        User user = getCurrentUser();

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());

        return transactionRepository.save(transaction);
    }
    private User getCurrentUser() {

        User user = (User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return user;
    }
}