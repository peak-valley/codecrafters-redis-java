package com.zyf.handle;

import com.zyf.CommandFactory;
import com.zyf.Constant.CommandEnum;
import com.zyf.Constant.Constants;
import com.zyf.Protocol;
import com.zyf.cluster.Master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.List;

public class Handler extends AbstractHandler {

    CommandFactory commandFactory = new CommandFactory();

    public Handler(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void reply(CommandEnum commandEnum, OutputStream outputStream, byte[] response) throws IOException {
        outputStream.write(response);
    }

    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }
}
