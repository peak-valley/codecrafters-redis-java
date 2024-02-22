package com.zyf;

import com.zyf.Constant.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
                outputStream.flush();
                if (c.equals(Constants.PSYNC)) {
                    outputStream.write(
                            "524544495330303131fa0972656469732d76657205372e322e30fa0a72656469732d62697473c040fa056374696d65c26d08bc65fa08757365642d6d656dc2b0c41000fa08616f662d62617365c000fff06e3bfec0ff5aa2".getBytes());
                    outputStream.flush();

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
