package com.minol.energymonitor.domain.model;

public class EnergyEfficiency {
    /**
     * 总能耗
     */
    private double totalEnergyConsumption;
    /**
     * 常规能源供热能耗
     */
    private double conventionalEnergy;
    /**
     * 常规能源替代量
     */
    private double replaceEnergy;

    public double getTotalEnergyConsumption() {
        return totalEnergyConsumption;
    }

    public void setTotalEnergyConsumption(double totalEnergyConsumption) {
        this.totalEnergyConsumption = totalEnergyConsumption;
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
}
