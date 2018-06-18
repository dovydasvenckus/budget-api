Feature: Register client
  In order to use system
  As a client
  I want to register

Scenario: Client registers
  When I register with valid personal data
  Then I should have empty list of accounts

Scenario Outline: Client registers and creates account
  Given I register with valid personal data
  When I create "Savings" account of type "<accountType>"
  Then Account "Savings" should exist with "<accountType>" type

  Examples:
  | accountType |
  | ASSET       |
  | LIABILITY   |
  | INCOME      |
  | EXPENSE     |
  | EQUITY      |


Scenario: Register client should not be able to create account with non existing type
  Given I register with valid personal data
  When I create account with non existing type
  Then I should have empty list of accounts