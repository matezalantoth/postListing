package org.springbootapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springbootapp.models.Group;
import org.springbootapp.models.Post;

import java.util.List;
public class GroupPage {


    public void sendPost(Post post, WebDriver driver) throws InterruptedException {
        driver.get("https://www.facebook.com/groups/" + post.getFirstGroup());
        Thread.sleep(2000);
        String url = driver.findElement(By.xpath("(//span[contains(text(), \"Discussion\")])[2]/parent::div/parent::a")).getAttribute("href");
        driver.get(url);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//span[text() = 'Write something...']")).click();
        Thread.sleep(2000);

        WebElement form = driver.findElement(By.xpath("(//form[@method = 'POST'])[2]"));
        WebElement formfield = form.findElement(By.xpath("//div[@aria-label = 'Write something...']"));
        Thread.sleep(2000);

        form.findElement(By.xpath("//input [@type='file']")).sendKeys(post.getImageLocation());
        Thread.sleep(5000);

        form.findElement(By.xpath("(//div [@aria-label = 'Write something...'])[2]")).sendKeys(post.getTitle() + "\n" + post.getPrice() + "\n" + post.getDescription() + "\n" + post.getLink());
        Thread.sleep(5000);

        form.findElement(By.xpath("(//div [@aria-label = 'Post'])[2]")).click();
        Thread.sleep(10_000);


    }

    public void postListing(Post post, WebDriver driver) throws InterruptedException {
        driver.get("https://www.facebook.com/groups/" + post.getFirstGroup());
        Thread.sleep(2000);
        String url = driver.findElement(By.xpath("(//span[contains(text(), \"Buy and sell\")])[2]/parent::div//parent::a")).getAttribute("href");
        driver.get(url);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@aria-label='Sell Something']")).click();
        Thread.sleep(15000);

        driver.findElement(By.xpath("(//i [@data-visualcompletion=\"css-img\"]/parent::div/parent::div/parent::div/parent::div/parent::div[@role=\"button\"])[3]")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//input[@accept=\"image/*,image/heif,image/heic\"]")).sendKeys(post.getImageLocation());
        Thread.sleep(5000);

        driver.findElement(By.xpath("//label[@aria-label=\"Property for sale or rent\"]")).click();
        Thread.sleep(5000);

        rentOrForSale(post, driver);
        Thread.sleep(5000);

        driver.findElement(By.xpath("//div[@aria-label = \"Next\"]")).click();
        Thread.sleep(5000);

        postToGroups(post, driver);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@aria-label = \"Post\"]")).click();

    }

    public void rentOrForSale(Post post, WebDriver driver) throws InterruptedException {

        if (post.getRentOrSale() == 0) {

            driver.findElement(By.xpath("//div [@role = 'option']")).click();

            driver.findElement(By.xpath("//label [@aria-label=\"Type of property for rent\"]")).click();

            typeOfHouseSelector(post, driver);
            Thread.sleep(2000);
            textBoxes(post, driver);
            Thread.sleep(2000);
            washingMachineOrDryer(post, driver);
            Thread.sleep(2000);
            parkingType(post, driver);
            Thread.sleep(2000);
            airConditioningType(post, driver);
            Thread.sleep(2000);
            heatingType(post, driver);
            Thread.sleep(2000);
            petFriendly(post, driver);
            Thread.sleep(2000);

            return;
        }

        if (post.getRentOrSale() == 1) {

            driver.findElement(By.xpath("(//div [@role = 'option'])[2]")).click();

            typeOfHouseSelector(post, driver);
            Thread.sleep(2000);
            textBoxes(post, driver);
            Thread.sleep(2000);
            washingMachineOrDryer(post, driver);
            Thread.sleep(2000);
            parkingType(post, driver);
            Thread.sleep(2000);
            airConditioningType(post, driver);
            Thread.sleep(2000);
            heatingType(post, driver);
            Thread.sleep(2000);

        }

    }
