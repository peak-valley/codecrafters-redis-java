package com.zyf.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class StreamData implements Comparable<StreamData> {
    private long timeMillSeconds;
    private long sequenceNumber;
    Map<String, String> entryFields;

    @Override
    public int compareTo(StreamData o) {
        if (this.timeMillSeconds > o.getTimeMillSeconds()) {
            return 1;
        } else if (this.timeMillSeconds < o.getTimeMillSeconds()) {
            return -1;
        } else if (this.sequenceNumber > o.sequenceNumber) {
            return 1;
        } else if (this.sequenceNumber < o.sequenceNumber) {
            return -1;
        }
        return 0;
    }

    public static class StreamDataBuilder {
        private long timeMillSeconds;
        private long sequenceNumber;
        private Map<String, String> entryFields;

        StreamDataBuilder() {
        }

        public StreamDataBuilder stream(String id) {
            String[] split = id.split("-");
            this.timeMillSeconds = Long.parseLong(split[0]);
            this.sequenceNumber = Long.parseLong(split[1]);
            return this;
        }

        public StreamDataBuilder stream(String id, long maxSequenceNumber) {
            String[] split = id.split("-");
            this.timeMillSeconds = Long.parseLong(split[0]);
            String sequenceString = split[1];
            if ("*".equals(sequenceString)) {
                this.sequenceNumber = ++maxSequenceNumber;
            } else {
                this.sequenceNumber = Long.parseLong(sequenceString);
            }
            return this;
        }

        public StreamDataBuilder entryFields(Map<String, String> entryFields) {
            this.entryFields = entryFields;
            return this;
        }

        public StreamData build() {
            return new StreamData(this.timeMillSeconds, this.sequenceNumber, this.entryFields);
        }


        public String toString() {
            long var10000 = this.timeMillSeconds;
            return "StreamData.StreamDataBuilder(timeMillSeconds=" + var10000 + ", sequenceNumber=" + this.sequenceNumber + ", entryFields=" + this.entryFields + ")";
        }
    }
    public static StreamDataBuilder builder() {
        return new StreamDataBuilder();
    }

    public String getStreamId() {
        return getTimeMillSeconds() + "-" + getSequenceNumber();
    }
}
