package org.dlg.win.system;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lingui
 * @Date 2022-09-16 16:59:17
 */
@Slf4j
public class SystemResUtil {

    private static final long GB = 1024 * 1024 * 1024;

    /**
     * 获取系统资源信息
     *
     * @return 信息
     */
    public static Map<String, Object> getSystemRes() {
        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Map<String, Object> map = new HashMap<>(5);
        map.put("进程CPU使用率： ", twoDecimal(bean.getProcessCpuLoad()) * 100 + "%");
        map.put("系统CPU使用率： ", twoDecimal(bean.getSystemCpuLoad()) * 100 + "%");
        map.put("物理内存总量： ", bean.getTotalPhysicalMemorySize() / GB + "GB");
        map.put("物理内存剩余可用量： ", bean.getFreePhysicalMemorySize() / GB + "GB");
        double memoryUse = (double) 100 * bean.getFreePhysicalMemorySize() / bean.getTotalPhysicalMemorySize();
        map.put("内存使用率： ", twoDecimal(memoryUse) + "%");
        map.put("交换空间总量： ", bean.getTotalSwapSpaceSize() / GB + "GB");
        map.put("交换空间剩余可用量： ", bean.getFreeSwapSpaceSize() / GB + "GB");
        map.put("CPU核心数： ", bean.getAvailableProcessors() + "个");
        map.put("已提交虚拟内存量： ", bean.getCommittedVirtualMemorySize() / GB + "GB");
        map.put("进程已使用CPU时间： ", bean.getProcessCpuTime() / 1000000000.0 + "秒");
        return map;
    }

    /**
     * 保留两位小数
     * @param val
     * @return val
     */
    public static double twoDecimal(double val) {
        BigDecimal bigDecimal = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(getSystemRes());
    }

}
