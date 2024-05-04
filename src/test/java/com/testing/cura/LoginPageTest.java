package com.testing.cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginPageTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\selenium\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        driver.manage().window().maximize();
    }

    @Test (priority = 0)
    public void testCheckElement() {
        // check h2 element (login)
        assertEquals(driver.findElement(By.cssSelector("section h2")).getText(), "Login");

        // check p element
        assertEquals(driver.findElement(By.cssSelector("section p.lead")).getText(), "Please login to make appointment.");

        // check text box element
        assertEquals(driver.findElement(By.id("txt-username")).getAttribute("placeholder"), "Username");
        assertEquals(driver.findElement(By.id("txt-password")).getAttribute("placeholder"), "Password");
    }

    @Test (priority = 1)
    public void testInputWithNullValues() {
        driver.findElement(By.id("btn-login")).click();
        assertEquals(driver.findElement(By.cssSelector("section p.text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 2)
    public void testInputWithIncorrectValues() {
        driver.findElement(By.id("txt-username")).sendKeys("wrong username");
        driver.findElement(By.id("txt-password")).sendKeys("wrong password");
        driver.findElement(By.id("btn-login")).click();
    }

    @Test (priority = 3)
    public void testInputWithCorrectValues() {
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.id("btn-login")).click();
       assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @AfterTest
    public void afterTestCloseBrowser() {
        driver.quit();
    }
}
