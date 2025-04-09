package com.expense_tracker.repository;

import com.expense_tracker.model.Expense;
import java.math.BigDecimal;
import java.util.List;

public  interface ExpenseRepository {

    public Expense save(Expense expense);
    public List<Expense> findAll();
    public   List<Expense> findForMonth(int month);
    public BigDecimal findSummary();
    public boolean delete(int expenseId);

}
