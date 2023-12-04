package org.dlg.win.cmd;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 执行cmd命令
 * @author lingui
 * @Date 2023/4/27 16:14
 */
public class CmdUtil {

    /**
     * 执行cmd命令
     *
     * @param cmd cmd命令
     */
    public static void cmd(String cmd) {
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Process ipconfig = Runtime.getRuntime().exec("ipconfig");
        InputStream stream = ipconfig.getInputStream();
        byte[] bts = new byte[1024 * 1024];
        int read = stream.read(bts);
        String str = new String(bts, "gbk");
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        str = new String(bytes);
        System.out.println(str);
    }

}
