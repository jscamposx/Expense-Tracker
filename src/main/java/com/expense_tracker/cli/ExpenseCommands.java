package com.expense_tracker.cli;

import com.expense_tracker.model.Expense;
import com.expense_tracker.service.ExpenseService;
import com.expense_tracker.exceptions.ExpenseNotFoundException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@ShellComponent
public class ExpenseCommands {

    private final ExpenseService expenseService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseCommands(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ShellMethod(key = "add", value = "📝 Añade un nuevo gasto. Uso: add --description \"Comida\" --amount 12.5")
    public String addExpense(@ShellOption(help = "📝 Descripción del gasto", value = "--description") String expenseName,
                             @ShellOption(help = "💰 Monto del gasto", value = "--amount") BigDecimal expenseAmount) {
        try {
            if (expenseAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return "\u001B[31m❌ El monto debe ser positivo.\u001B[0m";
            }

            Expense saved = expenseService.createExpense(expenseName, expenseAmount);
            return "\u001B[32m✅ Gasto agregado correctamente:\u001B[0m ID: " + saved.getExpenseID();
        } catch (Exception e) {
            return "\u001B[31m❌ Error al crear el gasto:\u001B[0m " + e.getMessage();
        }
    }

    @ShellMethod(key = "list", value = "📄 Lista todos los gastos registrados")
    public String listExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if (expenses.isEmpty()) {
            return "\u001B[33m⚠️ No hay gastos registrados.\u001B[0m";
        }

        StringBuilder output = new StringBuilder();
        output.append("\u001B[34m\n╔══════════════════════════════════════════════════╗\n");
        output.append("║              📄 LISTA DE GASTOS                  ║\n");
        output.append("╚══════════════════════════════════════════════════╝\u001B[0m\n");
        output.append(String.format("\n%-5s %-12s %-25s %-10s\n", "ID", "📅 Fecha", "📝 Descripción", "💰 Monto"));
        output.append("-------------------------------------------------------------\n");

        for (Expense e : expenses) {
            output.append(String.format("%-5d %-12s %-25s $%.2f\n",
                    e.getExpenseID(),
                    e.getExpenseDate().format(DATE_FORMATTER),
                    e.getExpenseName(),
                    e.getExpenseAmount().doubleValue()
            ));
        }
        return output.toString();
    }

    @ShellMethod(key = "summary", value = "📊 Muestra el total de gastos. Opcional: --month 4")
    public String summary(@ShellOption(help = "📆 Mes (1-12)", defaultValue = ShellOption.NULL, value = "--month") Integer month) {
        try {
            BigDecimal total;
            String label;
            if (month != null) {
                int currentYear = LocalDate.now().getYear();
                total = expenseService.getExpensesForMonth(month)
                        .stream()
                        .map(Expense::getExpenseAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                String nombreMes = new DateFormatSymbols(new Locale("es", "ES")).getMonths()[month - 1];
                label = "Total de gastos para " + capitalize(nombreMes) + " " + currentYear;
            } else {
                total = expenseService.getExpenseSummary();
                label = "Total de todos los gastos";
            }

            return "\u001B[36m📊 " + label + ":\u001B[0m $" + String.format("%.2f", total.doubleValue());
        } catch (Exception e) {
            return "\u001B[31m❌ Error al calcular el resumen:\u001B[0m " + e.getMessage();
        }
    }

    @ShellMethod(key = "delete", value = "🗑️ Elimina un gasto por su ID. Uso: delete --id 2")
    public String deleteExpense(@ShellOption(help = "🆔 ID del gasto a eliminar", value = "--id") int expenseId) {
        try {
            boolean deleted = expenseService.deleteExpense(expenseId);
            if (deleted) {
                return "\u001B[32m🗑️ Gasto eliminado correctamente.\u001B[0m";
            } else {
                throw new ExpenseNotFoundException("Gasto no encontrado con ID: " + expenseId);
            }
        } catch (ExpenseNotFoundException e) {
            return "\u001B[33m⚠️ " + e.getMessage() + "\u001B[0m";
        } catch (Exception e) {
            return "\u001B[31m❌ Error al eliminar el gasto:\u001B[0m " + e.getMessage();
        }
    }

    private String capitalize(String str) {
        return str == null || str.isEmpty() ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
