package com.zyf.commands;

import java.util.List;

public class Wait extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        return "0".getBytes();
    }
}
