package com.minol.energymonitor.domain.model;

public class EnvironmentalBenefits {
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
