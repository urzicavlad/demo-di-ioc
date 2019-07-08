package ro.softvision.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

public class Audi implements Car {

    private SpeedProvider speedProvider;


    @Autowired
    private void setSpeedProvider(SpeedProvider speedProvider){
        this.speedProvider = speedProvider;
    }

    @Override
    public int getSpeed() {
        return speedProvider.calculateTheSpeed();
    }
}
