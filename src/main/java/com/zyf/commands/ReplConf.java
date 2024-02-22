package com.zyf.commands;

import java.util.List;

public class ReplConf extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {

        return buildSimpleStrResponse("OK");
    }
}
