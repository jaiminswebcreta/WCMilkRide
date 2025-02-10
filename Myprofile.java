package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

public class Myprofile {
	private Page page;

	@BeforeClass
	public void setUp() throws InterruptedException{
		page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin");
		// Change URL
		page.fill("//input[@id='mobileNumber']", "8849776064");
		Thread.sleep(2000);

		page.click("(//div[@class='mb-4'])[1]");
		Thread.sleep(6000);
		page.click("//button[@id='submitButton']");
		Thread.sleep(2000);
		Locator successToast = page.locator("//div[@class='toast-body']");
		Locator invalidOtpMessage = page.locator("(//p[@class='mt-1 help-block text-danger '])[1]");																						
																										

		// Conditional check for login success or failure
		if (successToast.isVisible()) {
			// Login successful
			String successMessage = successToast.textContent();
			System.out.println("Login successful: " + successMessage);
			assertTrue(true, "login successful message printed");
		} else if (invalidOtpMessage.isVisible()) {
			// Login failed - invalid OTP
			String errorMessage = invalidOtpMessage.textContent();
			System.err.println("invalid otp: " + errorMessage);
			assertTrue(false, "invalid otp");

		} else {
			// Login failed - unknown reason
			System.err.println("Login failed: Unknown Reason");
			assertTrue(false, "Login failed due to unknown reason");
		}
	}

	@Test(priority = 1)
	public void Myprofil ()  throws InterruptedException{
		
		
		page.click("//a[normalize-space()='Home']");

		page.waitForLoadState();
		
		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		
		page.click("//a[contains(text(),'My Account')]");
		
		page.waitForLoadState();
		Thread.sleep(2000);
		
		// Extract customer name dynamically
        String customerName = page.waitForSelector("//div[@class='profile-name']/h4").textContent();
        System.out.println("Customer Name: " + customerName);

        // Extract mobile number dynamically
        String mobileNumber = page.waitForSelector("//div[@class='col-lg-6 col-md-6 col-12 position-relative'][2]//input").inputValue();
        System.out.println("Mobile Number: " + mobileNumber);

        // Extract address dynamically
        String address = page.waitForSelector("//div[@class='col-md-12 position-relative'][1]/textarea").inputValue();
        System.out.println("Address: " + address);
        String Hubname = page.waitForSelector("//div[contains(@class, 'col-md-12') and contains(@class, 'position-relative')][2]//input").inputValue();
        System.out.println("Hub Name: " + Hubname);
      
        
        String Area = page.waitForSelector("//div[contains(@class, 'col-md-12') and contains(@class, 'position-relative')][3]//input").inputValue();
        System.out.println("Area: " + Area);
        
      
        String Walletbal = page.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3").textContent();
        System.out.println("Customer Name: " + Walletbal);
        Thread.sleep(1000);
		
        System.out.println("My profile Details");

	}
	
	
	@Test(priority = 2)
	public void Mywallet() throws InterruptedException{
		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		
		page.click("//a[contains(text(),'My Wallet')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("✅ Clicked on 'MyWallet' ");
		 // Click on Wallet
        

        // Click on "Pay Online" tab
        Locator payOnlineTab = page.locator("//button[@id='pay-online-tab']");
        payOnlineTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        payOnlineTab.click();
        System.out.println("Clicked on 'Pay Online' tab.");

        // Enter amount
        Locator amountInput = page.locator("//input[@id='amountInputOnline']");
        amountInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        amountInput.fill("500"); // ✅ Using `fill()` for better stability
      

        // Click the "Proceed to Pay" button
        Locator proceedButton = page.locator("//button[normalize-space()='Proceed to Pay']");
        proceedButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        proceedButton.click();
       
        page.waitForLoadState();
        
        Thread.sleep(3000);
     // Click on back button
        Locator backButton = page.locator("//a[normalize-space()='Back']");
        backButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        backButton.click();
     
        page.waitForLoadState();
        
        System.out.println("payOnlineTab completed");
        
        page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		
		page.click("//a[contains(text(),'My Wallet')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("✅ Clicked on 'Cash' tab.");
        // Click on Wallet
       
        // Click on "Request By Cash" tab
        Locator payCashTab = page.locator("//button[@id='pay-cash-tab']");
        payCashTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        payCashTab.click();
        

        // Enter amount
        Locator cashAmountInput = page.locator("(//input[@id='amountInputRecharge'])[1]");
        cashAmountInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        cashAmountInput.fill("800");
        

        // Click the submit button
        Locator submitButton = page.locator("//button[normalize-space()='Submit']");
        submitButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        submitButton.click();
        

        // Wait for success message
        Locator toastMessage = page.locator("(//div[@class='toast-body'])[1]");
        toastMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        String toastText = toastMessage.textContent();
        System.out.println("Cash add success message: " + toastText);

        // Click on Home button
        
        System.out.println("cashrequiest completed");
        
        String Walletbal = page.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3").textContent();
        System.out.println("Customer Name: " + Walletbal);
        Thread.sleep(1000);
        System.out.println("✅ my wallet competed");
        
       
        System.out.println("Clicked on 'Home' button.");
	}
	
	@Test(priority = 3)
	public void BillingHistory ()throws InterruptedException {
		
		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		
		page.click("//a[contains(text(),'Billing History')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("✅ Clicked on 'Billing History' ");
		
		String WalletBal = page.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3").textContent();
        System.out.println("WalletBal " + WalletBal);
        Thread.sleep(1000);
        
     
         
        
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);
        
        page.evaluate("window.scrollTo(0, 0)");
        Thread.sleep(2000);
        System.out.println("BillingHistory Completed");      
        
        }
	@Test(priority = 4)
	public void InvoicesHis()throws InterruptedException {

		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		page.click("//a[contains(text(),'Invoice History')]");
		page.waitForLoadState();
		System.out.println("INV History Completed");
		
		
		
		
	}
	
	@Test(priority = 5)
	public void logout () throws InterruptedException{
		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		page.click("//a[contains(text(),'Logout')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("Logout Completed");
		
		
		
	}
	
		
		
	

	@AfterClass
	public void tearDown() {
		BrowserSetup.closeBrowser();
	}
}
