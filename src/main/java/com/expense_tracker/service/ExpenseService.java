package com.expense_tracker.service;

import com.expense_tracker.model.Expense;
import com.expense_tracker.repository.FileExpenseRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    private final FileExpenseRepository expenseRepository;

    public ExpenseService(FileExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(String expenseName, BigDecimal expenseAmount) {
        Expense newExpense = new Expense(expenseName, expenseAmount);
        return expenseRepository.save(newExpense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesForMonth(int month) {
        return expenseRepository.findForMonth(month);
    }

    public BigDecimal getExpenseSummary() {
        return expenseRepository.findSummary();
    }

    public boolean deleteExpense(int expenseId) {
        return expenseRepository.delete(expenseId);
    }
}
