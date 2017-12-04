package com.minol.energymonitor.domain.model;

import java.util.List;

public class ChartModal {
    private List<String> text;
    private List<Double> value;

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}
