package org.springbootapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springbootapp.models.Group;

import java.util.ArrayList;
import java.util.List;

public class LinksPage {

    WebDriver driver;

    public LinksPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Group> getLinks() throws InterruptedException {

        driver.get("https://www.facebook.com/groups/joins");

        Thread.sleep(2000);

        scrollDown();

        List<Group> groups = new ArrayList<>();

        driver.findElements(By.xpath("//a [@aria-label='View group']")).forEach(groupLink -> {

            Group group = new Group();

            group.setLink(groupLink.getAttribute("href"));
            System.out.println(groupLink.getAttribute("href"));

            group.setName(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/child::div/following-sibling::div/div/div/span/span/div/a")).getText());
            System.out.println(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/child::div/following-sibling::div/div/div/span/span/div/a")).getText());

            group.setImage(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/div/a/div/div//*//*/following-sibling::*//*")).getAttribute("xlink:href"));
            System.out.println(groupLink.findElement(By.xpath("./parent::div/preceding-sibling::div/div/a/div/div//*//*/following-sibling::*//*")).getAttribute("xlink:href"));

            groups.add(group);

        });
        return groups;

    }
    private void scrollDown() throws InterruptedException {

        for (int i = 1; i <= 50; i++) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 10000)", "");
            Thread.sleep(2000);
            System.out.println(i);

        }
    }
}
