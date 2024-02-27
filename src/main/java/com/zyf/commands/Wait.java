package com.zyf.commands;

import com.zyf.cluster.Master;

import java.util.Collections;
import java.util.List;

public class Wait extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        int i = Master.getMaster().slaveSize();
        return buildIntegerResponse(i);
    }
}
