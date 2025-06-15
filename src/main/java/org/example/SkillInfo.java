package org.example;

public class SkillInfo {
    private String id;
    private String skillName;
    private Integer consumption; // Integer型にして、値がnullの場合に対応
    private Double temperature; // 温度を保持するためのフィールド

    public SkillInfo(String id, String skillName, Integer consumption, Double temperature) {
        this.id = id;
        this.skillName = skillName;
        this.consumption = consumption;
        this.temperature = temperature;
    }

    public String getId() {
        return id;
    }

    public String getSkillName() {
        return skillName;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public Double getTemperature() {
        return temperature;
    }
}
