package com.zyf.commands;

import com.zyf.collect.RedisRepository;
import com.zyf.stream.StreamData;

import java.util.*;

public class Xadd extends AbstractCommand {

    private static final String ERROR_MESSAGE_MINI_ENTRY_ID = "ERR The ID specified in XADD must be greater than 0-0";
    private static final String ERROR_MESSAGE_EQUAL_OR_SMALLER_TOP_ID = "ERR The ID specified in XADD is equal or smaller than the target stream top item";


    @Override
    public byte[] execute(List<Object> content) {
        String key = new String((byte[]) content.get(1));
        String streamId = new String((byte[]) content.get(2));


        if (chickIsMiniId(streamId)) {
            return buildSimpleErrResponse(ERROR_MESSAGE_MINI_ENTRY_ID);
        }

        TreeSet<StreamData> streamTreeSet = RedisRepository.getStream(key);
        StreamData data;
        StreamData lastStreamData = null;
        if (streamTreeSet == null) {
            streamTreeSet = new TreeSet<>(StreamData::compareTo);
        }
        if (streamTreeSet.isEmpty()) {
            data = StreamData.builder().stream(streamId, -1).build();
        } else {
            lastStreamData = streamTreeSet.getLast();
            data = StreamData.builder().stream(streamId, lastStreamData.getSequenceNumber()).build();
        }
        if (checkIsErrorId(data, lastStreamData)) {
            return buildSimpleErrResponse(ERROR_MESSAGE_EQUAL_OR_SMALLER_TOP_ID);
        }

        HashMap<String, String> entryFields = new HashMap<>();
        for (int i = 3; i < content.size(); i+=2) {
            entryFields.put(new String((byte[]) content.get(i)), new String((byte[]) content.get(i + 1)));
        }

        data.setEntryFields(entryFields);
        streamTreeSet.add(data);

        RedisRepository.putStream(key, streamTreeSet);
        return buildBulkResponse(data.getStreamId());
    }
    private boolean chickIsMiniId(String streamId) {
        String[] split = streamId.split("-");
        if ("0".equals(split[0]) && "0".equals(split[1])) {
            return true;
        }
        return false;
    }
    private boolean checkIsErrorId(StreamData add, StreamData lastStreamData) {
        if (lastStreamData == null) {
            return false;
        }
        return add.compareTo(lastStreamData) <= 0;
    }
}
