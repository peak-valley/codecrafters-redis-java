package com.zyf.commands;

import java.util.List;

public interface Command {
    byte[] execute(List<Object> content);

}
