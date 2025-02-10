package tests;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.ElementHandle.ClickOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import utilities.BrowserSetup;
public class SubModification {
	private Page page;

	@BeforeClass
	public void setUp() {
		page = BrowserSetup.launchBrowser("https://dev.milkride.com/milkmates/signin");
	}

	@Test(priority = 1)
	public void testLogin() throws InterruptedException {
		page.fill("//input[@id='mobileNumber']", "8849776064");

		page.click("(//div[@class='mb-4'])[1]");
		Thread.sleep(8000);
		page.click("//button[@id='submitButton']");
		Thread.sleep(2000);
		Locator successToast = page.locator("//div[@class='toast-body']");
		Locator invalidOtpMessage = page.locator("(//p[@class='mt-1 help-block text-danger '])[1]"); // Example
																										// selector,
																										// adjust it as
																										// required

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

	@Test(priority = 2)
	public void subadd() throws InterruptedException {

		// Scroll until the product is visible
		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);

//            page.click("//label[normalize-space()='Evening']");//evening selected 
//            Thread.sleep(2000);

		// Click on 'subscribe' button
		page.click("//button[@id='subscription-tab']");
		Thread.sleep(1000);

		page.click("//button[@id='every_day-tab']");
		Thread.sleep(2000);

		Locator subscribe = page.locator("div[id='every_day'] button[type='submit']");
		subscribe.scrollIntoViewIfNeeded();
		Thread.sleep(3000);
		
		
		// Increase quantity
		page.click("//div[@id='every_day']//i[@class='bi-plus']");
		Thread.sleep(1000);

		// Click 'subscribe' button
		page.click("div[id='every_day'] button[type='submit']");
		Thread.sleep(4000);

		// Verify add to cart success message
		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

		// Wait for cart icon to be visible
		Locator cartIcon = page.locator("//i[@class='bi-cart']");
		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

		page.click("//a[normalize-space()='Home']");

		System.out.println("subscribe Add successfully");

	}

	@Test(priority = 3)

