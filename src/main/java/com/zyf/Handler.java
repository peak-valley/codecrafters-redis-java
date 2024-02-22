package com.zyf;

import com.zyf.Constant.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.List;

public class Handler {


    private final Socket clientSocket;
    CommandFactory commandFactory = new CommandFactory();

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

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
                    final String command = new String(bytes);
                    c = command;
                    response = commandFactory.execute(command.toUpperCase(), content);
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
                outputStream.write(response);
                if (c.equals(Constants.PSYNC)) {
                    System.out.println("send empty RDB");
                    byte[] decode = Base64.getDecoder().decode(Constants.EMPTY_RDB_BASE64);
                    String prefix = "$" + decode.length + "/r/n";

                    String s = prefix + new String(decode);
                    System.out.println(s);
//                    Base64
//                    outputStream.write(bytes);
                    outputStream.write(s.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] buildBulkResponse(String content) {
        int length = content.length();
        StringBuilder sb = new StringBuilder();
        String ret = "$" + length + "\r\n" + content + "\r\n";
        System.out.println("build response " + ret);
//        byte[] ret = new byte[]
//        ret.add((byte) '+');
        return ret.getBytes();
    }

    public byte[] buildSimpleStrResponse(String content) {
        return ("+" + content + "\r\n").getBytes();
    }
}
