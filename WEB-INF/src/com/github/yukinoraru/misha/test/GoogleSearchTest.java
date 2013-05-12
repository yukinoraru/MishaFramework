package com.github.yukinoraru.misha.test;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GoogleSearchTest {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "https://www.google.co.jp/";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Test
    public void testSerchTitleIsEqualsQuery() throws Exception {
        selenium.open("/webhp?hl=en&tab=ww");
        selenium.type("id=gbqfq", "recruit");
        selenium.click("id=gbqfb");
        Thread.sleep(1500);
        assertEquals("recruit - Google Search", selenium.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}
