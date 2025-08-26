package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrapper; 

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    /*
     * Test Case 1 - Look for high rated Washing Machines
     * 
     * 1. Open Flipkart landing page
     * 2. Find search bar to enter required text
     * 3. Enter text "Washing Machine" and press enter
     * 4. Sort products by Popularity
     * 5. Get the list of all product ratings
     * 6. Count the number of products with rating <= 4
     * */
    @Test
    public void testCase01(){

        //Test Case 1: Look for high rated Washing Machines
        //Opening flipkart landing page
        wrapper.openURL("http://www.flipkart.com/");
        System.out.println("Flipkart landing page opened successfully");

        //finding search bar to enter required text
        WebElement searchBar = wrapper.waitForVisibilityOfElement(By.xpath("//button/following::input[@placeholder='Search for Products, Brands and More']"));
        System.out.println("Search bar found.");

        //Enter text "Washing Machine" and press enter
        wrapper.enterText(searchBar, "Washing Machine");
        System.out.println("Washing Machine entered in search bar.");
        searchBar.sendKeys(Keys.ENTER);
        System.out.println("Search submitted.");

        //Sort products by Popularity
        WebElement sortByPopularity = wrapper.waitForElementToBeClickable(By.xpath("//div[@id='container']/descendant::div[normalize-space(text())='Popularity']"));
        System.out.println("Sort by Popularity found.");
        wrapper.clickElement(sortByPopularity);
        System.out.println("Products sorted by Popularity.");

        //Get the list of all product ratings
        List<WebElement> productRatings = wrapper.waitForVisibilityOfElements(By.xpath("//span[contains(@id,'productRating_') and @class='Y1HWO0']"));
        System.out.println("Product ratings found.");

        //Count the number of products with rating <= 4
        int count = 0;
        for(WebElement ratingElement: productRatings){
            String ratingText = ratingElement.getText().replaceAll("[^0-9.]", "");
            float value = Float.parseFloat(ratingText);
            if(value<=4){
                count++;
            }
        }
        System.out.println("Products with rating <= 4: " + count);
        if (count == 0) {
            System.out.println("No products with rating <= 4 found.");
        }
        
    }

    @Test
    public void testCase02(){

        //Test Case 2: Look for phones with certain discount
        //Opening flipkart landing page
        wrapper.openURL("http://www.flipkart.com/");
        System.out.println("Flipkart landing page opened successfully");

        //finding search bar to enter required text
        WebElement searchBar = wrapper.waitForVisibilityOfElement(By.xpath("//button/following::input[@placeholder='Search for Products, Brands and More']"));
        System.out.println("Search bar found.");

        //Enter text "iPhone" and press enter
        wrapper.enterText(searchBar, "iPhone");
        System.out.println("Entered search text: iPhone");
        searchBar.sendKeys(Keys.ENTER);
        System.out.println("Search submitted.");


        //Get the list of all product discounts and titles
        List<WebElement> discounts = wrapper.waitForVisibilityOfElements(By.xpath("//div[@class='UkUFwK']/child::span"));
        System.out.println("Product discounts found.");


        List<WebElement> titles = wrapper.waitForVisibilityOfElements(By.xpath("//div[@class='UkUFwK']/preceding::div[@class='KzDlHZ']"));
        System.out.println("Product titles found.");

        //Iterate through the products and print the title and discount for products with discount > 17%
        for(int i=0; i< titles.size() && i<discounts.size(); i++){
            String title = titles.get(i).getText();
            String discountText = discounts.get(i).getText();
            
            //Extract the discount value from the text
            int discount = Integer.parseInt(discountText.replaceAll("[^0-9.]", ""));
            
            //Check if the discount is greater than 17%
            if(discount>17){
                System.out.println("Product with discount > 17%: " + title + " -" + discountText);
            }
        }
        
    }

    @Test
    public void testCase03() throws InterruptedException{
        
        //Opening flipkart landing page
        wrapper.openURL("http://www.flipkart.com/");
        System.out.println("Flipkart landing page opened successfully");

        //finding search bar to enter required text
        WebElement searchBar = wrapper.waitForVisibilityOfElement(By.xpath("//button/following::input[@placeholder='Search for Products, Brands and More']"));
        System.out.println("Search bar found successfully");

        //sending text to search bar
        wrapper.enterText(searchBar, "Coffee Mug");
        System.out.println("Entered search text: Coffee Mug");

        //Pressing enter after sending/entering the text
        searchBar.sendKeys(Keys.ENTER);
        System.out.println("Search submitted.");

        //Locating checkbox of rating and clicking on it
        WebElement checkBox = wrapper.waitForElementToBeClickable(By.xpath("(//input[@type='checkbox']/following::div[@class='XqNaEv'])[1]"));
        System.out.println("Rating checkbox found successfully");
        wrapper.clickElement(checkBox);
        System.out.println("Rating checkbox clicked.");

        //Locating list of elements for reviews, titles and images
        List<WebElement> numberOfReviews = wrapper.waitForVisibilityOfElements(By.xpath("//div[@class='_5OesEi afFzxY']/child::span[2]"));
        List<WebElement> titles = wrapper.waitForVisibilityOfElements(By.xpath("//a[@class='wjcEIp' and @rel='noopener noreferrer']"));
        List<WebElement> images = wrapper.waitForVisibilityOfElements(By.xpath("//div[@class='_4WELSP']/child::img[@src]"));
        System.out.println("List of elements for reviews, titles and images found.");

        //printing the Title and image URL of the 5 items with highest number of reviews

        List<Map<String, Object>> items = new ArrayList<>();

        for(int i=0; i< titles.size() && i<numberOfReviews.size() && i<images.size() ; i++){
            String title, reviewsText, imagesrc;
            try{
                title = titles.get(i).getText();
                reviewsText = numberOfReviews.get(i).getText();
                imagesrc= images.get(i).getAttribute("src");

            } catch(StaleElementReferenceException e){
                numberOfReviews = wrapper.waitForVisibilityOfElements(By.xpath("//div[@class='_5OesEi afFzxY']/child::span[2]"));
                titles = wrapper.waitForVisibilityOfElements(By.xpath("//a[@class='wjcEIp' and @rel='noopener noreferrer']"));
                images = wrapper.waitForVisibilityOfElements(By.xpath("//div[@class='_4WELSP']/child::img[@src]"));
                title = titles.get(i).getText();
                reviewsText = numberOfReviews.get(i).getText();
                imagesrc= images.get(i).getAttribute("src");


            }
            
            int reviewCount = Integer.parseInt(reviewsText.replaceAll("[^0-9.]", ""));

            Map<String, Object> product = new HashMap<>();
            product.put("title", title);
            product.put("reviews", reviewCount);
            product.put("imageURL", imagesrc);
            items.add(product); 
            
        }


        //Sorting the list of items based on number of reviews in descending order 
        items.sort((a,b) -> Integer.compare((int)b.get("reviews"), (int)a.get("reviews")));
        System.out.println("Items sorted based on number of reviews.");

        //Printing the title and image URL of the 5 items with highest number of reviews
        for(int i=0; i<Math.min(5, items.size()); i++){
            Map<String, Object> product = items.get(i);
            System.out.println(product.get("title") + " | Reviews: " + product.get("reviews") +
                       " | Image: " + product.get("imageURL"));

        }


    }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);
        wrapper = new Wrappers(driver);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}