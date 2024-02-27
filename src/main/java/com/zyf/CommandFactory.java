package com.zyf;

import com.zyf.Constant.Constants;
import com.zyf.Constant.CommandEnum;
import com.zyf.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandFactory {
    Map<String, Command> commandCache = new HashMap<>();

    public CommandFactory() {
        commandCache.put(CommandEnum.GET.getName(), new Get());
        commandCache.put(CommandEnum.SET.getName(), new Set());
        commandCache.put(CommandEnum.ECHO.getName(), new Echo());
        commandCache.put(CommandEnum.PING.getName(), new Ping());
        commandCache.put(CommandEnum.INFO.getName(), new Info());
        commandCache.put(CommandEnum.REPLCONF.getName(), new ReplConf());
        commandCache.put(CommandEnum.PSYNC.getName(), new Psync());
        commandCache.put(CommandEnum.FULLRESYNC.getName(), new FullResync());
        commandCache.put(CommandEnum.WAIT.getName(), new Wait());
        commandCache.put(CommandEnum.CONFIG.getName(), new CONFIG());
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
