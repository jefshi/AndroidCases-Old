package com.csp.cases.activity.network.ping;

import com.csp.library.java.stream.StreamJavaUtil;
import com.csp.utils.android.log.LogCat;

import java.io.IOException;

/**
 * Description: ping 命令测试工具
 * <p>Create Date: 2018/04/16
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class PingUtil {
    public static PingBean ping(int count, String ip) {
        String[] data = new String[2];

        String cmd = "ping -c " + count + " -s 64 -i 1 " + ip;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            data[0] = StreamJavaUtil.getString(process.getInputStream());
            data[1] = StreamJavaUtil.getString(process.getErrorStream());
        } catch (IOException e) {
            LogCat.printStackTrace(e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        PingBean pingBean = new PingBean();
        pingBean.setPacketsCount(count);
        pingBean.setResult(data[0]);
        pingBean.setError(data[1]);
        pingBean.parse();
        return pingBean;
    }
}
