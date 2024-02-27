package com.zyf.handle;

import com.zyf.Constant.CommandEnum;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Handler extends AbstractHandler {

    public Handler(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void afterExecuting(CommandEnum commandEnum, OutputStream outputStream, Object data, byte[] response) {
        // nothing
    }

    @Override
    public void reply(CommandEnum commandEnum, OutputStream outputStream, byte[] response) throws IOException {
        outputStream.write(response);
    }

    @Override
    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }
}
