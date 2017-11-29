package com.minol.energymonitor.domain.model;

public class Energy {
    /**
     * 耗热量
     */
    private double heat;
    /**\
     * 耗电量
     */
    private double powerConsumption;
    /**
     * 平均温度
     */
    private double averageTemp;
    /**
     * 最高温度
     */
    private double maxTemp;
    /**
     * 最低温度
     */
    private double minTemp;

    public double getHeat() {
        return heat;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public double getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(double averageTemp) {
        this.averageTemp = averageTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
}
