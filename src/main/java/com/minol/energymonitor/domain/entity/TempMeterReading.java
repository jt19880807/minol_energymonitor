package com.minol.energymonitor.domain.entity;

public class TempMeterReading {
    /**
     * 温度
     */
    private double temp;
    /**
     * 湿度
     */
    private double humidity;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
