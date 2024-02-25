package com.zyf.Constant;

import static com.zyf.Constant.CommandType.*;

public enum CommandEnum {
    GET("GET", READ),
    SET("SET", WRITE),
    ECHO("ECHO", READ),
    INFO("INFO", READ),
    REPLCONF("REPLCONF", OTHER),
    PSYNC("PSYNC", OTHER),
    PING("PING", READ),
    FULLRESYNC("FULLRESYNC", OTHER),
    NULL("NULL", OTHER)

    ;

    CommandEnum(String name, CommandType type) {
        this.name = name;
        this.type = type;
    }

    String name;
    CommandType type;

    public String getName() {
        return name;
    }

    public CommandType getType() {
        return type;
    }
}
