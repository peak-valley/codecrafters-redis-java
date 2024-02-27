package com.zyf.handle;

import com.zyf.Constant.CommandEnum;

import java.io.IOException;
import java.io.OutputStream;

public interface IHandler {
    void handle();

    void reply(CommandEnum commandEnum, OutputStream outputStream, Object sendData, byte[] response) throws IOException;
}
