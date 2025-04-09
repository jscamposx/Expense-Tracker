package com.expense_tracker.cli;

import com.expense_tracker.service.ExpenseService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ExpenseCommands {

    private final ExpenseService expenseService;

    public ExpenseCommands(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ShellMethod(key = "add", value = "📝 Añade un nuevo gasto. Uso: add --description \"Comida\" --amount 12.5")
    public String addExpense(@ShellOption(help = "📝 Descripción del gasto", value = "--description") String expenseName,
                             @ShellOption(help = "💰 Monto del gasto", value = "--amount") float expenseAmount) {
        try {
            expenseService.createExpense(expenseName, expenseAmount);
            return "\u001B[32m✅ Gasto agregado correctamente:\u001B[0m " + "📝 " + expenseName + " | 💰 $" + expenseAmount;
        } catch (Exception e) {
            return "\u001B[31m❌ Error al crear el gasto:\u001B[0m " + e.getMessage();
        }
    }
}
