package com.expense_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

        private int expenseID;
        private String expenseName;
        private LocalDate expenseDate;
        private BigDecimal expenseAmount;

        public Expense(String expenseName,  BigDecimal expenseAmount) {
                this.expenseName = expenseName;
                this.expenseAmount = expenseAmount;
        }

}
