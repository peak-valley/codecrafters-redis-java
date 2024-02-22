package com.zyf.Constant;

public enum CommandEnum {
    GET("GET"),
    SET("SET"),
    ECHO("ECHO"),
    INFO("INFO"),
    REPLCONF("REPLCONF"),
    PSYNC("PSYNC"),

    ;
//
//    String PING = "PING";
//    String SET = "SET";
//    String ECHO = "ECHO";
//    String INFO = "INFO";
//    String REPLCONF = "REPLCONF";
//    String PSYNC = "PSYNC";


    CommandEnum(String name) {
        this.name = name;
    }

    String name;
    public void getName() {
        getName();
    }
    public void setName(String name) {
        this.name = name;
    }
}
