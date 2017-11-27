package com.minol.energymonitor.domain.entity;

public class HeatMeterReading extends BaseReadingEntity {
    /**
     * 瞬时流量
     */
    private double instantaneousflow;
    /**
     * 功率
     */
    private double power;
    /**
     * 累计热量
     */
    private double accumulationheat;
    /**
     * 累计冷量
     */
    private double accumulationcooling;
    /**
     * 供水温度
     */
    private double supplywatertemp;
    /**
     * 回水温度
     */
    private double returnwatertemp;

    private Meter meter;


    public double getInstantaneousflow() {
        return instantaneousflow;
    }

    public void setInstantaneousflow(double instantaneousflow) {
        this.instantaneousflow = instantaneousflow;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getAccumulationheat() {
        return accumulationheat;
    }

    public void setAccumulationheat(double accumulationheat) {
        this.accumulationheat = accumulationheat;
    }

    public double getAccumulationcooling() {
        return accumulationcooling;
    }

    public void setAccumulationcooling(double accumulationcooling) {
        this.accumulationcooling = accumulationcooling;
    }

    public double getSupplywatertemp() {
        return supplywatertemp;
    }

    public void setSupplywatertemp(double supplywatertemp) {
        this.supplywatertemp = supplywatertemp;
    }

    public double getReturnwatertemp() {
        return returnwatertemp;
    }

    public void setReturnwatertemp(double returnwatertemp) {
        this.returnwatertemp = returnwatertemp;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }
}
