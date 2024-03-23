package com.zyf.commands;

import com.zyf.collect.RedisRepository;
import com.zyf.stream.StreamData;

import java.util.*;
import java.util.Set;

import static com.zyf.Constant.Constants._R_N;

public class XRead extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        int keySize = (content.size() - 2) / 2;
        Map<String, String> ret = new HashMap<>();
        for (int i = 0; i < keySize; i++) {
            String key = convertByteToString(content.get(i + 2));
            String streamId = convertByteToString(content.get(i + 2 + keySize));
            String start, end = "+";
            if ("0".equals(streamId) || "0-0".equals(streamId)) {
                start = "0";
            } else {
                start = streamId;
            }
            ret.put(key, select(start, end, key));
        }

        return buildXReadRet(ret);
    }

    private byte[] buildXReadRet(Map<String, String> data) {
        StringBuilder sb = new StringBuilder("*" + data.size() + _R_N);
        Set<String> streamSet = data.keySet();
        for (String s : streamSet) {
            sb.append("*2" + _R_N + "$" + s.length() + _R_N + s + _R_N + data.get(s));
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
        NavigableSet<StreamData> streamDataSet = stream.subSet(startStreamData, true, endStreamData, true);

        return buildStreamDataSet(streamDataSet);
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
        System.out.println("build command ->" + command);
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
        System.out.println("build command ->" + command);
        return command.toString();
    }
}
