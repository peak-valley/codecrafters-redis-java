package com.zyf.Constant;

public interface Constants {
    String _R_N = "\r\n";
    byte[] _R_N_BYTES = _R_N.getBytes();
    // com.zyf.cluster
    String MASTER_HOST = "MASTER_HOST";
    String MASTER_PORT = "MASTER_PORT";
    String EMPTY_RDB_BASE64 = "UkVESVMwMDEx+glyZWRpcy12ZXIFNy4yLjD6CnJlZGlzLWJpdHPAQPoFY3RpbWXCbQi8ZfoIdXNlZC1tZW3CsMQQAPoIYW9mLWJhc2XAAP/wbjv+wP9aog==";
    // data
    String NULL_BULK_STRING = "$-1" + _R_N;
    byte[] NULL_BULK_STRING_BYTES = NULL_BULK_STRING.getBytes();
    // command
    String GET = "GET";
    String PING = "PING";
    String SET = "SET";
    String ECHO = "ECHO";
    String INFO = "INFO";
    String REPLCONF = "REPLCONF";
    String PSYNC = "PSYNC";
    String FULLRESYNC = "FULLRESYNC";
    String WAIT = "WAIT";
}
