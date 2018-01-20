package com.dovydasvenckus.budget.account;

import com.dovydasvenckus.budget.operation.OperationType;

import static com.dovydasvenckus.budget.operation.OperationType.CREDIT;
import static com.dovydasvenckus.budget.operation.OperationType.DEBIT;

public enum AccountType {
    ASSET(DEBIT), LIABILITY(CREDIT), INCOME(CREDIT), EXPENSE(DEBIT), EQUITY(CREDIT);

    private final OperationType increaseOperation;

    AccountType(OperationType operationType) {
        this.increaseOperation = operationType;
    }

    public OperationType getIncreaseOperation() {
        return increaseOperation;
    }

    public OperationType getDecreaseOperation() {
        return (increaseOperation == DEBIT) ? CREDIT : DEBIT;
    }
}
