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
        commandCache.put(Constants.PSYNC, new Psync());
        commandCache.put(Constants.FULLRESYNC, new FullResync());
        commandCache.put(Constants.WAIT, new Wait());
    }

    public byte[] execute(String command, List<Object> content) {
        final Command c = commandCache.get(command);
        if (c == null) {
            System.out.println("command " + command + " is not exist");
            return null;
        }
        System.out.println(command + " command is running");
        return c.execute(content);
    }
}
