package com.zyf;

import com.zyf.Constant.Constants;
import com.zyf.Constant.CommandEnum;
import com.zyf.collect.RedisRepository;
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
        commandCache.put(CommandEnum.KEYS.getName(), new Keys());
        commandCache.put(CommandEnum.PING.getName(), new Ping());
        commandCache.put(CommandEnum.INFO.getName(), new Info());
        commandCache.put(CommandEnum.REPLCONF.getName(), new ReplConf());
        commandCache.put(CommandEnum.PSYNC.getName(), new Psync());
        commandCache.put(CommandEnum.FULLRESYNC.getName(), new FullResync());
        commandCache.put(CommandEnum.WAIT.getName(), new Wait());
        commandCache.put(CommandEnum.CONFIG.getName(), new Config());
        commandCache.put(CommandEnum.TYPE.getName(), new Type());
        // stream
        commandCache.put(CommandEnum.XADD.getName(), new Xadd());
        commandCache.put(CommandEnum.XRANGE.getName(), new XRange());
        commandCache.put(CommandEnum.XREAD.getName(), new XRead());
        //Transactions
        commandCache.put(CommandEnum.INCR.getName(), new Increment());
        commandCache.put(CommandEnum.MULTI.getName(), new Multi());

    }

    public byte[] execute(String command, List<Object> content) {
        final Command c = commandCache.get(command);
        if (c == null) {
            System.out.println("command " + command + " is not exist");
            return null;
        }
        if(RedisRepository.multiStateOpen()) {
            RedisRepository.multiOffer(content);
            return buildSimpleStrResponse("OK");
        }
        System.out.println(command + " command is running");
        return c.execute(content);
    }

    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }
}
