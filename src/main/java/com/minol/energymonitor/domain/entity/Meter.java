package com.minol.energymonitor.domain.entity;

public class Meter extends BaseEntity {
    /**
     * 表号
     */
    private String number;
    /**
     * 采集器ID
     */
    private int collector_id;
    /**
     * 表计类型 1：热量表，2：电量表 3：测温设备
     */
    private int meter_type;
    /**
     * 设备厂家
     */
    private String factory;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 安装位置 室内/室外
     */
    private String position;
    /**
     * 安装房间号
     */
    private String doorNum;

    private Collector collector;

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCollector_id() {
        return collector_id;
    }

    public void setCollector_id(int collector_id) {
        this.collector_id = collector_id;
    }

    public int getMeter_type() {
        return meter_type;
    }

    public void setMeter_type(int meter_type) {
        this.meter_type = meter_type;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDoorNum() {
        return doorNum;
    }

    public void setDoorNum(String doorNum) {
        this.doorNum = doorNum;
    }
}
