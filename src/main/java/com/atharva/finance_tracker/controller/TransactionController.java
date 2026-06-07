package com.atharva.finance_tracker.controller;

import com.atharva.finance_tracker.dto.TransactionRequest;
import com.atharva.finance_tracker.entity.Transaction;
import com.atharva.finance_tracker.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction addTransaction(
            @RequestBody TransactionRequest request) {

        return transactionService.addTransaction(request);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(
            @PathVariable Long id) {

        transactionService.deleteTransaction(id);

        return "Transaction Deleted Successfully";
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequest request) {

        return transactionService.updateTransaction(id, request);
    }
}