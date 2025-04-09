package com.expense_tracker.repository;

import com.expense_tracker.model.Expense;

public  interface ExpenseRepository {
    public Expense save(Expense expense);
}
