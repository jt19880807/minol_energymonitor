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
    /**
     * 总能耗
     */
    private double totalEnergyConsumption;
    /**
     * SCOP
     */
    private double SCOP;
    /**
     * 常规能源供热能耗
     */
    private double conventionalEnergy;
    /**
     * 常规能源替代量
     */
    private double replaceEnergy;
    /**
     * CO2减排量
     */
    private double CO2;
    /**
     * SO2减排量
     */
    private double SO2;
    /**
     * 氮氧化物减排量
     */
    private double nitrogenOxides;
    /**
     * 颗粒物减排量
     */
    private double particulates;

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

    public double getTotalEnergyConsumption() {
        return totalEnergyConsumption;
    }

    public void setTotalEnergyConsumption(double totalEnergyConsumption) {
        this.totalEnergyConsumption = totalEnergyConsumption;
    }

    public double getSCOP() {
        return SCOP;
    }

    public void setSCOP(double SCOP) {
        this.SCOP = SCOP;
    }

    public double getConventionalEnergy() {
        return conventionalEnergy;
    }

    public void setConventionalEnergy(double conventionalEnergy) {
        this.conventionalEnergy = conventionalEnergy;
    }

    public double getReplaceEnergy() {
        return replaceEnergy;
    }

    public void setReplaceEnergy(double replaceEnergy) {
        this.replaceEnergy = replaceEnergy;
    }

    public double getCO2() {
        return CO2;
    }

    public void setCO2(double CO2) {
        this.CO2 = CO2;
    }

    public double getSO2() {
        return SO2;
    }

    public void setSO2(double SO2) {
        this.SO2 = SO2;
    }

    public double getNitrogenOxides() {
        return nitrogenOxides;
    }

    public void setNitrogenOxides(double nitrogenOxides) {
        this.nitrogenOxides = nitrogenOxides;
    }

    public double getParticulates() {
        return particulates;
    }

    public void setParticulates(double particulates) {
        this.particulates = particulates;
    }
}
