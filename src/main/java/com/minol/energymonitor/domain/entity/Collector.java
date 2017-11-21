package com.minol.energymonitor.domain.entity;

public class Collector extends BaseEntity {

    /**
     * 采集器编号
     */
    private String number;
    /**
     * 楼栋编号
     */
    private int building_id;
    /**
     * 当前程序版本号
     */
    private int current_version;
    /**
     * 最新程序版本号
     */
    private int last_version;

    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public int getCurrent_version() {
        return current_version;
    }

    public void setCurrent_version(int current_version) {
        this.current_version = current_version;
    }

    public int getLast_version() {
        return last_version;
    }

    public void setLast_version(int last_version) {
        this.last_version = last_version;
    }
}
