package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    ChromeDriver driver;
    WebDriverWait wait;

    public Wrappers(ChromeDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openURL(String URL){
        driver.get(URL);
    }

    public void clickElement(WebElement element){
        try {
            element.click();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Unable to click element: " + e.getMessage());
        }
    }

    public void enterText(WebElement element, String text){
        try{
            element.clear();
            element.sendKeys(text);
        } catch (Exception e){
            System.out.println("Unable to enter text: " + e.getMessage());
        }
    }

    public Boolean waitForElementInvisibility(WebElement name){
        return wait.until(ExpectedConditions.invisibilityOf(name));
    }

    public Boolean waitForURl(String name){
        return wait.until(ExpectedConditions.urlToBe(name));
    }

    public WebElement waitForElementToBeClickable(By name){
        return wait.until(ExpectedConditions.elementToBeClickable(name));
    }

    public WebElement waitForVisibilityOfElement(By name){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(name));
    }

    public List<WebElement> waitForVisibilityOfElements(By name){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(name));
    }

}
