package com.zyf.commands;

import com.zyf.collect.RedisRepository;
import com.zyf.stream.StreamData;

import java.util.*;

import static com.zyf.Constant.Constants._R_N;

public class XRange extends AbstractCommand {
    @Override
    public byte[] execute(List<Object> content) {
        String key = convertByteToString(content.get(1));
        String start = convertByteToString(content.get(2));
        String end = convertByteToString(content.get(3));


        long startTimeMillSeconds;
        long endTimeMillSeconds;
        long startSequenceNumber = 0L;
        long endSequenceNumber = Long.MAX_VALUE;

        if (start.equals("-")) {
            StreamData streamData = RedisRepository.firstStream();
            if (streamData == null) {
                return buildArraysResponse(Collections.emptyList());
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
                return buildArraysResponse(Collections.emptyList());
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
            return buildArraysResponse(Collections.emptyList());
        }
        NavigableSet<StreamData> streamDataSet = stream.subSet(startStreamData, true, endStreamData, true);

        return buildStreamDataSet(streamDataSet);
    }

    /*
    看，这里有三层数组，第一层为 stream id 的个数，第二层数组是固定的为两个（stream id + entry list），第三层数组为entry的个数乘以2，entry为kv结构，所以需要乘以2
1) 1) "1711157304746-0"
   2) 1) "name"
      2) "Virginia"
      3) "surname"
      4) "Woolf"
2) 1) "1711157304746-1"
   2) 1) "name"
      2) "Jane"
      3) "surname"
      4) "Austen"
3) 1) "1711157304746-2"
   2) 1) "name"
      2) "Toni"
      3) "surname"
      4) "Morrison"
4) 1) "1711157304747-0"
   2) 1) "name"
      2) "Agatha"
      3) "surname"
      4) "Christie"
5) 1) "1711157304747-1"
   2) 1) "name"
      2) "Ngozi"
      3) "surname"
      4) "Adichie"
6) 1) "1711157304747-2"
   2) 1) "name"
      2) "Ngozi"
      3) "surname"
      4) "Adichie"

     */
    private byte[] buildStreamDataSet(NavigableSet<StreamData> data) {
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
        return command.toString().getBytes();
    }




}
