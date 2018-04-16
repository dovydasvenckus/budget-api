package com.dovydasvenckus.budget.acceptance;

import com.dovydasvenckus.budget.account.AccountDTO;
import com.dovydasvenckus.budget.account.AccountType;
import com.dovydasvenckus.budget.client.ClientDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.jetty.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientSteps extends CucumberIntegrationTest {
    private String currentClient;

    @When("^I register with valid personal data$")
    public void iRegisterWithValidPersonalData() throws UnirestException {
        currentClient = RandomStringUtils.randomAlphabetic(10);
        Unirest.post(getApiAddress() + "/clients")
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(new ClientDTO(currentClient, "John", "Oliver"))
                .asJson();
    }

    @Then("^I should have empty list of accounts$")
    public void iShouldBeAbleToListMyAccounts() throws UnirestException {
        HttpResponse<AccountDTO[]> accountsResponse = Unirest.get(getApiAddress() + getAccountsResourceAddress())
                .asObject(AccountDTO[].class);

        assertThat(accountsResponse.getStatus()).isEqualTo(200);
        assertThat(accountsResponse.getBody().length).isEqualTo(0);
    }

    @When("^I create \"([^\"]*)\" account$")
    public void iCreateAccount(String accountName) throws UnirestException {
        AccountDTO accountDTO = new AccountDTO(accountName, AccountType.ASSET);

        HttpResponse response = Unirest.post(getApiAddress() + getAccountsResourceAddress())
                .header("Content-type", "application/json")
                .header("accept", "application/json")
                .body(accountDTO)
                .asJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED_201);
        assertThat(response.getHeaders().get("Location")).startsWith("/api/accounts/1");
    }

    @Then("^Account \"([^\"]*)\" should exist$")
    public void accountShouldExist(String accountName) throws UnirestException {
        HttpResponse<AccountDTO[]> accountsResponse = Unirest.get(getApiAddress() + getAccountsResourceAddress())
                .asObject(AccountDTO[].class);

        assertThat(accountsResponse.getStatus()).isEqualTo(200);
        assertThat(accountsResponse.getBody().length).isEqualTo(1);

        assertThat(accountsResponse.getBody()[0].getName()).isEqualTo(accountName);
        assertThat(accountsResponse.getBody()[0].getType()).isEqualTo(AccountType.ASSET);
        assertThat(accountsResponse.getBody()[0].getId()).isNotNull();
    }

    private String getAccountsResourceAddress() {
        return String.format("/clients/%s/accounts", currentClient);
    }

}
