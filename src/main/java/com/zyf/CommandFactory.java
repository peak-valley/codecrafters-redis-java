package com.zyf;

import com.zyf.Constant.Constants;
import com.zyf.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandFactory {
    Map<String, Command> commandCache = new HashMap<>();

    public CommandFactory() {
        commandCache.put(Constants.GET, new Get());
        commandCache.put(Constants.SET, new Set());
        commandCache.put(Constants.ECHO, new Echo());
        commandCache.put(Constants.PING, new Ping());
        commandCache.put(Constants.INFO, new Info());
        commandCache.put(Constants.REPLCONF, new ReplConf());
//        commandCache.put(com.zyf.Constant.Constants.PING, new com.zyf.commands.Get());
    }

    public byte[] execute(String command, List<Object> content) {
        final Command c = commandCache.get(command);
        if (c == null) {
            System.out.printf("command " + command + " is not exist");
            throw new IllegalArgumentException("command " + command + " is not exist");
        }
        System.out.println(command + " command is running");
        return c.execute(content);
    }
}
