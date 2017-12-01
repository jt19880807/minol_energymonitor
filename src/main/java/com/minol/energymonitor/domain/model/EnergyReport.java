package com.minol.energymonitor.domain.model;

public class EnergyReport {
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目地址
     */
    private String projectAddress;
    /**
     * 制热面积
     */
    private double heating_area;
    /**
     * 建筑类型
     */
    private String building_type;
    /**
     * 建筑年限
     */
    private int building_years;
    /**
     * 住户数量
     */
    private int house_count;
    /**
     * 评估时间
     */
    private String reportTime;
    /**
     * 平均温度
     */
    private double averageTemp;
    /**
     * 平均温度达标天数
     */
    private int standardDays;
    /**
     * 平均温度不达标天数
     */
    private int noStandardDays;
    /**
     * 耗热量
     */
    private double heat;
    /**\
     * 耗电量
     */
    private double powerConsumption;
    /**
     * SCOP
     */
    private double SCOP;
    /**
     * 评估结果
     */
    private String  reportResult;
    /**
     * 能耗效益
     */
    private  EnergyEfficiency energyEfficiency;
    /**
     * 环境效益
     */
    private EnvironmentalBenefits environmentalBenefits;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public double getHeating_area() {
        return heating_area;
    }

    public void setHeating_area(double heating_area) {
        this.heating_area = heating_area;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }

    public int getBuilding_years() {
        return building_years;
    }

    public void setBuilding_years(int building_years) {
        this.building_years = building_years;
    }

    public int getHouse_count() {
        return house_count;
    }

    public void setHouse_count(int house_count) {
        this.house_count = house_count;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public double getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(double averageTemp) {
        this.averageTemp = averageTemp;
    }

    public int getStandardDays() {
        return standardDays;
    }

    public void setStandardDays(int standardDays) {
        this.standardDays = standardDays;
    }

    public int getNoStandardDays() {
        return noStandardDays;
    }

    public void setNoStandardDays(int noStandardDays) {
        this.noStandardDays = noStandardDays;
    }

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

    public double getSCOP() {
        return SCOP;
    }

    public void setSCOP(double SCOP) {
        this.SCOP = SCOP;
    }

    public String getReportResult() {
        return reportResult;
    }

    public void setReportResult(String reportResult) {
        this.reportResult = reportResult;
    }

    public EnergyEfficiency getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(EnergyEfficiency energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }

    public EnvironmentalBenefits getEnvironmentalBenefits() {
        return environmentalBenefits;
    }

    public void setEnvironmentalBenefits(EnvironmentalBenefits environmentalBenefits) {
        this.environmentalBenefits = environmentalBenefits;
    }
}
