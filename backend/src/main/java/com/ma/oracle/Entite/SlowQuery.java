package com.ma.oracle.Entite;

import lombok.Data;

@Data
public class SlowQuery {
    private String sqlId;
    private String sqlText;
    private long elapsedTime;

    public SlowQuery(String sqlId, String sqlText, long elapsedTime) {
        this.sqlId = sqlId;
        this.sqlText = sqlText;
        this.elapsedTime = elapsedTime;
    }

}