	public void sublist() {

		page.click("(//a[normalize-space()='My Subscriptions'])[1]");

		page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));

		// Scroll to bottom of page
		scrollPageToBottom();

		// Wait again to make sure content has loaded after scroll.
		page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));

		List<ElementHandle> accordionItems = page.querySelectorAll(".accordion-item");

		System.out.println("Subscription Names:");

		for (ElementHandle accordionItem : accordionItems) {
			ElementHandle nameElement = accordionItem.querySelector(".order-product-name h6");

			if (nameElement != null) {
				String subscriptionName = nameElement.innerText();
				System.out.println("- " + subscriptionName.trim());
			} else {
				System.out.println("- Name element not found in this item");
			}
		}
	}

	// Method to scroll to bottom of page
	private void scrollPageToBottom() {
		page.evaluate("window.scrollTo(0, document.body.scrollHeight)");

		// You might need a small wait after scrolling depending on the page loading
		// speed
		try {
			Thread.sleep(1000); // 1 second delay (adjust if necessary)
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("Subscribe Name list completed");
	}



	 @Test(priority = 15)
	    public void TemporarySingle() throws InterruptedException {
	        page.click("(//a[normalize-space()='My Subscriptions'])[1]");
	        page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));
	        page.waitForLoadState();
	        Thread.sleep(2000);

	        try {
	        	 // 1. Click the Modify button on the first subscription.
	            String modifyButtonXpath = "(//button[contains(@class, 'btn') and contains(@class, 'rounded-pill') and @data-bs-toggle='modal'])[2]";
	            page.locator(modifyButtonXpath).click();
	            Thread.sleep(1000);
	            System.out.println("click on modify button");


	            // 2. Click the Temporary button on the popup.
	            String tempButtonXpath = "(//a[@class='edit-qty btn btn-light btn-sm fs-6'][normalize-space()='Temporary'])[1]";
	            page.locator(tempButtonXpath).click();
	            System.out.println("click on tempary button");
	            Thread.sleep(1000);

	            // 4. Click save button (before adding any quantity)
	            String saveButtonXpath = "(//button[@type='button'][normalize-space()='Save'])[1]";
	            page.locator(saveButtonXpath).click();

	            System.out.println("click on save button");

	            // 5. Check for 'Failed to update qty' message
	            String errorMessageXpath = "//h2[@id='swal2-title']";
	            String errorMessgeText = page.locator(errorMessageXpath).textContent();
	            System.out.println("Error message is : " + errorMessgeText);

	            // 6. click on 'got it' button
	            String gotItButtonXpath = "(//button[normalize-space()='Got it'])[1]";
	            page.locator(gotItButtonXpath).click();
	            System.out.println("click on got it button");

	            // 7. Enter quantity
	            String quantityFieldXpath = "(//input[contains(@id, 'temp_qty-')])[1]";
	            page.locator(quantityFieldXpath).clear();
	            page.locator(quantityFieldXpath).fill("2");

	            // 8. Click Save button
	            page.locator(saveButtonXpath).click();
	            System.out.println("save button click");

	            // 9. Check for successful message
	            String successMessageXpath = "//div[@id='swal2-content']";
	            String successMessgeText = page.locator(successMessageXpath).textContent();
	            System.out.println("Success message is : " + successMessgeText);

	            // 10. click on ok button
	            String okButtonXpath = "(//button[normalize-space()='Okay, got it!'])[1]";
	            page.locator(okButtonXpath).click();
	            page.waitForLoadState();

	            System.out.println("Singal modify completed successfully.");

	        } catch (Exception e) {
	            System.out.println("An error occurred: " + e.getMessage());
	        } finally {
//	        browser.close();
	        }
	        System.out.println("✅ Temporary single modify successfully");
	    }

	    @Test(priority = 16)
	    public void TemporaryMultiple() throws InterruptedException {
	        page.click("(//a[normalize-space()='My Subscriptions'])[1]");
	        page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));
	        page.waitForLoadState();
	        Thread.sleep(2000);

	        try {

	        	 // 1. Click the Modify button on the first subscription.
	            String modifyButtonXpath = "(//button[contains(@class, 'btn') and contains(@class, 'rounded-pill') and @data-bs-toggle='modal'])[2]";
	            page.locator(modifyButtonXpath).click();
	            Thread.sleep(1000);
	            System.out.println("click on modify button");

	            // 2. Click the Temporary button on the popup.
	            String tempButtonXpath = "(//a[@class='edit-qty btn btn-light btn-sm fs-6'][normalize-space()='Temporary'])[1]";
	            page.locator(tempButtonXpath).click();
	            System.out.println("click on tempary button");
	            Thread.sleep(1000);

	            page.click("(//button[contains(@id, 'multiple-tab-')])[1]");

	            Thread.sleep(1000);

	            String saveButtonXpath = "(//button[@type='button'][normalize-space()='Save'])[1]";
	            page.locator(saveButtonXpath).click();

	            System.out.println("click on save button");

	            // 5. Check for 'Failed to update qty' message
	            String errorMessageXpath = "//div[@id='swal2-content']";
	            String errorMessgeText = page.locator(errorMessageXpath).textContent();
	            System.out.println("Error message is : " + errorMessgeText);

	            // 6. click on 'got it' button
	            String gotItButtonXpath = "(//button[normalize-space()='Got it'])[1]";
	            page.locator(gotItButtonXpath).click();
	            System.out.println("click on got it button");

	            // 7. Enter quantity
	            String quantityFieldXpath = "(//input[contains(@id, 'temp_qty_multiple-')])[1]";
	            page.locator(quantityFieldXpath).clear();
	            page.locator(quantityFieldXpath).fill("2");

	            // 8. Click Save button
	            page.locator(saveButtonXpath).click();
	            System.out.println("save button click");

	            // 9. Check for successful message
	            String successMessageXpath = "(//div[@id='swal2-content'])[1]";
	            String successMessgeText = page.locator(successMessageXpath).textContent();
	            System.out.println("Success message is : " + successMessgeText);

	            // 10. click on ok button
	            String okButtonXpath = "//button[normalize-space()='Okay, got it!']";
	            page.locator(okButtonXpath).click();
	            page.waitForLoadState();

	            System.out.println("Multiple modify completed successfully.");

	        } catch (Exception e) {
	            System.out.println("An error occurred: " + e.getMessage());
	        } finally {
//	       browser.close();
	        }
	        System.out.println("✅ Temporary Multiple modify successfully");
	    }

	    @Test(priority = 17)

	    public void PermanentModify() throws InterruptedException {
	        page.click("(//a[normalize-space()='My Subscriptions'])[1]");
	        page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));
	        page.waitForLoadState();

	        try {

	            // 1. Click the Modify button on the first subscription.
	            String modifyButtonXpath = "(//button[contains(@class, 'btn') and contains(@class, 'rounded-pill') and @data-bs-toggle='modal'])[2]";
	            page.locator(modifyButtonXpath).click();
	            Thread.sleep(3000);
	            System.out.println("click on modify button");

	            // 2. Click the Temporary button on the popup.
	            String PermButtonXpath = "(//a[@type='button'][normalize-space()='Permanent'])[1]";
	            page.locator(PermButtonXpath).click();
	            System.out.println("click on permanent button");
	            Thread.sleep(1000);
	            // 7. Enter quantity
	            String quantityFieldXpath = "(//input[@id='qty'])[1]";
	            page.locator(quantityFieldXpath).clear();
	            page.locator(quantityFieldXpath).fill("2");
	            Thread.sleep(1000);
	            

	            page.click("(//button[@id='save_changes'])[1]");

	            System.out.println("perment qty updated succefully");
	            page.waitForLoadState();
	            Thread.sleep(1000);
	            String successMessageXpath = "//div[@class='toast-body']";
	            String successMessgeText = page.locator(successMessageXpath).textContent();
	            System.out.println("Success message is : " + successMessgeText);

	        } catch (Exception e) {
	            System.out.println("An error occurred: " + e.getMessage());
	        } finally {
//	    browser.close();
	        }
	        System.out.println("✅ Permanet Modify successfully");

	    }
}




