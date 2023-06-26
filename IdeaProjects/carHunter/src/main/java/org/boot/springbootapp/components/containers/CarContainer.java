package org.boot.springbootapp.components.containers;

import org.boot.springbootapp.components.models.Car;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Component
public class CarContainer {

    private List<Car> cars;

    public CarContainer(){
        this.cars = new ArrayList<>();

    }
    public void addPusherData(JSONObject data) {
        BigInteger marketplaceId = data.getJSONObject("car").getBigInteger("carId");
        Integer userId = data.getJSONObject("user").getInt("userId");
        Car car = new Car();
        car.setMarketplaceId(marketplaceId);
        car.setUserId(userId);
        cars.add(car);
    }

    public List<Car> getAllCars(){
        return cars;
    }
}
