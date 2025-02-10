package tests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;


public class Logintest  {
	private Page page;

    @BeforeClass
    public void setUp() {
        page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin"); 
    }

    @Test
    public void testLogin() throws InterruptedException {
        page.fill("//input[@id='mobileNumber']", "8849776064");
  
        page.click("(//div[@class='mb-4'])[1]");
        Thread.sleep(8000);
        page.click("//button[@id='submitButton']");
        Thread.sleep(2000);
        Locator successToast = page.locator("//div[@class='toast-body']");
        Locator invalidOtpMessage = page.locator("(//p[@class='mt-1 help-block text-danger '])[1]"); // Example selector, adjust it as required

        // Conditional check for login success or failure
        if (successToast.isVisible()) {
            // Login successful
            String successMessage = successToast.textContent();
            System.out.println("Login successful: " + successMessage);
            assertTrue(true,"login successful message printed");
        } else if(invalidOtpMessage.isVisible()){
            // Login failed - invalid OTP
            String errorMessage = invalidOtpMessage.textContent();
             System.err.println("invalid otp: " + errorMessage);
             assertTrue(false, "invalid otp");

        } else {
           // Login failed - unknown reason
        	System.err.println("Login failed: Unknown Reason");
             assertTrue(false,"Login failed due to unknown reason");
        }
    }
     	




    @AfterClass
    public void tearDown() {
        BrowserSetup.closeBrowser();
    }
} 