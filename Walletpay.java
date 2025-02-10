package tests; // ✅ Corrected package declaration

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

public class Walletpay { // ✅ Class name follows Java naming conventions
	private Page page;

    @BeforeClass
    public void setUp()throws InterruptedException {
        page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin"); 
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
        }// Change URL
    }

    @Test
    public void payOnline() {
    	
        try {
            // Click on Wallet
            Locator walletButton = page.locator("//span[@class='wallet-amount fw-semibold']");
            walletButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            walletButton.click();
            System.out.println("✅ Clicked on Wallet.");

            // Click on "Pay Online" tab
            Locator payOnlineTab = page.locator("//button[@id='pay-online-tab']");
            payOnlineTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            payOnlineTab.click();
            System.out.println("✅ Clicked on 'Pay Online' tab.");

            // Enter amount
            Locator amountInput = page.locator("//input[@id='amountInputOnline']");
            amountInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            amountInput.fill("500"); // ✅ Using `fill()` for better stability
            System.out.println("✅ Entered amount: 500.");

            // Click the "Proceed to Pay" button
            Locator proceedButton = page.locator("//button[normalize-space()='Proceed to Pay']");
            proceedButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            proceedButton.click();
            System.out.println("✅ Clicked on 'Proceed to Pay' button.");

        } catch (Exception e) {
            System.err.println("❌ An error occurred in payOnline(): " + e.getMessage());
        }

        // Click on back button
        Locator backButton = page.locator("//a[normalize-space()='Back']");
        backButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        backButton.click();
        System.out.println("✅ Clicked on 'Back' button.");
    }

    @Test
    public void payCash()throws InterruptedException {
        try {
            // Click on Wallet
            Locator walletButton = page.locator("//span[@class='wallet-amount fw-semibold']");
            walletButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            walletButton.click();
            System.out.println("✅ Clicked on Wallet.");

            // Click on "Request By Cash" tab
            Locator payCashTab = page.locator("//button[@id='pay-cash-tab']");
            payCashTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            payCashTab.click();
            System.out.println("✅ Clicked on 'Request By Cash' tab.");

            // Enter amount
            Locator cashAmountInput = page.locator("(//input[@id='amountInputRecharge'])[1]");
            cashAmountInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            cashAmountInput.fill("800");
            System.out.println("✅ Entered cash amount: 800.");

            // Click the submit button
            Locator submitButton = page.locator("//button[normalize-space()='Submit']");
            submitButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            submitButton.click();
            System.out.println("✅ Clicked on 'Submit' button.");

            // Wait for success message
            Locator toastMessage = page.locator("(//div[@class='toast-body'])[1]");
            toastMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            String toastText = toastMessage.textContent();
            System.out.println("✅ Cash add success message: " + toastText);

            // Click on Home button
            Locator homeButton = page.locator("//a[normalize-space()='Home']");
            homeButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            homeButton.click();
            System.out.println("✅ Clicked on 'Home' button.");

        } catch (Exception e) {
            System.err.println("❌ An error occurred in payCash(): " + e.getMessage());
        }
        String Walletbal = page.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3").textContent();
        System.out.println("Customer Name: " + Walletbal);
        Thread.sleep(1000);
		
        System.out.println("wallet Details compalete");
        
      
       
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            BrowserSetup.closeBrowser();
            System.out.println("✅ Browser closed successfully.");
        }
    }
}
