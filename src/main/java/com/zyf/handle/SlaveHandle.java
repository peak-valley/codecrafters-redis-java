package com.zyf.handle;

import com.zyf.Constant.CommandEnum;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SlaveHandle extends AbstractHandler {
    Handler handler;

    public SlaveHandle(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void reply(CommandEnum commandEnum, OutputStream outputStream, byte[] response) throws IOException {
        System.out.println("slave cancels reply");
    }
}
