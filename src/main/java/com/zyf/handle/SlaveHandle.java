package com.zyf.handle;

import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.CommandType;

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
        if (CommandType.WRITE.equals(commandEnum.getType())) {
            System.out.println("The slave's write command cancels the reply");
        } else {
            outputStream.write(response);
        }
    }
}
