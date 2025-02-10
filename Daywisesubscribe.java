package tests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

public class Daywisesubscribe {
	private Page page;

    @BeforeClass
    public void setUp() {
        page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin"); // Change URL
    }

    @Test
    public void Daywisesub() throws InterruptedException{
    	 page.fill("//input[@id='mobileNumber']", "8849776064");
    	  
         page.click("(//div[@class='mb-4'])[1]");
         Thread.sleep(6000);
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
    
     	
         // Scroll until the product is visible
         Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
         newArrival.scrollIntoViewIfNeeded();
         Thread.sleep(3000);

         // Click on a product
         page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
         Thread.sleep(3000);
         
         page.click("//label[normalize-space()='Evening']");//evening selected 
         Thread.sleep(2000);

         // Click on 'subscribe' button
         page.click("//button[@id='subscription-tab']");
         Thread.sleep(1000);
         
         
         
         page.click("//button[@id='weekly-tab']");
         Thread.sleep(2000);
         
         Locator subscribe = page.locator("(//button[contains(@type,'submit')][normalize-space()='Subscribe'])[4]");
         subscribe.scrollIntoViewIfNeeded();
         Thread.sleep(2000);
      // Increase quantity
         page.click("(//i)[31]");
         
         page.click("(//i)[33]");
         
         page.click("(//i)[35]");
         page.click("(//i)[37]");
         page.click("(//i)[39]");
         page.click("(//i)[41]");
         page.click("(//i)[43]");

         // Increase quantity
         
         Thread.sleep(1000);

         // Click 'subscribe' button
         page.click("(//button[contains(@type,'submit')][normalize-space()='Subscribe'])[4]");
         Thread.sleep(4000);

         // Verify add to cart success message
         Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
         assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
         System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

         // Wait for cart icon to be visible
         Locator cartIcon = page.locator("//i[@class='bi-cart']");
         assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

 
        page.click("//a[normalize-space()='Home']");
     }
     
    @AfterClass
    public void tearDown() {
		BrowserSetup.closeBrowser();
	}
}
