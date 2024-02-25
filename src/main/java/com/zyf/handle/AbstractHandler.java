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

public abstract class AbstractHandler implements IHandler{

    private final Socket clientSocket;
    CommandFactory commandFactory = new CommandFactory();

    public AbstractHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void handle() {
        try(
                InputStream inputStream = clientSocket.getInputStream();
                final OutputStream outputStream = clientSocket.getOutputStream()
        ) {
            byte[] bs = new byte[1024];
            int read;
            String c = "";
            while (true) {
                final Object data = Protocol.process(inputStream);
                byte[] response = null;
                if (data instanceof byte[]) {
                    final byte[] bytes = (byte[]) data;
                    final String content = new String(bytes);
                    if ("PING".equalsIgnoreCase(content.toUpperCase())) {
                        System.out.println("ping data reception");
                        response = buildSimpleStrResponse("PONG");
                    }
                } else if (data instanceof List) {
                    final List<Object> content = (List<Object>) data;
                    byte[] bytes = (byte[]) content.get(0);
                    final String command = new String(bytes).toUpperCase();
                    c = command;
                    response = commandFactory.execute(command, content);
                    if (Master.getMaster() != null) {
                        Master.getMaster().send(content);
                    }
                } else if (data instanceof String content){
                    String[] strings = content.split(" ");
                    c = strings[0].toUpperCase();
                    response = commandFactory.execute(c, List.of(strings));
                } else {
                    System.out.println("error data type");
                }
                if (response == null) {
                    System.out.printf("respose is null");
                    continue;
                }

                reply(CommandEnum.valueOf(c), outputStream, response);

                if (c.equals(Constants.PSYNC)) {
                    System.out.println("send empty RDB");
//                    sendRDBFile(outputStream);
                    byte[] decodeDB = Base64.getDecoder().decode(Constants.EMPTY_RDB_BASE64);
                    String prefix = "$" + decodeDB.length + "\r\n";

                    outputStream.write(prefix.getBytes());
                    outputStream.write(decodeDB);
                    if (Master.getMaster() != null) {
                        Master.getMaster().addSlave(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort(),
                                outputStream);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }
}
