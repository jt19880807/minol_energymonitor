package com.minol.energymonitor.domain.entity;

public class AmMeterReading extends BaseReadingEntity {
    /**
     *电压
     */
    private double voltage;
    /**
     *电流
     */
    private double current;
    /**
     *有功功率
     */
    private double activepower;
    /**
     *无功功率
     */
    private double reactivepower;
    /**
     *视在功率
     */
    private double apparentpower;
    /**
     *功率因数
     */
    private double powerfactor;
    /**
     *频率
     */
    private double frequency;
    /**
     *有功电度
     */
    private double activeelectricity;

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getActivepower() {
        return activepower;
    }

    public void setActivepower(double activepower) {
        this.activepower = activepower;
    }

    public double getReactivepower() {
        return reactivepower;
    }

    public void setReactivepower(double reactivepower) {
        this.reactivepower = reactivepower;
    }

    public double getApparentpower() {
        return apparentpower;
    }

    public void setApparentpower(double apparentpower) {
        this.apparentpower = apparentpower;
    }

    public double getPowerfactor() {
        return powerfactor;
    }

    public void setPowerfactor(double powerfactor) {
        this.powerfactor = powerfactor;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getActiveelectricity() {
        return activeelectricity;
    }

    public void setActiveelectricity(double activeelectricity) {
        this.activeelectricity = activeelectricity;
    }
}
