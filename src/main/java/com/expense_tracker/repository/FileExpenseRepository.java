package com.expense_tracker.repository;

import com.expense_tracker.exceptions.ExpenseStorageException;
import com.expense_tracker.model.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class FileExpenseRepository implements ExpenseRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path filePath = Paths.get("expenses.json");
    private final ReentrantLock lock = new ReentrantLock();
    private List<Expense> expensesList = new ArrayList<>();

    @PostConstruct
    public void init() {
        loadExpensesFromFile();
    }

    private void loadExpensesFromFile() {
        lock.lock();
        try {
            ensureFileExists();
            byte[] jsonData = Files.readAllBytes(filePath);
            expensesList = jsonData.length > 0
                    ? objectMapper.readValue(jsonData, new TypeReference<List<Expense>>() {})
                    : new ArrayList<>();

        } catch (IOException e) {
            throw new ExpenseStorageException("Error cargando gastos", e);
        } finally {
            lock.unlock();
        }
    }

    private void ensureFileExists() throws IOException {
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
            expensesList = new ArrayList<>();
            saveExpensesListToFile();
        }
    }

    private void saveExpensesListToFile() {
        try {
            String json = objectMapper.writeValueAsString(expensesList);
            Files.write(filePath,
                    json.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new ExpenseStorageException("Error guardando gastos", e);
        }
    }

    @Override
    public Expense save(Expense expense) {
        lock.lock();
        try{
            int newExpenseId = generatedNextExpenseId();
            expense.setExpenseID(newExpenseId);
            expense.setExpenseDate(LocalDate.now().toString());
            expensesList.add(expense);
            saveExpensesListToFile();
            return expense;
        } finally {
            lock.unlock();
        }
    }

    private int generatedNextExpenseId() {
        return expensesList.stream()
                .mapToInt(Expense::getExpenseID)
                .max()
                .orElse(0) + 1;
    }
}