package com.guyue.pms.util;

import org.springframework.stereotype.Component;

@Component
public class IDUtil {
    private long sequence=0;

    private long lastTimestamp = -1L;

    // 每次都会产生一个唯一的序列号
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (lastTimestamp == timestamp) {
            long sequenceMask = -1L ^ (-1L << 12);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - 1585644268888L) << 12) |
                sequence;
    }

    /**
     * 当某一毫秒的时间，产生的id数 超过4095，系统会进入等待，直到下一毫秒，系统继续产生ID
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {

        long timestamp = System.currentTimeMillis();

        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
