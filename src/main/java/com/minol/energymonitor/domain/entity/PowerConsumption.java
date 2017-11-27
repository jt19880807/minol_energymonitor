package com.minol.energymonitor.domain.entity;

public class PowerConsumption extends BaseReadingEntity {
    /**
     * 日耗电量
     */
   private double consumption;

   private Meter meter;

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}
