package com.minol.energymonitor.domain.entity;

public class Building extends BaseEntity {
    /**
     * 楼栋名称
     */
    private String name;
    /**
     * 项目ID
     */
    private int project_id;
    /**
     * 小区ID
     */
    private int area_id;
    /**
     * 建筑年限
     */
    private String building_years;
    /**
     * 建筑高度
     */
    private double building_height;
    /**
     * 户数
     */
    private int house_count;
    /**
     * 制热面积
     */
    private double heating_area;
    /**
     * 建筑性质
     */
    private String buliding_type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getBuilding_years() {
        return building_years;
    }

    public void setBuilding_years(String building_years) {
        this.building_years = building_years;
    }

    public double getBuilding_height() {
        return building_height;
    }

    public void setBuilding_height(double building_height) {
        this.building_height = building_height;
    }

    public int getHouse_count() {
        return house_count;
    }

    public void setHouse_count(int house_count) {
        this.house_count = house_count;
    }

    public double getHeating_area() {
        return heating_area;
    }

    public void setHeating_area(double heating_area) {
        this.heating_area = heating_area;
    }

    public String getBuliding_type() {
        return buliding_type;
    }

    public void setBuliding_type(String buliding_type) {
        this.buliding_type = buliding_type;
    }
}
