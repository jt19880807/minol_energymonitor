package com.minol.energymonitor.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ImportModel {
    private Integer count;
    private List<String> results=new ArrayList<>();

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
