package com.dovydasvenckus.budget.account;

import static com.dovydasvenckus.budget.account.OperationType.CREDIT;
import static com.dovydasvenckus.budget.account.OperationType.DEBIT;

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
        if (increaseOperation == DEBIT) {
            return CREDIT;
        }

        return DEBIT;
    }
}
