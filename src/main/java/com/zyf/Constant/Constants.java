package com.zyf.Constant;

public interface Constants {
    // com.zyf.cluster
    String MASTER_HOST = "MASTER_HOST";
    String MASTER_PORT = "MASTER_PORT";
    // data
    String NULL_BULK_STRING = "$-1\r\n";
    byte[] NULL_BULK_STRING_BYTES = NULL_BULK_STRING.getBytes();
    // command
    String GET = "GET";
    String PING = "PING";
    String SET = "SET";
    String ECHO = "ECHO";
    String INFO = "INFO";
    String REPLCONF = "REPLCONF";
}
