package com.zyf.commands;

import com.zyf.Constant.Constants;
import com.zyf.collect.RedisRepository;
import com.zyf.stream.StreamData;

import java.lang.constant.Constable;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.zyf.Constant.Constants._R_N;

public class XRead extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        long blockMillSenconds = 0;
        int keySize;
        int i = 0;
        List<String> paramKeys = new ArrayList<>();
        if ("block".equalsIgnoreCase(convertByteToString(content.get(1)))) {
            blockMillSenconds = Long.parseLong(convertByteToString(content.get(2)));
            keySize = (content.size() - 4) / 2;
        } else {
            keySize = (content.size() - 2) / 2;
        }
        Map<String, String> data = readData(content, i, keySize, paramKeys, blockMillSenconds > 0);

        boolean isNull = checkIsNull(data);
        if (!isNull) {
            return buildXReadRet(data, paramKeys);
        } else if (blockMillSenconds == 0) {
            return Constants.NULL_BULK_STRING_BYTES;
        }
        Instant start = Instant.now();
        while (Duration.between(start, Instant.now()).toMillis() < blockMillSenconds) {
            try {Thread.sleep(50);} catch (InterruptedException ignored) {}
            data = readData(content, 0, keySize, paramKeys, blockMillSenconds > 0);
            if (!checkIsNull(data)) {
                return buildXReadRet(data, paramKeys);
            }
        }
        System.out.println("time out XRead,data is null");
        return Constants.NULL_BULK_STRING_BYTES;
    }

    private Map<String, String> readData(List<Object> content, int i, int keySize, List<String> paramKeys, boolean block) {
        Map<String, String> ret = new HashMap<>();
        for (; i < keySize; i++) {
            String key;
            String streamId;
            if (block) {
                key = convertByteToString(content.get(i + 4));
                streamId = convertByteToString(content.get(i + 4 + keySize));
            } else {
                key = convertByteToString(content.get(i + 2));
                streamId = convertByteToString(content.get(i + 2 + keySize));
            }
            String start, end = "+";
            if ("0".equals(streamId) || "0-0".equals(streamId)) {
                start = "0";
            } else {
                start = streamId;
            }
            if (!paramKeys.contains(key)) {
                paramKeys.add(key);
            }
            ret.put(key, select(start, end, key));
        }
        return ret;
    }

    private byte[] buildXReadRet(Map<String, String> data, List<String> paramKeys) {
        StringBuilder sb = new StringBuilder("*" + data.size() + _R_N);
        for (String s : paramKeys) {
            sb.append("*2" + _R_N + "$").append(s.length()).append(_R_N).append(s).append(_R_N).append(data.get(s));
        }
        return sb.toString().getBytes();
    }

    private String select(String start, String end, String key) {
        long startTimeMillSeconds;
        long endTimeMillSeconds;
        long startSequenceNumber = 0L;
        long endSequenceNumber = Long.MAX_VALUE;

        if (start.equals("-")) {
            StreamData streamData = RedisRepository.firstStream();
            if (streamData == null) {
                return buildArrayString(Collections.emptyList());
            }
            startTimeMillSeconds = streamData.getTimeMillSeconds();
            startSequenceNumber = streamData.getSequenceNumber();
        }else if (start.contains("-")) {
            String[] split = start.split("-");
            startTimeMillSeconds = Long.parseLong(split[0]);
            startSequenceNumber = Long.parseLong(split[1]);
        } else {
            startTimeMillSeconds = Long.parseLong(start);
        }

        if (end.equals("+")) {
            StreamData streamData = RedisRepository.lastStream();
            if (streamData == null) {
                return buildArrayString(Collections.emptyList());
            }
            endTimeMillSeconds = streamData.getTimeMillSeconds();
            endSequenceNumber = streamData.getSequenceNumber();
        } else if (end.contains("-")) {
            String[] split = end.split("-");
            endTimeMillSeconds = Long.parseLong(split[0]);
            endSequenceNumber = Long.parseLong(split[1]);
        } else {
            endTimeMillSeconds = Long.parseLong(end);
        }
        StreamData startStreamData = StreamData.builder().stream(startTimeMillSeconds + "-" + startSequenceNumber).build();
        StreamData endStreamData = StreamData.builder().stream(endTimeMillSeconds + "-" + endSequenceNumber).build();
        TreeSet<StreamData> stream = RedisRepository.getStream(key);
        if (stream == null || stream.isEmpty()) {
            return buildArrayString(Collections.emptyList());
        }
        if (startStreamData.compareTo(endStreamData) > 0) {
            return buildArrayString(Collections.emptyList());
        }
        NavigableSet<StreamData> streamDataSet = stream.subSet(startStreamData, false, endStreamData, true);

        String ret = buildStreamDataSet(streamDataSet);
        return ret;
    }

    private boolean checkIsNull(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return true;
        }
        boolean isNull = true;
        for (String k : data.keySet()) {
            if (!data.get(k).equals("*0" + _R_N)) {
                isNull = false;
            }
        }
        return isNull;
    }

    private String buildStreamDataSet(NavigableSet<StreamData> data) {
        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (StreamData streamData : data) {
            command.append("*2" + _R_N);
            // stream id
            String streamId = streamData.getTimeMillSeconds() + "-" + streamData.getSequenceNumber();
            command.append("$").append(streamId.length()).append(_R_N).append(streamId).append(_R_N);
            // entry
            Map<String, String> entryFields = streamData.getEntryFields();
            int size = entryFields.size();
            command.append("*").append(size * 2).append(_R_N);
            for (String key : entryFields.keySet()) {
                command.append("$").append(key.length()).append(_R_N).append(key).append(_R_N);
                String value = entryFields.get(key);
                command.append("$").append(value.length()).append(_R_N).append(value).append(_R_N);
            }
        }
        return command.toString();
    }

    public String buildArrayString(List<Object> data) {

        StringBuilder command = new StringBuilder("*" + data.size() + _R_N);
        for (Object o : data) {
            if (o instanceof String) {
                String o1 = (String) o;
                command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
            }else {
                String o1 = new String((byte[]) o);
                command.append("$").append(o1.length()).append(_R_N).append(o1).append(_R_N);
            }
        }
        return command.toString();
    }
}
