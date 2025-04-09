package com.expense_tracker.repository;

import com.expense_tracker.exceptions.ExpenseStorageException;
import com.expense_tracker.model.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class FileExpenseRepository implements ExpenseRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path filePath = Paths.get("expenses.json");
    private final ReentrantLock lock = new ReentrantLock();
    private List<Expense> expensesList = new ArrayList<>();

    public FileExpenseRepository() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

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
            e.printStackTrace();
            throw new ExpenseStorageException("Error guardando gastos", e);

        }
    }

    @Override
    public Expense save(Expense expense) {
        lock.lock();
        try{
            int newExpenseId = generatedNextExpenseId();
            expense.setExpenseID(newExpenseId);
            expense.setExpenseDate(LocalDate.now());
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

    @Override
    public List<Expense> findAll() {
        lock.lock();
        try {
            return new ArrayList<>(expensesList);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Expense> findForMonth(int month) {
        lock.lock();
        try {
            return expensesList.stream()
                    .filter(expense -> {
                        LocalDate date = expense.getExpenseDate();
                        return date != null && date.getMonthValue() == month;
                    })
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }


    @Override
    public BigDecimal findSummary() {
        lock.lock();
        try {
            return expensesList.stream()
                    .map(Expense::getExpenseAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean delete(int expenseId) {
        lock.lock();
        try {
            boolean removed = expensesList.removeIf(expense -> expense.getExpenseID() == expenseId);
            if (removed) {
                saveExpensesListToFile();
            }
            return removed;
        } finally {
            lock.unlock();
        }
    }
}