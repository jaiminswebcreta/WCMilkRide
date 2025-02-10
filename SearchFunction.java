package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchFunction {
	private Page page;

	@BeforeClass
	public void setUp() {
		  try {
	            page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin"); // Change URL
	            System.out.println("Browser launched and navigated to the login page successfully.");
	        } catch (Exception e) {
	            System.err.println("Error during browser setup: " + e.getMessage());
	           Assert.fail("Browser setup failed due to an exception." + e.getMessage());
	        }
	    }
	@Test
	public void productserah()throws InterruptedException {
		
		page.fill("//input[@id='mobileNumber']", "8849776064");

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
		
		page.click("//a[normalize-space()='Home']");
		
		System.out.println("Home page click");
		
		page.waitForLoadState();
		Thread.sleep(2000);
		
		page.click("(//i[@class='fa fa-search'])[1]");
		
		Thread.sleep(2002);
		page.fill("(//input[@id='searchInput'])[1]", "White Butter");
		Thread.sleep(3000);
		
		// Locate the product name, package size, and price elements
		String productNameLocator = "//p[@class='search-product-name mb-0 font-bold text-start']";
		String packageSizeLocator = "//div[@class='col-md-2 text-start']/p[@class='mb-0 small text-muted']";
		String productPriceLocator = "//div[@class='col-md-3 text-start']/p[@class='mb-0 text-muted']/span";

		// Extract the text from the elements
		String productName = page.locator(productNameLocator).innerText();
		String packageSize = page.locator(packageSizeLocator).innerText();
		String productPrice = page.locator(productPriceLocator).innerText();

		// Print the extracted information
		System.out.println("Product Name: " + productName);
		Thread.sleep(1000);
		System.out.println("Package Size: " + packageSize);
		Thread.sleep(1000);
		System.out.println("Product Price: " + productPrice);
		Thread.sleep(2000);
		
		page.click("(//p[@class='search-product-name mb-0 font-bold text-start'])[1]");
		page.waitForLoadState();
		

		// Click on 'Buy Once' button
		page.click("//button[@id='buy-once-tab']");
		Thread.sleep(2000);

		// Increase quantity
		page.click("//div[@class='tab-data-content']//i[@class='bi-plus']");
		Thread.sleep(2000);
		
//		
		
		

		// Click 'Add to Cart' button
		page.click("//button[contains(text(),'Add to')]");
		Thread.sleep(1000);

		// Verify add to cart success message
		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

		// Wait for cart icon to be visible
		Locator cartIcon = page.locator("//i[@class='bi-cart']");
		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

		// Click on cart icon
		cartIcon.click();
		Thread.sleep(2000);
		
		
		
		
		
		
		// Click on 'Place Order'
		page.click("//a[@id='placeOrderButton']");
		
		Thread.sleep(3000);
	

		// Wait for order success message
		page.waitForSelector("(//div[@class='toast-body'])[1]");
		Locator orderSuccessMessage = page.locator("(//div[@class='toast-body'])[1]");
		assertTrue(orderSuccessMessage.isVisible(), "Order success message is not displayed");
		System.out.println("Order Success Message: " + orderSuccessMessage.textContent());
		Thread.sleep(2000);
		
		// Capture the system date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        System.out.println("Order Placed at (System Time): " + formattedDateTime);

		
		

        // Navigate to My Orders
         page.click("//a[normalize-space()='My Orders']"); // Updated to navigate to my order page
        page.waitForLoadState();
	


System.out.println("✅ Morning order place complete");
}
		
		
	}



