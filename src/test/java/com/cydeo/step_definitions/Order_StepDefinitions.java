package com.cydeo.step_definitions;

import com.cydeo.pages.BasePage;
import com.cydeo.pages.OrderPage;
import com.cydeo.pages.ViewAllOrdersPage;
import com.cydeo.pages.WebTableLoginPage;
import com.cydeo.utilities.BrowserUtils;
import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

public class Order_StepDefinitions {

    WebTableLoginPage webTableLoginPage = new WebTableLoginPage();
    BasePage basePage = new BasePage();
    OrderPage orderPage = new OrderPage();

    ViewAllOrdersPage viewAllOrdersPage = new ViewAllOrdersPage();

    @Given("user is already logged in and on order page")
    public void user_is_already_logged_in_and_on_order_page() {
        //Navigating to page by using the URL from configuration.properties
        Driver.getDriver().get(ConfigurationReader.getProperty("web.table.url"));
        //calling our login method() to log in to web table app
        webTableLoginPage.login();
        //clicking to "order" link to go to order page
        basePage.orderBtn.click();
    }
    @When("user selects product type {string}")
    public void user_selects_product_type(String string) {

        Select select = new Select(orderPage.productDropdown);
        select.selectByVisibleText(string);

    }
    @When("user enters quantity {int}")
    public void user_enters_quantity(int int1) {
        //clear() method will delete whatever is in the input box
        orderPage.inputQuantity.clear();
        // The second alternative to delete the existing input is .sendKeys(Keys.BACK_SPACE);

         //orderPage.inputQuantity.sendKeys(Keys.BACK_SPACE);
        //accepting int argument and sending it using senKeys() method
        //since SendKeys() method only accepts String we need to either concat with ""
        //or send String.valueOf(int);
        //orderPage.inputQuantity.sendKeys(String.valueOf(2));
        orderPage.inputQuantity.sendKeys(2+"");

    }
    @When("user enters customer name {string}")
    public void user_enters_customer_name(String string) {
        orderPage.inputName.sendKeys(string);
    }
    @When("user enters street {string}")
    public void user_enters_street(String string) {
        orderPage.inputStreet.sendKeys(string);
    }
    @When("user enters city {string}")
    public void user_enters_city(String string) {
        orderPage.inputCity.sendKeys(string);
    }
    @When("user enters state {string}")
    public void user_enters_state(String string) {
        orderPage.inputState.sendKeys(string);
    }
    @When("user enters zipcode {string}")
    public void user_enters_zipcode(String string) {
        orderPage.inputZipCode.sendKeys(string);
    }
    @When("user selects credit card type {string}")
    public void user_selects_credit_card_type(String expectedCardType) {

        //This line will loop through the list and decide which radio button to click
        BrowserUtils.clickRadioButton(orderPage.creditCardType, expectedCardType);
    }
    @When("user enters credit card number {string}")
    public void user_enters_credit_card_number(String string) {
        orderPage.cardnoInput.sendKeys(string);
    }
    @When("user enters expiry date {string}")
    public void user_enters_expiry_date(String string) {
        orderPage.cardexpInput.sendKeys(string);
    }
    @When("user enters process order button")
    public void user_enters_process_order_button() {
        orderPage.processOrderButton.click();
    }
    @Then("user should see {string} in first row of the web table")
    public void user_should_see_in_first_row_of_the_web_table(String expectedName) {
        String actualName = viewAllOrdersPage.newCustomerCell.getText();
        Assert.assertEquals(expectedName, actualName);
    }
}
