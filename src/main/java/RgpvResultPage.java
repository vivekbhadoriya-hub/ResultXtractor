import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
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
    By rollNoField = By.id("ctl00_ContentPlaceHolder1_txtrollno");
    By mca2Year = By.id("radlstProgram_17");
    By semesterDropdown = By.id("ctl00_ContentPlaceHolder1_drpSemester");
    By captchaImage = By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_pnlCaptcha\"]/table/tbody/tr[1]/td/div/img");
    By captchaField = By.id("ctl00_ContentPlaceHolder1_TextBox1");
    By viewResultBtn = By.id("ctl00_ContentPlaceHolder1_btnviewresult");

    // Method to read CAPTCHA using OCR
    public String readCaptcha() throws Exception {
        // Capture screenshot of CAPTCHA
        WebElement captchaElement = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaImage));

        Thread.sleep(1500);
        File src = captchaElement.getScreenshotAs(OutputType.FILE);
        File dest = new File("captcha.png");
        org.openqa.selenium.io.FileHandler.copy(src, dest);

        // Use Tesseract OCR
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\vivek\\Testing\\RGPV AUTOMATION\\RgpvAutomation\\tesseract-main\\tessdata"); // <-- escape backslashes in path
        String captchaText = tesseract.doOCR(dest);

        // Clean text: remove spaces and non-alphanumeric chars
        captchaText = captchaText.replaceAll("\\s+", "");
        captchaText = captchaText.replaceAll("[^a-zA-Z0-9]", "");
        captchaText = captchaText.toUpperCase();

        System.out.println("Extracted CAPTCHA: " + captchaText);
        return captchaText.trim();
    }

    // Method to check result
    public void checkResult(String rollNo, String semester) throws Exception {

        // Click MCA2Year program
        wait.until(ExpectedConditions.elementToBeClickable(mca2Year)).click();

        // Enter RollNo.
        wait.until(ExpectedConditions.visibilityOfElementLocated(rollNoField)).sendKeys(rollNo);

        // Select semester
        WebElement semesterElement = wait.until(ExpectedConditions.elementToBeClickable(semesterDropdown));
        Select selectSemester = new Select(semesterElement);
        selectSemester.selectByVisibleText(semester);

        // Read CAPTCHA and enter
        String captcha = readCaptcha();
        WebElement captchaInput = wait.until(ExpectedConditions.elementToBeClickable(captchaField));
        captchaInput.clear();
        captchaInput.sendKeys(captcha);

        Thread.sleep(3000);
        // Click on view result
        wait.until(ExpectedConditions.elementToBeClickable(viewResultBtn)).click();
    }
}
