package ru.croc.project.statistics;

public enum StatType {
    Integer("INT"),
    String("VARCHAR(255)");

    private final String sqlType;
    StatType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlType() {
        return sqlType;
    }
}
