package com.minol.energymonitor.domain.entity;

public class Area extends BaseEntity {

    /**
     * 小区称
     */
    private String name;
    /**
     * 所属地区
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
