package com.testing.cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HomePageTest {
    WebDriver driver;
    @BeforeTest
    public void setUp() {
        // Initiate Browser
        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\selenium\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();

        // Open Browser
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();
    }

    @Test (priority = 0)
    public void testCheckElement() {
        // check h1 element
        String header = driver.findElement(By.cssSelector("header h1")).getText();
        String color = Color.fromString(driver.findElement(By.cssSelector("header h1")).getCssValue("color")).asHex();
        assertEquals(header, "CURA Healthcare Service");
        assertEquals(color, "#ffffff");

        // check h3 element
        String header3 = driver.findElement(By.cssSelector("header h3")).getText();
        String color3 = Color.fromString(driver.findElement(By.cssSelector("header h3")).getCssValue("color")).asHex();
        assertEquals(header3, "We Care About Your Health");
        assertEquals(color3, "#4fb6e7");

        // check button element
        String button = driver.findElement(By.id("btn-make-appointment")).getText();
        String backgroundOfButton = driver.findElement(By.id("btn-make-appointment")).getCssValue("background-color");
        assertEquals(button, "Make Appointment");
        assertEquals(backgroundOfButton, "rgba(115, 112, 181, 0.8)");

        // check toggle menu
        String menuToggle = driver.findElement(By.id("menu-toggle")).getCssValue("background-color");
        assertEquals(menuToggle, "rgba(115, 112, 181, 0.8)");
    }

    @Test(priority = 1)
    public void testToggleMenu() {
        driver.findElement(By.id("menu-toggle")).click();
        assertEquals(driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[1]")).getText(), "CURA Healthcare");
        assertEquals(driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[2]")).getText(), "Home");
        assertEquals(driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]")).getText(), "Login");
        driver.findElement(By.id("menu-toggle")).click();
    }

    @Test(priority = 2)
    public void testClickButtonMakeAppointment() {
        driver.findElement(By.id("btn-make-appointment")).click();
        assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/profile.php#login");
    }

    @AfterTest
    public void afterTestCloseBrowser() {
        driver.quit();
    }
}
