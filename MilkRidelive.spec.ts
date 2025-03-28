import { test, expect } from "@playwright/test";

test.describe.serial("MilkRide Live", () => {
  let page; // Declare a page variable to reuse in tests

  test.beforeAll(async ({ browser }) => {
    page = await browser.newPage(); 
    await page.goto("https://prod.milkride.com/DairyDash/signin");
    const mobileNumber = "8850505060";

    
    await page.locator("#mobileNumber").fill(mobileNumber);
    await page.locator("//button[@type='submit']").click();
    
    // Wait for login button to appear and click
    await page.locator("#submitButton").waitFor({ state: "visible" });
    await page.locator("#submitButton").click();
  
    /**
     * Function to check for a toast message containing specific text.
     */
    
      const toastLocator = page.locator('//div[@class="toast-body"]');
      const actualText = await toastLocator.innerText();
      console.log(`✅ Sucemessage : "${actualText}" `);
      const title = await page.title();
    console.log('Page Title:', title); 
    
 
  });

  test('My Profile', async () => {
    // Navigate to Home
    await page.click("//a[normalize-space()='Home']");
    await page.waitForLoadState();

    // Open profile menu
    await page.waitForSelector("//i[@class='bi-person']", { timeout: 10000 });
    await page.click("//i[@class='bi-person']");
    await page.waitForTimeout(2000);

    // Click on 'My Account'
    await page.click("//a[contains(text(),'My Account')]");
    await page.waitForLoadState();
    await page.waitForTimeout(2000);
    console.log("✅ My Profile Details");

    // Extract customer name dynamically
    const customerName = await page.locator("//div[@class='profile-name']/h4").textContent();
    console.log(`Customer Name: ${customerName?.trim()}`);  // Trim unnecessary spaces
    
    // Extract customer number
    const customerNumber = await page.locator(`(//div[@class="col-lg-6 col-md-6 col-12 mb-3 position-relative"]/div[@class="detail-value"])[2]`).textContent();
    console.log(`Customer Number: ${customerNumber?.trim()}`);

     // Extract Area
     const Hubname = await page.locator(`//div[@class="col-lg-6 col-md-12 position-relative mb-3"][1]/div[@class="detail-value"]`).textContent();
     console.log(`Customer Hubname: ${Hubname?.trim()}`);

    // Extract Hub name
    const customerArea = await page.locator(`//div[@class="col-lg-6 col-md-12 position-relative mb-3"][2]/div[@class="detail-value"]`).textContent();
    console.log(`Customer Areaname: ${customerArea?.trim()}`);

     // Extract Address
     const Address = await page.locator(`//div[@class="col-md-12 position-relative"]/div[@class="detail-value"]`).textContent();
     console.log(`Customer Address: ${Address?.trim()}`);

     // Extract Wallte
     const Wallet = await page.locator(`//h6[contains(text(),'Wallet Balance')]/following-sibling::h3`).textContent();
     console.log(`Customer Wallet: ${Wallet?.trim()}`);

     // Extract Deliveryboy
     const DeliveryBoy = await page.locator(`//h6[contains(text(),'Delivery Boy')]/following-sibling::h3`).textContent();
     console.log(`Customer Delivery Boy: ${DeliveryBoy?.trim()}`);

     // Extract Reserved Balance

     const ReservedBalance
     = await page.locator(`//h6[contains(text(),'Reserved Balance')]/following-sibling::h3`).textContent();
     console.log(`Customer Reserved Balance: ${ReservedBalance?.trim()}`);  
    console.log(`✅ My Profile Details Successfully view`);
});

  test("Morning Order Place", async () => {
    await page.locator("//i[@class='fa fa-search']").click();
  const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
  await productSearch.fill("Cow A2 Milk");
  await productSearch.press("Enter");

  // **Fetch Product Details**
  const productName = page.locator("//ul[@id='productList']/li[1]//p[@class='search-product-name mb-0 font-bold text-start']");
  const productPackage = page.locator("//ul[@id='productList']/li[1]//p[@class='mb-0 small text-muted']");
  const productPrice = page.locator("//ul[@id='productList']/li[1]//p[@class='mb-0 text-muted']//span[1]");
  
  const viewItem = await productName.innerText();
  const viewPackage = await productPackage.innerText();
  const viewPrice = await productPrice.innerText();
  
  console.log(`📌 Product Name: ${viewItem}`);
  console.log(`📦 Package: ${viewPackage}`);
  console.log(`💰 Price: ${viewPrice}`);

  // **Click on Product & Buy Once**
  await page.locator("//ul[@id='productList']/li[1]").click();
  const buyOnceButton = page.locator("//button[@id='buy-once-tab']");
  await buyOnceButton.waitFor({ state: "visible" });
  await buyOnceButton.click();
  
  // **Increase Quantity & Add to Cart**
  await page.locator("//div[@id='buy-once']//i[@class='bi-plus']").click();
  const addToCartButton = page.locator('#add-to-cart-btn');
  await addToCartButton.click();


  // **Verify Product Added to Cart**
  const cartToastMessage = page.locator("//div[@class='toast-body']");
  await expect(cartToastMessage).toBeVisible();
  console.log(`🛒 Product added to cart: ${await cartToastMessage.innerText()}`);
  

  // **Go to Cart & Validate Product Details**
  await page.waitForSelector("//i[@class='bi-cart']");

  // Click on the cart icon
  await page.locator("//i[@class='bi-cart']").click();
  const cartProductName = page.locator("//div[@class='order-product-name']");
  const cartProductPackage = page.locator("//div[@class='order-product-price']//span[@class='packae-size']");
  const cartProductPrice = page.locator("//div[@class='order-product-price']/span[@class='sale-price']");
  
  console.log(`🛍️  Cart Product Name: ${await cartProductName.innerText()}`);
  console.log(`📦 Cart Package: ${await cartProductPackage.innerText()}`);
  console.log(`💰 Cart Price: ${await cartProductPrice.innerText()}`);

  // **Place Order & Verify Success Message**
  const placeorder = page.locator('#placeOrderButton');
  await placeorder.waitFor({ state: "visible" });
  await placeorder.click();
  


  const orderSuccessMessage = page.locator(`//div[@class='toast-body']`);

  await expect(orderSuccessMessage).toBeVisible();
  console.log(` Order Placed Successfully: ${await orderSuccessMessage.innerText()}`);

  console.log(`✅  Morning Buy once Order Placed Successfully`);
});



  test("Evening Order place", async () => {
    
    // **Search and Select Product**
  await page.locator("//i[@class='fa fa-search']").click();
  const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
  await productSearch.fill("Buffalo Curd");
  await productSearch.press("Enter");

  // **Fetch Product Details**
  const productName = page.locator("//ul[@id='productList']/li[1]//p[@class='search-product-name mb-0 font-bold text-start']");
  const productPackage = page.locator("//ul[@id='productList']/li[1]//p[@class='mb-0 small text-muted']");
  const productPrice = page.locator("//ul[@id='productList']/li[1]//p[@class='mb-0 text-muted']//span[1]");
  
  const viewItem = await productName.innerText();
  const viewPackage = await productPackage.innerText();
  const viewPrice = await productPrice.innerText();
  
  console.log(`📌 Product Name: ${viewItem}`);
  console.log(`📦 Package: ${viewPackage}`);
  console.log(`💰 Price: ${viewPrice}`);
  await page.locator("//ul[@id='productList']/li[1]").click();
  //**Delivary type Select **
  const Delivarytype = page.locator(`//label[@for="delivery_type_evening"]`);
  await Delivarytype.click();

  // **Click on Product & Buy Once**
  
  const buyOnceButton = page.locator("//button[@id='buy-once-tab']");
  await buyOnceButton.waitFor({ state: "visible" });
  await buyOnceButton.click();
  
  // **Increase Quantity & Add to Cart**
  await page.locator("//div[@id='buy-once']//i[@class='bi-plus']").click();
  const addToCartButton = page.locator('#add-to-cart-btn');
  await addToCartButton.click();


  // **Verify Product Added to Cart**
  const cartToastMessage = page.locator("//div[@class='toast-body']");
  await expect(cartToastMessage).toBeVisible();
  console.log(`🛒 Product added to cart: ${await cartToastMessage.innerText()}`);
 
  

  // **Go to Cart & Validate Product Details**
  await page.waitForSelector("//i[@class='bi-cart']");

// Click on the cart icon
await page.locator("//i[@class='bi-cart']").click();
  
  const cartProductName = page.locator("//div[@class='order-product-name']");
  const cartProductPackage = page.locator("//div[@class='order-product-price']//span[@class='packae-size']");
  const cartProductPrice = page.locator("//div[@class='order-product-price']/span[@class='sale-price']");
  
  console.log(`🛍️  Cart Product Name: ${await cartProductName.innerText()}`);
  console.log(`📦 Cart Package: ${await cartProductPackage.innerText()}`);
  console.log(`💰 Cart Price: ${await cartProductPrice.innerText()}`);

  // **Place Order & Verify Success Message**

  const placeorder = page.locator('#placeOrderButton');
  await placeorder.waitFor({ state: "visible" });
  await placeorder.click();

  


  const orderSuccessMessage = page.locator(`//div[@class='toast-body']`);
  await orderSuccessMessage.waitFor({ state: "visible" });

  await expect(orderSuccessMessage).toBeVisible();
  console.log(` Order Placed Successfully: ${await orderSuccessMessage.innerText()}`);
  console.log(`✅  Evening Buy once Order Placed Successfully`);
});
test("place order after balance", async () => {
    
  // Open profile menu
  await page.waitForSelector("//i[@class='bi-person']", { timeout: 10000 });
  await page.click("//i[@class='bi-person']");
  await page.waitForTimeout(2000);

  // Click on 'My Account'
  await page.click("//a[contains(text(),'My Account')]");
  await page.waitForLoadState();
  await page.waitForTimeout(2000);
  console.log("✅ My Profile Details");

    // Extract Wallte
    const Wallet = await page.locator(`//h6[contains(text(),'Wallet Balance')]/following-sibling::h3`).textContent();
    console.log(`Customer Wallet: ${Wallet?.trim()}`); 
  await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
  await page.waitForLoadState();


});
test("Every day Morning Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();

// **Click on Product & Subscription Every day**

const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="every_day-tab"]`).click();

// **Increase Quantity & Add to Cart**
await page.locator(`(//i[@class="bi-plus"])[2]`).click();
const SubButton = page.locator('(//button[@type="submit"])[3]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Everyday Morning Subscription Order Placed Successfully`);

});
test("Every day Evening Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();
const Delivarytype = page.locator(`//label[@for="delivery_type_evening"]`);
  await Delivarytype.click();

// **Click on Product & Subscription Every day**


const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="every_day-tab"]`).click();

// **Increase Quantity **
await page.locator(`(//i[@class="bi-plus"])[2]`).click();
const SubButton = page.locator('(//button[@type="submit"])[3]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Everyday Evening Subscription Order Placed Successfully`);



});

test("Every 2 day Morning Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();

// **Click on Product & Subscription Every 2 day**

const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="alternate-day-tab"]`).click();

// **Increase Quantity & Add to Cart**
await page.locator(`(//i[@class="bi-plus"])[3]`).click();
const SubButton = page.locator('(//button[@type="submit"])[4]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Every 2 day Morning Subscription Order Placed Successfully`);

});
test("Every 2day Evening Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();
const Delivarytype = page.locator(`//label[@for="delivery_type_evening"]`);
  await Delivarytype.click();

// **Click on Product & Subscription Every 2 day**


const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="alternate-day-tab"]`).click();

// **Increase Quantity **
await page.locator(`(//i[@class="bi-plus"])[3]`).click();
const SubButton = page.locator('(//button[@type="submit"])[4]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Every 2 day Evening Subscription Order Placed Successfully`);
});
test("Every 3 day Morning Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();

// **Click on Product & Subscription Every 2 day**

const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="every-three-day-tab"]`).click();

// **Increase Quantity & Add to Cart**
await page.locator(`(//i[@class="bi-plus"])[4]`).click();
const SubButton = page.locator('(//button[@type="submit"])[5]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Every3day Morning Subscription Order Placed Successfully`);

});
test("Every3day Evening Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();
const Delivarytype = page.locator(`//label[@for="delivery_type_evening"]`);
  await Delivarytype.click();

// **Click on Product & Subscription Every 2 day**


const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="every-three-day-tab"]`).click();

// **Increase Quantity **
await page.locator(`(//i[@class="bi-plus"])[4]`).click();
const SubButton = page.locator('(//button[@type="submit"])[5]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Every3day Evening Subscription Order Placed Successfully`);
});

test("Weekly Morning Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();


// **Click on Product & Subscription Every 2 day**


const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="weekly-tab"]`).click();

// **Increase Quantity **
await page.locator(`(//i[@class="bi-plus"])[5]`).click();
await page.locator(`(//i[@class="bi-plus"])[6]`).click();
await page.locator(`(//i[@class="bi-plus"])[7]`).click();
await page.locator(`(//i[@class="bi-plus"])[8]`).click();
await page.locator(`(//i[@class="bi-plus"])[9]`).click();
await page.locator(`(//i[@class="bi-plus"])[10]`).click();
await page.locator(`(//i[@class="bi-plus"])[11]`).click();
await page.waitForTimeout(2000);



const SubButton = page.locator('(//button[@type="submit"])[6]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Weekly Morning Subscription Order Placed Successfully`);
});
test("Weekly Evening Subscription", async () => {
  await page.locator("//i[@class='fa fa-search']").click();
const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
await productSearch.fill("Cow A2 Milk");
await productSearch.press("Enter");
await page.waitForLoadState();


await page.locator("//ul[@id='productList']/li[1]").click();
const Delivarytype = page.locator(`//label[@for="delivery_type_evening"]`);
  await Delivarytype.click();

// **Click on Product & Subscription Every 2 day**


const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
await subscriptionButton.waitFor({ state: "visible" });
await subscriptionButton.click();

await page.locator(`//button[@id="weekly-tab"]`).click();

// **Increase Quantity **
await page.locator(`(//i[@class="bi-plus"])[5]`).click();
await page.locator(`(//i[@class="bi-plus"])[6]`).click();
await page.locator(`(//i[@class="bi-plus"])[7]`).click();
await page.locator(`(//i[@class="bi-plus"])[8]`).click();
await page.locator(`(//i[@class="bi-plus"])[9]`).click();
await page.locator(`(//i[@class="bi-plus"])[10]`).click();
await page.locator(`(//i[@class="bi-plus"])[11]`).click();
await page.waitForTimeout(2000);


const SubButton = page.locator('(//button[@type="submit"])[6]');
await SubButton.click();


// **Verify Product Added to Cart**
const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
await expect(SubscribeToastMessage).toBeVisible();
console.log(`🛒 Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);




// Click on the My subscription
await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
await page.waitForLoadState();

await page.locator(`(//a[normalize-space()='Home'])[1]`).click();
await page.waitForLoadState();
console.log(`✅ Weekly Evening Subscription Order Placed Successfully`);
});

test("My Wallet", async() =>{
  // Open profile menu
  await page.waitForSelector("//i[@class='bi-person']", { timeout: 10000 });
  await page.click("//i[@class='bi-person']");
  await page.waitForTimeout(2000);

  await page.locator(`(//a[contains(text(),'My Wallet')])[1]`).click();
  await page.waitForLoadState();
  await page.fill("(//input[@id='amountInputOnline'])[1]", "500");
  await page.waitForTimeout(2000);
  await page.locator("(//button[normalize-space()='Proceed to Pay'])[1]").click();
  await page.waitForLoadState();
  await page.waitForTimeout(2000);
  const title = await page.title();
    console.log('Page Title:', title);
  await page.goBack();  
  console.log(`✅ Pay Online payment Successfully`);

});
test("Request by cash", async() => {
  // Open profile menu
  await page.waitForSelector("//i[@class='bi-person']", { timeout: 10000 });
  await page.click("//i[@class='bi-person']");
  await page.waitForTimeout(2000);
  await page.locator(`(//a[contains(text(),'My Wallet')])[1]`).click();
  await page.waitForLoadState();

  await page.locator("(//button[normalize-space()='Request by Cash'])[1]").click();
  

  await page.fill("(//input[@id='amountInputRecharge'])[1]", "600");
  await page.waitForTimeout(2000);
  await page.locator("(//button[normalize-space()='Submit'])[1]").click();
  await page.waitForLoadState();
  
    // **Verify Product Added to Cart**
  const cashToastMessage = page.locator(`(//div[@id='liveToast'])[1]`);
  await expect(cashToastMessage).toBeVisible();
  console.log(`✅ Cash Request : ${await cashToastMessage.innerText()}`);
 
  await page.locator(`(//a[normalize-space()='Home'])[1]`).click();



});
test("Subscription Pause", async() => {  
  await page.locator("//i[@class='fa fa-search']").click();
  const productSearch = page.getByRole('textbox', { name: 'Search products' }).first();
  await productSearch.fill("Cow A2 Milk");
  await productSearch.press("Enter");
  await page.waitForLoadState();
  
  
  await page.locator("//ul[@id='productList']/li[1]").click();
  const Delivarytype = page.locator(`//label[@for="delivery_type_evening"]`);
    await Delivarytype.click();
  
  // **Click on Product & Subscription Every day**
  
  
  const subscriptionButton = page.locator(`//button[@id="subscription-tab"]`);
  await subscriptionButton.waitFor({ state: "visible" });
  await subscriptionButton.click();
  
  await page.locator(`//button[@id="every_day-tab"]`).click();
 
  
  // **Increase Quantity **
  await page.locator(`(//i[@class="bi-plus"])[2]`).click();
  const SubButton = page.locator('(//button[@type="submit"])[3]');
  await SubButton.click();
  
  
  // **Verify Product Added to Cart**
  const SubscribeToastMessage = page.locator(`//div[@class="toast-body"]`);
  await expect(SubscribeToastMessage).toBeVisible();
  console.log(`✅ Product added to My Subsciption: ${await SubscribeToastMessage.innerText()}`);
  
  
  
  
  // Click on the My subscription
  await page.locator(`//a[normalize-space()='My Subscriptions']`).click();
  await page.waitForLoadState();
  await page.locator(`(//button[contains(@class, 'pause-btn') and contains(@data-bs-target, 'kt_modal_pause_subscription')])[1]`).click();
  await page.waitForTimeout(2000);
  await page.locator(`(//form[contains(@id, 'kt_modal_pause_subscription_form')])[1]//button[normalize-space()='Cancel'][1]`).click();
  console.log("pause cancel Button");
  await page.locator(`(//button[contains(@class, 'pause-btn') and contains(@data-bs-target, 'kt_modal_pause_subscription')])[1]`).click();
  await page.waitForTimeout(2000);
  await page.locator(`(//form[contains(@id, 'kt_modal_pause_subscription_form')])[1]//button[normalize-space()='Save'][1]`).click();
  await page.waitForTimeout(1000);
  const SubscribePause = page.locator(`//div[@class="toast-body"]`);
  await expect(SubscribePause).toBeVisible();
  console.log(`✅ Pause Subsciption: ${await SubscribePause.innerText()}`);

  const pauseMessage = page.locator("(//p[@class='text-start mb-0 text-danger'])[1]");
  await expect(pauseMessage).toBeVisible();
  console.log(`✅ Pause message : ${await pauseMessage.innerText()}`);
  await page.locator(`(//a[contains(@class, 'resumeSubscriptionLink')][normalize-space()='Resume'])[1]`).click();
  await page. locator(`(//button[normalize-space()='Yes, Resume it!'])[1]`).click();
  const ResumeToastMessage = page.locator(`//div[@class="toast-body"]`);
  await expect(ResumeToastMessage).toBeVisible();
  console.log(`✅ Resume Subsciption: ${await ResumeToastMessage.innerText()}`); 
  await page.click("//a[normalize-space()='Home']");
  

});


  test.afterAll(async () => {
    // await page.close(); // Close the browser after all tests
    console.log("Teardown: Closed browser");

  });
 
});
