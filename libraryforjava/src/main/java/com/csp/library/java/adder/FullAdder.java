package com.csp.library.java.adder;

import java.util.Arrays;

/**
 * 全加器
 * 用 0x00 - 0xFF 表示 n 进制的位数值
 * 例：10 进制的 100，表示为 0x 01 00 00
 * <p>
 * 注
 */
public class FullAdder {
    private final long STATUS_NONE = 0x00L; // 正常，不进位
    private final long STATUS_CARRY = 0x0100000000000000L; // 正常，进位
    private final long STATUS_OVERFLOW = 0x8000000000000000L; // 超过 7 位 n 进制有效值

    private int system; // 进制数
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public FullAdder() {
        this(16, 0);
    }

    public FullAdder(int system, long value) {
        if (system > 0xFF)
            throw new RuntimeException("最高支持 0xFF 进制！");

        if (value < -1)
            throw new RuntimeException("起始值的最小值为 -1");

        this.system = system;
        this.value = value;
    }

    /**
     * 全加器：最高支持 7 位 0xFF 进制数的全加器
     *
     * @return true: 数据太大，已溢出
     */
    public boolean fullAdder() {
        for (int digit = 0; true; ++digit) {
            long result = halfAdder(value, digit);
            if ((result & STATUS_OVERFLOW) == STATUS_OVERFLOW) {
                return true;
            }

            if ((result & STATUS_CARRY) == STATUS_CARRY) {
                value = result & 0x00FFFFFFFFFFFFFFL;
                continue;
            }

            value = result;
            break;
        }
        return false;
    }

    /**
     * 半加器：最高支持 7 位 0xFF 进制数的半加器
     *
     * @param value 值
     * @param digit 位数，表示第 digit 位进行半加器计算
     * @return 最高 16 位，表示状态。{@link #STATUS_NONE}, {@link #STATUS_CARRY}, {@link #STATUS_OVERFLOW}
     */
    public long halfAdder(long value, int digit) {
        int offset = digit * 8; // 每 8 位二进制数表示一位 n 进制数
        long increment = 1L << offset;
        value += increment;

        long carry = (long) system << offset; // 进位
        if ((value & carry) != carry)
            return value;

        // 7 位 n 进制数溢出
        if (offset == 48) {
            return STATUS_OVERFLOW;
        }

        // 进位
        long suffix = (1L << offset) - 1L;
        long prefix = (0xFFFFFFFFFFFFFFFFL >> (offset + 8)) << (offset + 8);
        long reset = prefix | suffix; // 重置溢出位
        return value & reset | STATUS_CARRY;
    }

    public static int[] toArray(long value) {
        int[] array = new int[7];
        array[0] = (int) ((value & 0x00FF000000000000L) >> 48);
        array[1] = (int) ((value & 0x0000FF0000000000L) >> 40);
        array[2] = (int) ((value & 0x000000FF00000000L) >> 32);
        array[3] = (int) ((value & 0x00000000FF000000L) >> 24);
        array[4] = (int) ((value & 0x0000000000FF0000L) >> 16);
        array[5] = (int) ((value & 0x000000000000FF00L) >> 8);
        array[6] = (int) (value & 0x00000000000000FFL);
        return array;
    }

    public static int[] toTArray(long value) {
        int[] array = new int[7];
        array[0] = (int) (value & 0x00000000000000FFL);
        array[1] = (int) ((value & 0x000000000000FF00L) >> 8);
        array[2] = (int) ((value & 0x0000000000FF0000L) >> 16);
        array[3] = (int) ((value & 0x00000000FF000000L) >> 24);
        array[4] = (int) ((value & 0x000000FF00000000L) >> 32);
        array[5] = (int) ((value & 0x0000FF0000000000L) >> 40);
        array[6] = (int) ((value & 0x00FF000000000000L) >> 48);
        return array;
    }

    public static void main(String[] args) {
        int system = 8;
        FullAdder adder = new FullAdder(system, -1);
        for (long i = 0; i < 4900L; i++) {
            boolean overflow = adder.fullAdder();
            if (overflow)
                throw new RuntimeException("数据太大，已溢出");

            System.out.format("0x%016x ", adder.getValue());
            System.out.println(Arrays.toString(FullAdder.toArray(adder.getValue())));
            if ((i + 1) % system == 0)
                System.out.println();
        }
    }
}
