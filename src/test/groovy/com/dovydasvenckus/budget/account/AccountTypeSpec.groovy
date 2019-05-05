package com.dovydasvenckus.budget.account

import spock.lang.Specification
import spock.lang.Unroll

import static com.dovydasvenckus.budget.account.AccountType.*
import static com.dovydasvenckus.budget.operation.OperationType.CREDIT
import static com.dovydasvenckus.budget.operation.OperationType.DEBIT

class AccountTypeSpec extends Specification {

    @Unroll
    def '#type should increase after #increaseOperation operation'() {
        expect:
            type.getIncreaseOperation() == increaseOperation

        where:
            type      || increaseOperation
            ASSET     || DEBIT
            LIABILITY || CREDIT
            INCOME    || CREDIT
            EXPENSE   || DEBIT
            EQUITY    || CREDIT
    }

    @Unroll
    def '#type should decrease after #decreaseOperation operation'() {
        expect:
            type.getDecreaseOperation() == decreaseOperation

        where:
            type      || decreaseOperation
            ASSET     || CREDIT
            LIABILITY || DEBIT
            INCOME    || DEBIT
            EXPENSE   || CREDIT
            EQUITY    || DEBIT
    }
}