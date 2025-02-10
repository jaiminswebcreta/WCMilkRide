package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utilities.BrowserSetup;

import static org.testng.Assert.assertTrue;

import java.util.List;

public class ProductList {
    private Page page;

    @BeforeClass
    public void setUp() throws InterruptedException {
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

    @Test
    public void productName() throws InterruptedException {

        page.waitForLoadState();
        Thread.sleep(1000);

        page.click("//a[normalize-space()='Home']");
        page.waitForLoadState();

        //Click View All categories
        page.locator("//a[normalize-space()='View All']").first().click();
        page.waitForLoadState();
        Thread.sleep(1000);
        System.out.println("view button click");

        List<Locator> categoryDivs = page.locator("//div[contains(@class, 'owl-item')]//a").all();

        for (int i = 0; i < categoryDivs.size(); i++) {
            Locator categoryDiv = categoryDivs.get(i);
            String categoryName = categoryDiv.locator("h5").innerText();
            System.out.println("Category Name: " + categoryName);

            categoryDiv.locator("h5").click(); // Click the h5 element instead
            page.waitForLoadState();
            Thread.sleep(2000); // Give extra time for products to load

            //  Updated product locator
            List<Locator> productDivs = page.locator("//div[contains(@class, 'col-lg-3') and contains(@class, 'col-sm-6')]").all();

            if (productDivs.isEmpty()) {
                System.out.println("No products available in category: " + categoryName);
                page.goBack();
                page.waitForLoadState();
                categoryDivs = page.locator("//div[contains(@class, 'owl-item')]//a").all(); // refresh the catagory again due to page back
                continue;
            } else {
                System.out.println("Found " + productDivs.size() + " products in category: " + categoryName); // New log
            }

            for (int j = 0; j < productDivs.size(); j++) {

                Locator productDiv = productDivs.get(j);

                try {
                    String productName = productDiv.locator("h5.product-name").innerText();  // Updated locator to CSS
                    String salePrice = productDiv.locator("h6.product-price span.sale-price").innerText(); // Updated locator to CSS

                    String mrpPrice = null;

                    // Check if MRP element exists before trying to get its text
                    Locator mrpLocator = productDiv.locator("h6.product-price span.mrp-price");
                    if (mrpLocator.count() > 0) {
                        mrpPrice = mrpLocator.innerText();
                    } else {
                        System.out.println("MRP not found for product: " + productName);
                    }

                    System.out.println("  Product Name: " + productName);
                    System.out.println("  Sale Price: " + salePrice);
                    if (mrpPrice != null) {
                        System.out.println("  MRP: " + mrpPrice);
                    }

                } catch (Exception e) {
                    System.err.println("Error extracting product details: " + e.getMessage());
                }
            }
            page.goBack();
            page.waitForLoadState();
            categoryDivs = page.locator("//div[contains(@class, 'owl-item')]//a").all(); // refresh the catagory again due to page back
        }
        page.locator("//a[normalize-space()='Home']").click();

//	        browser.close();
    }

    @AfterClass
    public void tearDown() {
        BrowserSetup.closeBrowser();
    }
}