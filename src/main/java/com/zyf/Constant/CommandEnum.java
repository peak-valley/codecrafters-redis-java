package com.zyf.Constant;

import static com.zyf.Constant.CommandType.*;

public enum CommandEnum {
    GET("GET", READ),
    SET("SET", WRITE),
    KEYS("KEYS", READ),
    ECHO("ECHO", READ),
    INFO("INFO", READ),
    REPLCONF("REPLCONF", OTHER),
    PSYNC("PSYNC", OTHER),
    PING("PING", READ),
    FULLRESYNC("FULLRESYNC", OTHER),
    WAIT("WAIT", OTHER),
    CONFIG("CONFIG", OTHER),
    NULL("NULL", OTHER)

    ;

    public static CommandEnum getCommand(String command) {
        for (CommandEnum value : values()) {
            if (value.name.equals(command.toUpperCase())) {
                return value;
            }
        }
        return NULL;
    }

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
