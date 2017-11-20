package com.minol.energymonitor.domain.entity;

public class Building extends BaseEntity {
    /**
     * 楼栋名称（编号）
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
    private int building_years;
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
    private String building_type;
    /**
     * 项目信息
     */
    private Project project;
    /**
     * 小区信息
     */
    private Area area;

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

    public int getBuilding_years() {
        return building_years;
    }

    public void setBuilding_years(int building_years) {
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
        return building_type;
    }

    public void setBuliding_type(String building_type) {
        this.building_type = building_type;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
