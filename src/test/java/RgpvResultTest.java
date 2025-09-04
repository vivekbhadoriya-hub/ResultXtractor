import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RgpvResultTest {

    WebDriver driver;
    RgpvResultPage rgpvResultPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://result.rgpv.ac.in/Result/");

        rgpvResultPage = new RgpvResultPage(driver);
    }

    @Test
    public void checkResult() throws Exception {
        // Hardcoded values
        String rollNo = "0903CA241063";
        String semester = "1";

        // Enter details and check result
        rgpvResultPage.checkResult(rollNo, semester);


    }

//    @AfterClass
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
