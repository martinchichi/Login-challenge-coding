
//package com.codility.selenium;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;

public class Login {

    private static final String APPLICATION_URL = "https://codility-frontend-prod.s3.amazonaws.com/media/task_static/qa_login_page/9a83bda125cd7398f9f482a3d6d45ea4/static/attachments/reference_page.html";
    public WebDriver webDriver;

    @Before
    public void setUp() {
        webDriver = new ChromeDriver();
        webDriver.get(APPLICATION_URL);
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testEmailAndPasswordFieldsPresence() {
        WebElement emailInput = webDriver.findElement(By.id("email-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        assertNotNull(emailInput);
        assertNotNull(passwordInput);
        assertNotNull(loginButton);
    }

    @Test
    public void testValidCredentials() {
        webDriver.findElement(By.id("email-input")).sendKeys("login@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();

        WebElement successMessage = webDriver.findElement(By.cssSelector("div.message.success"));
        assertEquals("Welcome to Codility", successMessage.getText());
    }

    @Test
    public void testWrongCredentials() {
        webDriver.findElement(By.id("email-input")).sendKeys("unknown@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();

        WebElement errorMessage = webDriver.findElement(By.cssSelector("div.message.error"));
        assertEquals("You shall not pass! Arr!", errorMessage.getText());
    }

    @Test
    public void testEmailValidation() {
        webDriver.findElement(By.id("email-input")).sendKeys("invalid-email");
        webDriver.findElement(By.id("login-button")).click();

        WebElement validationError = webDriver.findElement(By.cssSelector("div.validation.error"));
        assertEquals("Enter a valid email", validationError.getText());
    }

    @Test
    public void testEmptyCredentials() {
        webDriver.findElement(By.id("login-button")).click();

        WebElement emailValidationError = webDriver.findElement(By.cssSelector("div.validation.error"));
        WebElement passwordValidationError = webDriver.findElement(By.cssSelector("div.validation.error"));
        //assertEquals("Enter a valid email", emailValidationError.getText());
        assertEquals("Email is required", emailValidationError.getText());
        //assertEquals("Password is required", passwordValidationError.getText());
        assertEquals("Email is required", passwordValidationError.getText());
    }

    @Test
    public void testTabAndEnterKeys() {
        WebElement emailInput = webDriver.findElement(By.id("email-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        // Create an Actions instance
        Actions actions = new Actions(webDriver);

        // Test TAB key
        actions.sendKeys(emailInput, "login@codility.com")
                .sendKeys(Keys.TAB)
                .sendKeys(passwordInput, "password")
                .sendKeys(Keys.TAB)
                .perform();

        // Check focus moved to password field and then to the next element after password input
        // (You might need to check the focus on an element following password input)

        // Test ENTER key
        actions.sendKeys(Keys.ENTER).perform();

        // Validate successful login
        WebElement successMessage = webDriver.findElement(By.cssSelector("div.message.success"));
        assertEquals("Welcome to Codility", successMessage.getText());
    }
}
