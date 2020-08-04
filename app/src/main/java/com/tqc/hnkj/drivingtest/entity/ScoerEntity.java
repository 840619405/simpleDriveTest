package com.tqc.hnkj.drivingtest.entity;

public class ScoerEntity {
     /*
    String sql2="create table test_succ(_id integer primary key autoincrement," +
                "succ_subject text," +
                "succ_time text," +
                "succ_score text," +
                "succ_qualifier text," +
                "succ_diration text)";
     */
     private String subject;
     private String time;
     private String qualifier;
     private String diration;
     private String score;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getDiration() {
        return diration;
    }

    public void setDiration(String diration) {
        this.diration = diration;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
