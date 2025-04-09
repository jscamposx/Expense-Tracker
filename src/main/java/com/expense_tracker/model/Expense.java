package com.expense_tracker.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
        private int expenseID;
        private String expenseName;

        private String expenseDate;
        private float expenseAmount;


        public Expense(String expenseName,  float expenseAmount) {
                this.expenseName = expenseName;

                this.expenseAmount = expenseAmount;
        }

}
