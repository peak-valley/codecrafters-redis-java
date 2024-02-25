package com.zyf.Constant;

import static com.zyf.Constant.CommandType.*;

public enum CommandEnum {
    GET("GET", READ),
    SET("SET", WRITE),
    ECHO("ECHO", READ),
    INFO("INFO", READ),
    REPLCONF("REPLCONF", OTHER),
    PSYNC("PSYNC", OTHER),

    ;
//
//    String PING = "PING";
//    String SET = "SET";
//    String ECHO = "ECHO";
//    String INFO = "INFO";
//    String REPLCONF = "REPLCONF";
//    String PSYNC = "PSYNC";


    CommandEnum(String name, CommandType type) {
        this.name = name;
        this.type = type;
    }

    String name;
    CommandType type;

    public void getName() {
        getName();
    }
    public void setName(String name) {
        this.name = name;
    }
}
