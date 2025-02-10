package utilities;

import com.microsoft.playwright.*;

public class BrowserSetup {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    public static Page launchBrowser(String url) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setArgs(java.util.Arrays.asList("--kiosk"))  // Using --kiosk for full-screen
                .setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate(url);
        
        return page;
    }

    public static void closeBrowser() {
        if (browser != null) {
            browser.close();
            playwright.close();
        }
    }
}