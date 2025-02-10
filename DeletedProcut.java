package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;





import com.alibaba.fastjson.JSONObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;

import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeletedProcut {


	private Page page;

	@BeforeClass
	public void setUp() throws InterruptedException {
		page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin"); // Change URL
		page.fill("//input[@id='mobileNumber']", "8849776064");

		page.click("(//div[@class='mb-4'])[1]");
		Thread.sleep(6000);
		page.click("//button[@id='submitButton']");
//		Thread.sleep(2000);
//		Locator successToast = page.locator("//div[@class='toast-body']");
//		Locator invalidOtpMessage = page.locator("(//p[@class='mt-1 help-block text-danger '])[1]");
//		// Conditional check for login success or failure
//		if (successToast.isVisible()) {
//			// Login successful
//			String successMessage = successToast.textContent();
//			System.out.println("Login successful: " + successMessage);
//			assertTrue(true, "login successful message printed");
//		} else if (invalidOtpMessage.isVisible()) {
//			// Login failed - invalid OTP
//			String errorMessage = invalidOtpMessage.textContent();
//			System.err.println("invalid otp: " + errorMessage);
//			assertTrue(false, "invalid otp");
//
//		} else {
//			// Login failed - unknown reason
//			System.err.println("Login failed: Unknown Reason");
//			assertTrue(false, "Login failed due to unknown reason");
//		}

		System.out.println("✅ Login setup complete");
	}
	
	

//	@Test(priority = 1)
//	public void MorningOrder() throws InterruptedException {
//
//		Thread.sleep(2000);
//
//		// Scroll until the product is visible
//		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
//		newArrival.scrollIntoViewIfNeeded();
//		Thread.sleep(3000);
//
//		// Click on a product
//		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
//		Thread.sleep(3000);
//
//		// Click on 'Buy Once' button
//		page.click("//button[@id='buy-once-tab']");
//		Thread.sleep(2000);
//
//		// Increase quantity
//		page.click("//div[@class='tab-data-content']//i[@class='bi-plus']");
//		Thread.sleep(1000);
//
//		// Click 'Add to Cart' button
//		page.click("//button[contains(text(),'Add to')]");
//		Thread.sleep(4000);
//
//		// Verify add to cart success message
//		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
//		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
//		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());
//
//		// Wait for cart icon to be visible
//		Locator cartIcon = page.locator("//i[@class='bi-cart']");
//		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");
//
//		// Click on cart icon
//		cartIcon.click();
//		Thread.sleep(3000);
//
//		// Click on 'Place Order'
//		page.click("//a[@id='placeOrderButton']");
//		page.waitForLoadState(); // Default is 'load'
//
//		// Wait for order success message
//		page.waitForSelector("//div[@class='toast-body']");
//		Locator orderSuccessMessage = page.locator("//div[@class='toast-body']");
//		assertTrue(orderSuccessMessage.isVisible(), "Order success message is not displayed");
//		System.out.println("Order Success Message: " + orderSuccessMessage.textContent());
//		Thread.sleep(2000);
//
//		page.click("//a[normalize-space()='Home']");
//		page.waitForLoadState();
//
//		System.out.println("✅ Morning order place complete");
//	}
	@Test(priority = 2)
	public void cancelOrder() throws InterruptedException {
	    page.click("//a[normalize-space()='Home']");
	    Thread.sleep(3000);

	    // Navigate to "My Orders" page
	    page.click("//a[normalize-space()='My Orders']");  // Assuming "My Orders" link is present
	    page.waitForLoadState();
	    Thread.sleep(2000);
	    //Click on + 1 date button
	     page.click("//div[@class='swiper-slide' and not(contains(@class, 'swiper-slide-prev'))]//button[.//h6[text()='11']]");
	        Thread.sleep(3000);


	    Thread.sleep(3000);
	      
	    
	    // Handle the confirmation popup
//	    Locator yesCancelItButton = page.locator("//button[contains(@class, 'btn-primary') and contains(@class, 'rounded-pill')]");
////	    WaitForOptions options = new WaitForOptions().setState(com.microsoft.playwright.options.WaitForOptions.State.VISIBLE);
////	    yesCancelItButton.waitFor(options);
//	    yesCancelItButton.click();
//	    Thread.sleep(3000);

	    // Optionally, assert that a success message is displayed (e.g., a toast)
	   
	}


	
}
	
