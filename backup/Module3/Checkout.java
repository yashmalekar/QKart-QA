package QKART_SANITY_LOGIN.Module1;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            /*
             * Click on the "Add new address" button, enter the addressString in the address text
             * box and click on the "ADD" button to save the address
             */
            // Clicking on "Add new address" button
            driver.findElement(By.id("add-new-btn")).click();
            // Entering text in address textbox
            driver.findElement(
                    By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[1]/div/textarea[1]"))
                    .sendKeys(addresString);
            // clicking on "Add new address" button
            driver.findElement(
                    By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[2]/button[1]"))
                    .click();
            // Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[1]/div/div[1]/p"),
                    addresString));

            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through all the address boxes to find the address box with matching text,
             * addressToSelect and click on it
             */
            List<WebElement> address =
                    driver.findElements(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[1]"));
            for (WebElement adrs : address) {
                String addrsText = adrs.getText();
                if (addrsText.contains(addressToSelect)) {
                    adrs.findElement(By.xpath(".//div/div/span/input")).click();
                    return true;
                }
            }
            System.out.println("Unable to find the given address");
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find the "PLACE ORDER" button and click on it
            driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/button[2]")).click();
            // Thread.sleep(3000);
            WebElement elem = driver.findElement(By.xpath("//*[@id='notistack-snackbar']"));
            if(elem.isDisplayed())
            {
                return true;
            }
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.urlContains("/thanks"));
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 08: MILESTONE 7
            WebElement Message = driver.findElement(By.id("notistack-snackbar"));
            String msg = Message.getText();
            if (Message.getText().contentEquals(msg)) {
                status = true;
            }

            return status;
        } catch (Exception e) {
            System.out.println(
                    "Exception while verifying insufficient balance message: " + e.getMessage());
            return status;
        }
    }

    public void checkButton(WebElement frame){
        boolean status = false;
        try {
            QkartSanity q = new QkartSanity();
            driver.switchTo().frame(frame);
        List<WebElement> buttons = driver.findElements(By.xpath("//div[@class='action_buttons']/button"));
        for(WebElement button:buttons)
        {
            if(button.getText().contains("Buy Now"))
            {
                button.click();
                String url = "/checkout";
                status = driver.getCurrentUrl().contains(url);
                driver.navigate().back();
                q.logStatus("TestCase Step", "Buy now is clickable", status?"PASS":"FAIL");
            }
            else
            {
                status = button.isEnabled();
                q.logStatus("TestCase Step", "View Cart is clickable", status?"PASS":"FAIL");
            }
        }
        driver.switchTo().parentFrame();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(
                "Exception while Checking Button: " + e.getMessage());
        }
    }
}
