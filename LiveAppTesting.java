package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class LiveAppTesting {
	private Page page;

	@BeforeClass
	public void setUp() throws InterruptedException {
		page = BrowserSetup.launchBrowser("https://ruralbloom.milkride.com/web/signin"); // Change URL
		page.fill("(//input[@id='mobileNumber'])[1]", "8849776064");

		page.click("(//div[@class='mb-4'])[2]");
		Thread.sleep(10000);
		page.click("(//button[normalize-space()='Submit'])[1]");
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

		System.out.println("✅ Login setup complete");
	}

	@Test(priority = 1)
	public void MorningOrder() throws InterruptedException {

		Thread.sleep(2000);

		// Scroll until the product is visible
		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);

		// Click on 'Buy Once' button
		page.click("//button[@id='buy-once-tab']");
		Thread.sleep(2000);

		// Increase quantity
		page.click("//div[@class='tab-data-content']//i[@class='bi-plus']");
		Thread.sleep(1000);

		// Click 'Add to Cart' button
		page.click("//button[contains(text(),'Add to')]");
		Thread.sleep(4000);

		// Verify add to cart success message
		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

		// Wait for cart icon to be visible
		Locator cartIcon = page.locator("//i[@class='bi-cart']");
		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

		// Click on cart icon
		cartIcon.click();
		Thread.sleep(3000);

		// Click on 'Place Order'
		page.click("//a[@id='placeOrderButton']");
		page.waitForLoadState(); // Default is 'load'

		// Wait for order success message
		page.waitForSelector("//div[@class='toast-body']");
		Locator orderSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(orderSuccessMessage.isVisible(), "Order success message is not displayed");
		System.out.println("Order Success Message: " + orderSuccessMessage.textContent());

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		System.out.println("✅ Morning order place complete");
	}
	@Test(priority = 2)
	public void EveningOrder() throws InterruptedException {
		page.click("//a[normalize-space()='Home']");

		page.waitForLoadState();

		// Scroll until the product is visible
		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);
		page.click("//label[normalize-space()='Evening']");// evening selected
		Thread.sleep(2000);

		// Click on 'Buy Once' button
		page.click("//button[@id='buy-once-tab']");
		Thread.sleep(2000);

		// Increase quantity
		page.click("//div[@class='tab-data-content']//i[@class='bi-plus']");
		Thread.sleep(1000);

		// Click 'Add to Cart' button
		page.click("//button[contains(text(),'Add to')]");
		Thread.sleep(4000);

		// Verify add to cart success message
		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

		// Wait for cart icon to be visible
		Locator cartIcon = page.locator("//i[@class='bi-cart']");
		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

		// Click on cart icon
		cartIcon.click();
		Thread.sleep(3000);

		// Click on 'Place Order'
		page.click("//a[@id='placeOrderButton']");
		page.waitForLoadState(); // Default is 'load'

		// Wait for order success message
		page.waitForSelector("//div[@class='toast-body']");
		Locator orderSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(orderSuccessMessage.isVisible(), "Order success message is not displayed");
		System.out.println("Order Success Message: " + orderSuccessMessage.textContent());
		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		System.out.println("✅ Evening order place complete");
	}

	@Test(priority = 3)
	public void EveryDaySubscribe() throws InterruptedException {

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		// Scroll until the product is visible
		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);

		page.click("//label[normalize-space()='Evening']");// evening selected
		Thread.sleep(2000);

		// Click on 'subscribe' button
		page.click("//button[@id='subscription-tab']");
		Thread.sleep(1000);

		page.click("//button[@id='every_day-tab']");
		Thread.sleep(2000);

		Locator subscribe = page.locator("div[id='every_day'] button[type='submit']");
		subscribe.scrollIntoViewIfNeeded();
		Thread.sleep(2000);

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
		page.waitForLoadState();
		System.out.println("✅ Everyday Subscribe place Complete");
	}

	@Test(priority = 4)
	public void Every2DaySubcribe() throws InterruptedException {

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);

		// Click on 'subscribe' button
		page.click("//button[@id='subscription-tab']");
		Thread.sleep(1000);

		page.click("//button[@id='alternate-day-tab']");
		Thread.sleep(2000);

		Locator subscribe = page
				.locator("//div[@id='alternate-day']//button[@type='submit'][normalize-space()='Subscribe']");
		subscribe.scrollIntoViewIfNeeded();
		Thread.sleep(2000);

		// Increase quantity
		page.click("//div[@id='alternate-day']//i[@class='bi-plus']");
		Thread.sleep(1000);

		// Click 'subscribe' button
		page.click("//div[@id='alternate-day']//button[@type='submit'][normalize-space()='Subscribe']");
		Thread.sleep(4000);

		// Verify add to cart success message
		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

		// Wait for cart icon to be visible
		Locator cartIcon = page.locator("//i[@class='bi-cart']");
		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();
		System.out.println("✅ Every2day subscribe place complete");
	}

	@Test(priority = 5)
	public void Every3DaySubscribe() throws InterruptedException {

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		// Scroll until the product is visible
		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);

		page.click("//label[normalize-space()='Evening']");// evening selected
		Thread.sleep(2000);

		// Click on 'subscribe' button
		page.click("//button[@id='subscription-tab']");
		Thread.sleep(1000);

		page.click("//button[@id='every-three-day-tab']");
		Thread.sleep(2000);

		Locator subscribe = page.locator("(//button[@type='submit'][normalize-space()='Subscribe'])[3]");
		subscribe.scrollIntoViewIfNeeded();
		Thread.sleep(2000);

		// Increase quantity
		page.click("//div[@id='every-three-day']//i[@class='bi-plus']");
		Thread.sleep(1000);

		// Click 'subscribe' button
		page.click("(//button[@type='submit'][normalize-space()='Subscribe'])[3]");
		Thread.sleep(4000);

		// Verify add to cart success message
		Locator cartSuccessMessage = page.locator("//div[@class='toast-body']");
		assertTrue(cartSuccessMessage.isVisible(), "Add to Cart success message is not displayed");
		System.out.println("Cart Success Message: " + cartSuccessMessage.textContent());

		// Wait for cart icon to be visible
		Locator cartIcon = page.locator("//i[@class='bi-cart']");
		assertTrue(cartIcon.isVisible(), "Cart icon is not visible");

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();
		System.out.println("✅ Every3day subscribe place complete");
	}

	@Test(priority = 6)
	public void DayWiseSubcribe() throws InterruptedException {

		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		// Scroll until the product is visible
		Locator newArrival = page.locator("//h4[normalize-space()='New Arrival']");
		newArrival.scrollIntoViewIfNeeded();
		Thread.sleep(3000);

		// Click on a product
		page.click("//section[contains(.,'New Arrival')]//div[@class='owl-item active'][1]//h5[@class='product-name']");
		Thread.sleep(3000);

		page.click("//label[normalize-space()='Evening']");// evening selected
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
		page.waitForLoadState();
		System.out.println("✅ Daywise subscribe place complete");
	}

	@Test(priority = 7)
	public void Onlinepay() {
		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();

		try {
			// Click on Wallet
			Locator walletButton = page.locator("//span[@class='wallet-amount fw-semibold']");
			walletButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			walletButton.click();

			// Click on "Pay Online" tab
			Locator payOnlineTab = page.locator("//button[@id='pay-online-tab']");
			payOnlineTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			payOnlineTab.click();

			// Enter amount
			Locator amountInput = page.locator("//input[@id='amountInputOnline']");
			amountInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			amountInput.fill("500"); // ✅ Using `fill()` for better stability

			// Click the "Proceed to Pay" button
			Locator proceedButton = page.locator("//button[normalize-space()='Proceed to Pay']");
			proceedButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			proceedButton.click();

		} catch (Exception e) {
			System.err.println("❌ An error occurred in payOnline(): " + e.getMessage());
		}

		// Click on back button
		Locator backButton = page.locator("//a[normalize-space()='Back']");
		backButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		backButton.click();

		System.out.println("✅ OnlinePay  Sucessfully");
	}

	@Test(priority = 8)
	public void Paycash() {
		page.click("//a[normalize-space()='Home']");
		page.waitForLoadState();
		try {
			// Click on Wallet
			Locator walletButton = page.locator("//span[@class='wallet-amount fw-semibold']");
			walletButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			walletButton.click();

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
			Locator homeButton = page.locator("//a[normalize-space()='Home']");
			homeButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			homeButton.click();

		} catch (Exception e) {
			System.err.println("❌ An error occurred in payCash(): " + e.getMessage());
		}

		System.out.println("✅ Cash Request Sucessfully");
	}

	@Test(priority = 9)
	public void MyProfile() throws InterruptedException {

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
		String mobileNumber = page
				.waitForSelector("//div[@class='col-lg-6 col-md-6 col-12 position-relative'][2]//input").inputValue();
		System.out.println("Mobile Number: " + mobileNumber);

		// Extract address dynamically
		String address = page.waitForSelector("//div[@class='col-md-12 position-relative'][1]/textarea").inputValue();
		System.out.println("Address: " + address);
		String Hubname = page
				.waitForSelector(
						"//div[contains(@class, 'col-md-12') and contains(@class, 'position-relative')][2]//input")
				.inputValue();
		System.out.println("Hub Name: " + Hubname);

		String Area = page
				.waitForSelector(
						"//div[contains(@class, 'col-md-12') and contains(@class, 'position-relative')][3]//input")
				.inputValue();
		System.out.println("Area: " + Area);

		String Walletbal = page
				.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3")
				.textContent();
		System.out.println("Customer Name: " + Walletbal);
		Thread.sleep(1000);

		System.out.println("✅ My profile Details");

	}

	@Test(priority = 10)
	public void MyWallte() throws InterruptedException {
		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);

		page.click("//a[contains(text(),'My Wallet')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("Clicked on 'MyWallet' ");
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
		System.out.println("Clicked on 'Cash' tab.");
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

		String Walletbal = page
				.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3")
				.textContent();
		System.out.println("Customer Name: " + Walletbal);
		Thread.sleep(1000);
		

		System.out.println("Clicked on 'Home' button.");
		System.out.println("✅ My Wallest sucessfully");
	}

	@Test(priority = 11)
	public void BillingHistory() throws InterruptedException {

		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);

		page.click("//a[contains(text(),'Billing History')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("Clicked on 'Billing History' ");

		String WalletBal = page
				.waitForSelector("//div[contains(@class, 'overview-box') and contains(@class, 'wallet-section')]//h3")
				.textContent();
		System.out.println("WalletBal " + WalletBal);
		Thread.sleep(1000);

		page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);

		page.evaluate("window.scrollTo(0, 0)");
		Thread.sleep(2000);
		System.out.println("✅ BillingHistory Completed");

	}

	@Test(priority = 12)
	public void InvoiceHistory() throws InterruptedException {

		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		page.click("//a[contains(text(),'Invoice History')]");
		page.waitForLoadState();

		System.out.println("✅ Invoice History Sucessfully");

	}

	@Test(priority = 13)
	public void SubscribeAdd() throws InterruptedException {
		page.click("//a[normalize-space()='Home']");

		page.waitForLoadState();

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
		Thread.sleep(2000);

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

		page.waitForLoadState();

		System.out.println("✅ Subscribe Add successfully");

	}

	@Test(priority = 14)

	public void SubcribeList() {

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

		System.out.println("✅ Subscribe List successfully");
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
//        browser.close();
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
//       browser.close();
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
//    browser.close();
        }
        System.out.println("✅ Permanet Modify successfully");

    }
 


	@Test(priority = 18)

	public void PauseSubscribe() throws InterruptedException{
		Thread.sleep(2000);
		page.click("(//a[normalize-space()='My Subscriptions'])[1]");
		page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));
		page.waitForLoadState();

		try {

			// 1. Click the Modify button on the first subscription.
			String pauseButtonXpath = "(//button[contains(@class,'btn btn-sm ms-2 pause-btn rounded-pill')][normalize-space()='Pause'])[1]";
			page.locator(pauseButtonXpath).click();
			Thread.sleep(2000);
			System.out.println("click on pause button");

			page.click("(//button[@type='submit'][normalize-space()='Save'])[1]");

			System.out.println("save button click");

			page.waitForLoadState();
			Thread.sleep(1000);

			String successMessageXpath = "(//div[@class='toast-body'])[1]";
			String successMessgeText = page.locator(successMessageXpath).textContent();
			System.out.println("Success message is : " + successMessgeText);
			Thread.sleep(2000);

			String pUASEmessage = "(//p[@class='text-start mb-0 text-danger'])[1]";
			String pausetext = page.locator(pUASEmessage).textContent();
			System.out.println("Success message is : " + pausetext);
			Thread.sleep(2000);

			page.click("(//a[normalize-space()='Resume'])[1]");

			Thread.sleep(2000);
			String Resumemessage = "//div[@id='swal2-content']";
			String Resumetext = page.locator(Resumemessage).textContent();
			System.out.println("Success message is : " + Resumetext);

			page.click("(//button[normalize-space()='Yes, Resume it!'])[1]");

			String successMessageh = "(//div[@class='toast-body'])[1]";
			String successMessgeText2 = page.locator(successMessageh).textContent();
			System.out.println("Success message is : " + successMessgeText2);
			Thread.sleep(2000);

		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		} finally {
//		    browser.close();
		}
		System.out.println("✅ Pause Subscription successfully");
	}

	@Test(priority = 19)

	public void SubscriptionList2() {

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
	private void Scroll() {
		page.evaluate("window.scrollTo(0, document.body.scrollHeight)");

		// You might need a small wait after scrolling depending on the page loading
		// speed
		try {
			Thread.sleep(1000); // 1 second delay (adjust if necessary)
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("✅ Subscription List successfully");
	}

	@Test(priority = 20)
	public void SubscriptionDeleted() {
		page.click("(//a[normalize-space()='My Subscriptions'])[1]");
		page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));
		scrollPageToBottom();
		page.waitForSelector(".accordion-item", new Page.WaitForSelectorOptions().setTimeout(5000));

		while (true) {
			List<ElementHandle> accordionItems = page.querySelectorAll(".accordion-item");
			if (accordionItems.isEmpty()) {
				break; // Exit if no more items
			}
			Iterator<ElementHandle> iterator = accordionItems.iterator();

			while (iterator.hasNext()) {
				ElementHandle accordionItem = iterator.next();

				try {
					// Find the Delete button within the current subscription item
					ElementHandle deleteButton = accordionItem.querySelector("button:has-text('Delete')");

					if (deleteButton == null) {
						System.out.println("Delete button not found in this accordion item");
						continue; // Skip to next subscription
					}

					deleteButton.click();
					// Wait for the modal to appear
					page.waitForSelector(".swal2-shown", new Page.WaitForSelectorOptions().setTimeout(5000));

					// Click "Yes, delete it!" the second time
					page.click("xpath=//button[normalize-space()='Yes, delete it!']");

					// Wait for page load after delete
					page.waitForLoadState();

					try {
						Thread.sleep(2000); // Wait for 2 seconds after page load
						boolean successMsgVisible = page.isVisible("xpath=//div[contains(@class,'toast-body')]");
						if (successMsgVisible) {
							System.out.println("Subscription deleted successfully");
							break; // Exit from inner loop.
						} else {
							System.out.println("Could not find success message");
							Assert.fail("Subscription deletion failed: Success message not found");
						}
					} catch (Exception e) {
						System.out.println("Exception while verifying success message" + e.getMessage());
						Assert.fail("Subscription deletion failed: " + e.getMessage());
					}
				} catch (Exception e) {
					System.out.println("An error occurred while deleting a subscription:" + e.getMessage());
					Assert.fail("Subscription deletion failed due to " + e.getMessage());
				}
			}
		}
		System.out.println("✅ Subscription Deleted successfully");
	}
	@Test(priority = 21)
	
	public void Serachproduct() throws InterruptedException{
		
page.click("//a[normalize-space()='Home']");
		
		System.out.println("Home page click");
		
		page.waitForLoadState();
		Thread.sleep(2000);
		
		page.click("(//i[@class='fa fa-search'])[1]");
		
		Thread.sleep(2002);
		page.fill("(//input[@id='searchInput'])[1]", "A2 DESI COW MILK TETRAPACK");
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
	


System.out.println("✅ Search product order place complete");
}
	

	@Test(priority = 22)
	public void logout() throws InterruptedException {
		page.click("//i[@class='bi-person']");
		Thread.sleep(2000);
		page.click("//a[contains(text(),'Logout')]");
		page.waitForLoadState();
		Thread.sleep(2000);
		System.out.println("✅ Logout successfully");

	}
	
	@AfterClass
	public void tearDown() {
		BrowserSetup.closeBrowser();
	}

}
