package accountManagementService;

/* author: Joao Silva s222961 */

import dtu.ws.fastmoney.*;
import handlers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import Entities.*;

import java.math.BigDecimal;

/* Scenario: Customer registers and unregisters successfully
    Given a customer with name "Joao" "Afonso" and bank account with balance 1000
    When the customer registers with DTU Pay
    Then the customer is saved in the customer list
    And the customer can be retrieved from the customer list
    And the customer unregisters from DTU Pay
    And the customer is removed from the customer list */

public class ManageAccountUnitSteps {

    private User user = new User();
    private String bankId;
    private BankService bank = new BankServiceService().getBankServicePort();
    private DTUPayUser customer;
    private AccountService customerService = new AccountService();

    @Given("a customer with name {string} {string} and bank account with balance {int}")
    public void a_customer_with_name_and_bank_id(String firstName, String lastName, int balance) {

        user.setCprNumber("28719-1234");
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // convert balance to bigDecimal
        BigDecimal bigDecimalBalance = new BigDecimal(balance);

        try {
            bankId = bank.createAccountWithBalance(user, bigDecimalBalance);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }

        // make full name from first and last name
        String name = firstName + " " + lastName;
        customer = new DTUPayUser(name, bankId, "customer");
    }

    @When("the customer registers with DTU Pay")
    public void the_customer_registers_with_dtu_pay() {
        customerService.registerAccount(customer);
    }

    @Then("the customer is saved in the customer list")
    public void the_customer_is_saved_in_the_system() {
        assertTrue(customerService.getAccountList("customer").contains(customer));
    }

    @And("the customer can be retrieved from the customer list")
    public void the_customer_can_get_correctly_retrieved_from_the_list() {
        assertEquals(customer, customerService.getAccount(customer.getAccountID()));
    }

    @And("the customer unregisters from DTU Pay")
    public void the_customer_unregisters_from_dtu_pay() {
        customerService.unregisterAccount(customer);
    }

    @And("the customer is removed from the customer list")
    public void the_customer_is_removed_from_the_customer_list() {
        assertTrue(!customerService.getAccountList("customer").contains(customer));
    }

    /*
    @After
    public void tearDown() {

        try {
            bank.retireAccount(bankId);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    } */

}