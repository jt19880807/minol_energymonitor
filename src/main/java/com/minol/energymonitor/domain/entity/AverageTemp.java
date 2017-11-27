package com.minol.energymonitor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class AverageTemp {
    /**
     *主键ID
     */
    private int id;
    /**
     * 表计ID
     */
    private int project_id;
    /**
     * 读数日期
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Timestamp date;
    /**
     * 室内平均温度
     */
    private double indoor_averagetemp;
    /**
     * 室外平均温度
     */
    private double outdoor_averagetemp;
    /**
     * 室内最高温度
     */
    private double indoor_maxtemp;
    /**
     * 室内最低温度
     */
    private double indoor_mintemp;
    /**
     * 室内平均湿度
     */
    private double indoor_averagehumidity;

    private Project project;

    /**
     * 是否删除(0：未删除 1:删除)
     */
    private int isdeleted;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getIndoor_averagetemp() {
        return indoor_averagetemp;
    }

    public void setIndoor_averagetemp(double indoor_averagetemp) {
        this.indoor_averagetemp = indoor_averagetemp;
    }

    public double getOutdoor_averagetemp() {
        return outdoor_averagetemp;
    }

    public void setOutdoor_averagetemp(double outdoor_averagetemp) {
        this.outdoor_averagetemp = outdoor_averagetemp;
    }

    public double getIndoor_maxtemp() {
        return indoor_maxtemp;
    }

    public void setIndoor_maxtemp(double indoor_maxtemp) {
        this.indoor_maxtemp = indoor_maxtemp;
    }

    public double getIndoor_mintemp() {
        return indoor_mintemp;
    }

    public void setIndoor_mintemp(double indoor_mintemp) {
        this.indoor_mintemp = indoor_mintemp;
    }

    public double getIndoor_averagehumidity() {
        return indoor_averagehumidity;
    }

    public void setIndoor_averagehumidity(double indoor_averagehumidity) {
        this.indoor_averagehumidity = indoor_averagehumidity;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