//Select rentOrSaleDrop = new Select(driver.findElement(By.xpath("//div [@role = 'option']")));

    public void typeOfHouseSelector(Post post, WebDriver driver) {

        if (post.getTypeOfHouse() == 0) {
            //House
            driver.findElement(By.xpath("//div [@role = \"option\"]")).click();
            return;
        }
        if (post.getTypeOfHouse() == 1) {
            //Townhouse
            driver.findElement(By.xpath("(//div [@role = \"option\"])[2]")).click();
            return;
        }
        if (post.getTypeOfHouse() == 2) {
            //Flat or Apartment
            driver.findElement(By.xpath("(//div [@role = \"option\"])[3]")).click();
        }
        if (post.getTypeOfHouse() == 3) {
            //Room only
            if (post.getRentOrSale() == 0) {
                driver.findElement(By.xpath("(//div [@role = \"option\"])[3]")).click();
            }
        }

    }

    public void textBoxes(Post post, WebDriver driver) throws InterruptedException {

        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[2]")).sendKeys(post.getBeds());
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[3]")).sendKeys(post.getBath());
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[4]")).sendKeys(post.getPrice() + "ft");
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[5]")).sendKeys(post.getAddress());
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div [@role = \"option\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//textarea [@dir=\"ltr\"]")).sendKeys(post.getDescription());
        Thread.sleep(5000);
        driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[6]")).sendKeys(post.getPropSize());
        Thread.sleep(2000);
        if (post.getRentOrSale() == 0) {
            driver.findElement(By.xpath("(//input [@dir=\"ltr\"])[7]")).sendKeys(post.getAvailAt());
            Thread.sleep(2000);
        }

    }

    public void washingMachineOrDryer(Post post, WebDriver driver) {

        driver.findElement(By.xpath("//label[@aria-label = \"Washing machine/dryer\"]")).click();

        if (post.getWashingMachineOrDryer() == 0) {
            //Washing Machine
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getWashingMachineOrDryer() == 1) {
            //Launderette
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getWashingMachineOrDryer() == 2) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
            return;
        }
        if (post.getWashingMachineOrDryer() == 3) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[4]")).click();
        }


    }

    public void parkingType(Post post, WebDriver driver) {

        driver.findElement(By.xpath("//label[@aria-label=\"Parking type\"]")).click();

        if (post.getParkingType() == 0) {
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getParkingType() == 1) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getParkingType() == 2) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
            return;
        }
        if (post.getParkingType() == 3) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[4]")).click();
            return;
        }
        if (post.getParkingType() == 4) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[5]")).click();

        }
    }

    public void airConditioningType(Post post, WebDriver driver) {
        driver.findElement(By.xpath("//label[@aria-label=\"Air conditioning\"]")).click();

        if (post.getAirConditioningType() == 0) {
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getAirConditioningType() == 1) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getAirConditioningType() == 2) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
        }


    }

    public void heatingType(Post post, WebDriver driver) {
        driver.findElement(By.xpath("//label[@aria-label=\"Heating type\"]")).click();

        if (post.getHeatingType() == 0) {
            driver.findElement(By.xpath("//div[@role=\"option\"]")).click();
            return;
        }
        if (post.getHeatingType() == 1) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[2]")).click();
            return;
        }
        if (post.getHeatingType() == 2) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[3]")).click();
            return;
        }
        if (post.getHeatingType() == 3) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[4]")).click();
            return;
        }
        if (post.getHeatingType() == 4) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[5]")).click();
            return;
        }
        if (post.getHeatingType() == 5) {
            driver.findElement(By.xpath("(//div[@role=\"option\"])[6]")).click();
        }

    }

    public void petFriendly(Post post, WebDriver driver) {
        if (post.getPetFriendly() == 0) {
            driver.findElement(By.xpath("(//input[@role = \"switch\"])[2]")).click();
            return;
        }
        if (post.getPetFriendly() == 1) {
            driver.findElement(By.xpath("(//input[@role = \"switch\"])[3]")).click();
        }
    }

    public void postToGroups(Post post, WebDriver driver) {

        List<Group> groups = post.getGroups();
        groups.forEach(group -> {
            String groupName = group.getName();
            driver.findElement(By.xpath("//span[contains(text(), " + groupName + ")]/parent::span/parent::span/parent::div/parent::div/parent::div/parent::div/following-sibling::div/child::div/child::div/child::i")).click();

        });
    }

}
