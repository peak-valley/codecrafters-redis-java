package com.zyf;

import com.zyf.Constant.Constants;
import com.zyf.cluster.Master;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.List;

public class Handler {


    private final Socket clientSocket;
    CommandFactory commandFactory = new CommandFactory();

    Master master = new Master();

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
                    final String command = new String(bytes).toUpperCase();
                    c = command;
                    response = commandFactory.execute(command, content);
                    master.send(content);
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
//                    sendRDBFile(outputStream);
                    byte[] decodeDB = Base64.getDecoder().decode(Constants.EMPTY_RDB_BASE64);
                    String prefix = "$" + decodeDB.length + "\r\n";

                    outputStream.write(prefix.getBytes());
                    outputStream.write(decodeDB);
                    master.addSlave(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort(), outputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRDBFile(OutputStream outputStream){
        byte[] decoded_db = Base64.getDecoder().decode(Constants.EMPTY_RDB_BASE64);
        String prefix = "$" + decoded_db.length + "\r\n";
        byte[] prefixBytes = prefix.getBytes();
        try {
            outputStream.write(prefixBytes);
            outputStream.write(decoded_db);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
