package com.csp.cases.activity.network.ping;

/**
 * Description: ping 命令测试结果
 * <p>Create Date: 2018/04/16
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class PingBean {
    private int packetsCount;
    private int receivedCount;
    private int lossPercent;
    private int totalTime;

    private float rttMin;
    private float rttAvg;
    private float rttMax;
    private float rttMDev;

    private String result;
    private String error;

    public int getPacketsCount() {
        return packetsCount;
    }

    public void setPacketsCount(int packetsCount) {
        this.packetsCount = packetsCount;
    }

    public int getReceivedCount() {
        return receivedCount;
    }

    public int getLossPercent() {
        return lossPercent;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public float getRttMin() {
        return rttMin;
    }

    public float getRttAvg() {
        return rttAvg;
    }

    public float getRttMax() {
        return rttMax;
    }

    public float getRttMDev() {
        return rttMDev;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PingBean{" +
                "packetsCount=" + packetsCount +
                ", receivedCount=" + receivedCount +
                ", lossPercent=" + lossPercent +
                ", totalTime=" + totalTime +
                ", rttMin=" + rttMin +
                ", rttAvg=" + rttAvg +
                ", rttMax=" + rttMax +
                ", rttMDev=" + rttMDev +
                "\n, result=" + result +
                "\n, error=" + error +
                '}';
    }

    public void parse() {
        if (result == null)
            return;

        String ping = result.substring(result.indexOf("statistics"));
        receivedCount = getInt(ping, "transmitted, ", " received,");
        lossPercent = getInt(ping, "received, ", "% packet");
        totalTime = getInt(ping, "loss, time ", "ms");

        ping = substring(ping, "mdev = ", " ms");
        String[] rtt = ping.split("/");
        rttMin = Float.parseFloat(rtt[0]);
        rttAvg = Float.parseFloat(rtt[1]);
        rttMax = Float.parseFloat(rtt[2]);
        rttMDev = Float.parseFloat(rtt[3]);
    }

    private int getInt(String str, String begin, String end) {
        return Integer.parseInt(substring(str, begin, end));
    }

    private String substring(String str, String begin, String end) {
        int beginIndex = str.indexOf(begin) + begin.length();
        int endIndex = str.indexOf(end);
        return str.substring(beginIndex, endIndex);
    }
}
