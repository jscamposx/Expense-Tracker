package com.expense_tracker.service;


import com.expense_tracker.model.Expense;

import com.expense_tracker.repository.FileExpenseRepository;
import org.springframework.stereotype.Service;




@Service
public class ExpenseService {

    private final FileExpenseRepository expenseRepository;

    public ExpenseService(FileExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    public Expense createExpense(String expenseName, float expenseAmount) {
        Expense newExpense = new Expense(expenseName,  expenseAmount);
        return expenseRepository.save(newExpense);
    }




}
