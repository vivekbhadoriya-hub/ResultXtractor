package com.example.rgpv.tests;

import com.example.rgpv.pages.RgpvResultPage;
import com.example.rgpv.utils.ExcelReader;
import com.example.rgpv.utils.ExcelUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class RgpvResultTest {

    WebDriver driver;
    RgpvResultPage rgpvResultPage;
    ExcelReader excelReader = new ExcelReader();

    @DataProvider(name = "rolls")
    public Object[][] rolls() throws Exception {
        // Path to your Excel file with full roll numbers
        String excelPath = "C:\\Users\\vivek\\Testing\\RGPV AUTOMATION\\RgpvAutomation\\src\\test\\resources\\rolls.xlsx";
        List<String> rollList = excelReader.readRollNumbers(excelPath);

        Object[][] data = new Object[rollList.size()][1];
        for (int i = 0; i < rollList.size(); i++) {
            data[i][0] = rollList.get(i);
        }
        return data;
    }


    @Test(dataProvider = "rolls")
    public void fetchResult(String roll) throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://result.rgpv.ac.in/Result/");

        rgpvResultPage = new RgpvResultPage(driver);

            String semester = "1";
            rgpvResultPage.clickMca2Year();
            Thread.sleep(1000);
            rgpvResultPage.enterRollNumber(roll);
            rgpvResultPage.selectSemester(semester);
            rgpvResultPage.enterCaptcha();
            Thread.sleep(3000);
            rgpvResultPage.clickViewResult();
            rgpvResultPage.validateClassHeader();
            String cgpa = rgpvResultPage.getCGPA(roll);

        ExcelUtil.writeResult(roll, cgpa);

        driver.quit();

    }

}
