package com.example.rgpv.pages;

import com.example.rgpv.utils.ExcelUtil;
import com.example.rgpv.utils.OCRUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RgpvResultPage {

    WebDriver driver;
    WebDriverWait wait;
    // Constructor
    public RgpvResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By rollNoField = By.id("ctl00_ContentPlaceHolder1_txtrollno");
    private By mca2Year = By.id("radlstProgram_17");
    private By semesterDropdown = By.id("ctl00_ContentPlaceHolder1_drpSemester");
    private By captchaImage = By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_pnlCaptcha\"]/table/tbody/tr[1]/td/div/img");
    private By captchaField = By.id("ctl00_ContentPlaceHolder1_TextBox1");
    private By viewResultBtn = By.id("ctl00_ContentPlaceHolder1_btnviewresult");
    private By classHeader = By.className("resultheader");
    private By cgpaField = By.id("ctl00_ContentPlaceHolder1_lblcgpa");




    public void clickMca2Year() {
        wait.until(ExpectedConditions.elementToBeClickable(mca2Year)).click();
    }

    public void enterRollNumber(String rollNo) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(rollNoField)).sendKeys(rollNo);
    }

    public void selectSemester(String semester) {
        WebElement semesterElement = wait.until(ExpectedConditions.elementToBeClickable(semesterDropdown));
        Select selectSemester = new Select(semesterElement);
        selectSemester.selectByVisibleText(semester);
    }

    public void enterCaptcha() {
        WebElement captchaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaImage));
        String captcha = OCRUtil.readCaptcha(driver, captchaElement);
        WebElement captchaInput = wait.until(ExpectedConditions.elementToBeClickable(captchaField));
        captchaInput.clear();
        captchaInput.sendKeys(captcha);
    }


    public void clickViewResult() {
        wait.until(ExpectedConditions.elementToBeClickable(viewResultBtn)).click();
    }

    public void validateClassHeader() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(classHeader));
    }

    public String getCGPA(String rollNo) {
        WebElement cgpaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cgpaField));
        String cgpa = cgpaElement.getText().trim();
        System.out.println("Roll: " + rollNo + " → CGPA: " + cgpa);
        return cgpa;
    }

}
