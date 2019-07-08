package ro.softvision.spring.service;

public class AudiSpeedProvider implements SpeedProvider {

    private int speed;

    @Override
    public int calculateTheSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
