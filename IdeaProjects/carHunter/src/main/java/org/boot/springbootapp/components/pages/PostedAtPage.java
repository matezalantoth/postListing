package org.boot.springbootapp.components.pages;

import org.boot.springbootapp.components.models.Car;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigInteger;

public class PostedAtPage {

    public void getTimePostedAt(WebDriver driver, Car car) throws InterruptedException {

        BigInteger carId = car.getMarketplaceId();
        System.out.println(carId);
        System.out.println("https://www.facebook.com/marketplace/item/" + carId + "/");

        driver.get("https://www.facebook.com/marketplace/item/" + carId + "/");
        Thread.sleep(2000);

        String postedAt = null;
        try {
            postedAt = driver.findElement(By.xpath("//span[@id =\"ssrb_feed_start\"]/parent::div/child::div/child::div/child::div/child::div/child::div/child::div/child::div/following-sibling::div/child::div/child::div/child::div/child::div/child::div/following-sibling::div/following-sibling::div/child::div/child::div/child::div/child::span")).getText();
            System.out.println(postedAt);

        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
        }
        car.setPostedAt(postedAt);
        Thread.sleep(2000);


    }
}
