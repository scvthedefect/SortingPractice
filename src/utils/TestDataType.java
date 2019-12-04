package utils;

import java.util.Arrays;
import java.util.Random;

public enum TestDataType {
    
    /**
     * 包含正负数，数据分布区间为[-128, 128)
     */
    NORMAL(0),
    
    /**
     * 仅正数，数据分布区间为[0, 128)
     */
    POSITIVE(1),
    
    /**
     * 仅负数，数据分布区间为[-128, 0)
     */
    NEGATIVE(-1),

    ;
    
    private int[] raw;
    
    private int[] sorted;
    
    private int len;
    
    private TestDataType(int type) {
        Random r = new Random();
        int max = 128, shift;
        switch (type) {
        case 1: // 仅正数
            shift = 0;
            break;
        case -1: // 仅负数
            shift = -128;
            break;
        default: // 含正负数
            max *= 2;
            shift = -128;
            break;
        }
        
        int least = 20; // 测试数组的长度
        len = least + 1; // 在测试数据的追加一个重复数据
        raw = new int[len];
        for(int i = 0; i < least; i++) {
            raw[i] = r.nextInt(max) + shift;
        }
        raw[least] = raw[0];
        
        sorted = Arrays.copyOf(raw, len);
        Arrays.sort(sorted);
    }

    public int[] getRaw() {
        return raw;
    }

    public int[] getSorted() {
        return sorted;
    }

    public int getLen() {
        return len;
    }
}
