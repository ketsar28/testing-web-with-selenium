package com.testing.cura;

    import org.openqa.selenium.By;
    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.WebElement;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.openqa.selenium.support.ui.Select;
    import org.testng.annotations.AfterTest;
    import org.testng.annotations.BeforeMethod;
    import org.testng.annotations.BeforeTest;
    import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class AppointmentPageTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        // Initiate Browser
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\selenium\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();

        // Open Browser
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();

        // Login Process
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.id("btn-login")).click();
        assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/#appointment");

    }

    @Test (priority = 0)
    public void testCheckElement() {
        // check h2 element (make appointment)
        assertEquals(driver.findElement(By.cssSelector("section#appointment h2")).getText(), "Make Appointment");

        // check dropdown element
        Select dropdown = new Select(driver.findElement(By.id("combo_facility")));
        List<WebElement> dropdownOptions = dropdown.getOptions();
        assertEquals(dropdownOptions.size(), 3);
        assertEquals(dropdownOptions.get(0).getAttribute("value"), "Tokyo CURA Healthcare Center");
        assertEquals(dropdownOptions.get(1).getAttribute("value"), "Hongkong CURA Healthcare Center");
        assertEquals(dropdownOptions.get(2).getAttribute("value"), "Seoul CURA Healthcare Center");

        // check text area
        assertEquals(driver.findElement(By.id("txt_comment")).getAttribute("placeholder"), "Comment");
    }

    @Test (priority = 1)
    public void testMakeAppointment() {
        // check dropdown
        Select dropdown = new Select(driver.findElement(By.id("combo_facility")));
        dropdown.selectByValue("Seoul CURA Healthcare Center");

        // check box
        driver.findElement(By.id("chk_hospotal_readmission")).click();

        // check radio button
        driver.findElement(By.id("radio_program_medicaid")).click();

        // check date
        driver.findElement(By.id("txt_visit_date")).sendKeys("28/12/2023");

        // check comment
        driver.findElement(By.id("txt_comment")).sendKeys("I wanna become a manager");

        // check submit button
        driver.findElement(By.id("btn-book-appointment")).click();
        assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/appointment.php#summary");
    }

    @Test (priority = 2, dependsOnMethods = {"testMakeAppointment"})
    public void testConfirmAppointment() {
        // check confirm appointment text
        assertEquals(driver.findElement(By.cssSelector("section#summary h2")).getText(), "Appointment Confirmation");

        // check description appointment text
        assertEquals(driver.findElement(By.cssSelector("section#summary .lead")).getText(), "Please be informed that your appointment has been booked as following:");

        // check facility value
        assertEquals(driver.findElement(By.id("facility")).getText(), "Seoul CURA Healthcare Center");

        // check apply for hospital readmission
        assertEquals(driver.findElement(By.id("hospital_readmission")).getText(), "Yes");

        // check healthcare program
        assertEquals(driver.findElement(By.id("program")).getText(), "Medicaid");

        // check visit date
        assertEquals(driver.findElement(By.id("visit_date")).getText(), "28/12/2023");

        // check comment text
        assertEquals(driver.findElement(By.id("comment")).getText(), "I wanna become a manager");

        // go to homepage
        driver.findElement(By.className("btn-default")).click();
        assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/");
    }

    @AfterTest
    public void afterTestCloseBrowser() {
        driver.quit();
    }
}
