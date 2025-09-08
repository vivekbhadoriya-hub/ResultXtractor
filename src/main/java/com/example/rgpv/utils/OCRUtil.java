package com.example.rgpv.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

public class OCRUtil {

    // Method to read CAPTCHA using OCR
    public static String readCaptcha(WebDriver driver, WebElement captchaElement) {
        String captchaText = "";
        int attempts = 0;

        while (attempts < 5) {
            try {
                Thread.sleep(1000);
                // Capture screenshot of CAPTCHA
                File src = captchaElement.getScreenshotAs(OutputType.FILE);
                File dest = new File("captcha.png");
                FileHandler.copy(src, dest);

                // Use Tesseract OCR
                ITesseract tesseract = new Tesseract();
                tesseract.setDatapath("C:\\Users\\vivek\\Testing\\ResultXtractor\\ResultXtractor\\tesseract-main\\tessdata");
                tesseract.setLanguage("eng");
                captchaText = tesseract.doOCR(dest);

                // Clean text: remove spaces & unwanted chars
                captchaText = captchaText.replaceAll("\\s+", "");
                captchaText = captchaText.replaceAll("[^a-zA-Z0-9]", "");
                captchaText = captchaText.toUpperCase();

                // Validate captcha length (usually 4-5 chars)
                if (captchaText.length() >= 5 && captchaText.length() <= 5) {
                    System.out.println("Extracted CAPTCHA: " + captchaText);
                    return captchaText.trim();
                } else {
                    System.out.println("Invalid CAPTCHA (" + captchaText + "), retrying...");
                }

            } catch (Exception e) {
                System.out.println("OCR failed: " + e.getMessage());
            }
            attempts++;
        }

        return captchaText;  // return last attempt, even if invalid
    }
}
