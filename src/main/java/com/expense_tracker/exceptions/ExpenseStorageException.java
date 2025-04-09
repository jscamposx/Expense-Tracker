package com.expense_tracker.exceptions;

public class ExpenseStorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExpenseStorageException(String message) {
        super(message);
    }

    public ExpenseStorageException(String message, Throwable cause) {
        super(message, cause);

    }

    public ExpenseStorageException(Throwable cause) {
        super(cause);
    }
}
