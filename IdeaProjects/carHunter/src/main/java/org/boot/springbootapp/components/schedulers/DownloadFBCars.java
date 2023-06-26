package org.boot.springbootapp.components.schedulers;

import org.boot.springbootapp.components.browsers.FacebookBrowser;
import org.boot.springbootapp.components.clients.CarClient;
import org.boot.springbootapp.components.containers.CarContainer;
import org.boot.springbootapp.components.containers.ImageContainer;
import org.boot.springbootapp.components.containers.UserContainer;
import org.boot.springbootapp.components.enums.CarStatus;
import org.boot.springbootapp.components.enums.ImageStatus;
import org.boot.springbootapp.components.enums.UserStatus;
import org.boot.springbootapp.components.models.Car;
import org.boot.springbootapp.components.models.Image;
import org.boot.springbootapp.components.models.User;
import org.boot.springbootapp.components.pages.MarketplacePage;
import org.boot.springbootapp.components.pages.PostedAtPage;
import org.boot.springbootapp.components.updaters.UserUpdater;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DownloadFBCars {

    @Autowired
    UserContainer userContainer;
    @Autowired
    FacebookBrowser facebookBrowser;
    @Autowired
    ImageContainer imageContainer;
    @Autowired
    UserUpdater userUpdater;
    @Autowired
    CarClient carClient;
    @Autowired
    CarContainer carContainer;


    @Scheduled(fixedRate = 600_000, initialDelay = 15_000)
    public void downloadCars() throws Exception {
        Car car = new Car();

        List<User> users = userContainer.getQueue();

        users.forEach(user -> {
            try {

                String link = getLinks(user);
                car.setStatus(CarStatus.IN_PROGRESS);

                WebDriver driver = facebookBrowser.getBrowser(user);

                LocalDateTime changedAt = user.getStatusChangedAt();

                if (changedAt.plusHours(6).isBefore(LocalDateTime.now())) {
                    userUpdater.updateStatus(user, UserStatus.ON_COOLDOWN);
                    return;
                }

                UserStatus currentStatus = user.getStatus();
                if (currentStatus == UserStatus.INVALID || currentStatus == UserStatus.ON_COOLDOWN) {
                    return;
                }

                MarketplacePage marketplacePage = new MarketplacePage(driver, imageContainer);
                List<Car> carsInfo = marketplacePage.getCars(link);
                carClient.sendCarsToServer(carsInfo);
                carsInfo.forEach(finalCar -> {

                    Image image = imageContainer.getImageByMarketplaceId(finalCar.getMarketplaceId());

                    ImageStatus imageStatus = image.getStatus();
                    if (imageStatus == ImageStatus.DOWNLOADED) {
                        return;
                    }

                    try {

                        finalCar.setImageContent(image.getImageContent());
                        carClient.updateCar(finalCar);
                        image.setStatus(ImageStatus.DOWNLOADED);
                        Thread.sleep(1000);

                    } catch (Exception e) {

                        String message = e.getMessage();
                        System.out.println(message);
                        throw new RuntimeException(e);
                    }
                });

                String time = String.valueOf(Instant.now().getEpochSecond());
                System.out.println(time);

                driver.get("https://www.facebook.com");
                Thread.sleep(2000);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        });
    }

    public String getLinks(User user) {
        if (user.getId() == 1) {
            return "https://www.facebook.com/marketplace/budapest/vehicles?minPrice=0&maxPrice=1000000&sortBy=creation_time_descend&exact=false";
        }
        Integer userId = user.getId();
        return "https://www.facebook.com/marketplace/budapest/vehicles?minPrice=" + (userId - 1) + "000000&maxPrice=" + userId + "000000&sortBy=creation_time_descend&exact=false";
    }

    @Scheduled(fixedRate = 60_000, initialDelay = 20_000)
    public void getPostedAt() {
        List<Car> cars = carContainer.getAllCars();
        cars.forEach(car -> {
            try {

                if (car.getStatus() != CarStatus.INIT) {
                    return;
                }

                Integer userId = car.getUserId();
                BigInteger newCarId = car.getMarketplaceId();
                System.out.println(newCarId);
                User user = userContainer.getFbUserByUserId(userId);

                WebDriver driver = facebookBrowser.getBrowser(user);

                PostedAtPage postedAtPage = new PostedAtPage();
                Car newCar = new Car();
                newCar.setMarketplaceId(newCarId);
                postedAtPage.getTimePostedAt(driver, newCar);
                LocalDateTime time = LocalDateTime.now();
                String postedAt = newCar.getPostedAt();
                String truePostedAt = (postedAt + ":" + String.valueOf(time));
                newCar.setPostedAt(truePostedAt);
                carClient.updateCar(newCar);

                car.setStatus(CarStatus.FINISHED);

            } catch (Exception e) {
                car.setStatus(CarStatus.FAILED);

            }
        });
    }
}

