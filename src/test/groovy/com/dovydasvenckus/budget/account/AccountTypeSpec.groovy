package com.dovydasvenckus.budget.account

import spock.lang.Specification
import spock.lang.Unroll

import static com.dovydasvenckus.budget.account.AccountType.*
import static com.dovydasvenckus.budget.account.OperationType.CREDIT
import static com.dovydasvenckus.budget.account.OperationType.DEBIT

class AccountTypeSpec extends Specification {

    @Unroll
    def '#type should increase when #increaseOperation'() {
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
    def '#type should decrease when #decreaseOperation'() {
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