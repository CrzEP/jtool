package org.dlg.win.output;


import com.sun.jna.Library;
import com.sun.jna.Native;


/**
 * 消息弹窗
 * 需要引用jna相关包
 *
 * @author lingui
 * @Date 2023/11/7 14:03
 */
public class MessageBox {

    static user32 lib;

    public static void init() {
        if (null == lib) {
            lib = (user32) Native.loadLibrary("user32", user32.class);
        }
    }

    public interface user32 extends Library {
        int MessageBoxA(int something, String text, String caption, int flags);
    }

    /**
     * 消息调用
     *
     * @param title   主题
     * @param message 消息内容
     */
    public static void toast(String title, String message) {
        lib.MessageBoxA(0, message, title, 0);
    }

    /**
     * 使用示例
     * @param args args
     */
    public static void main(String[] args) {
        String message = "您已使用计算机 100 分钟" +
                "\n\n 桌面开始使用时间: xx" +
                "\n\n 提醒周期:  xx";
        toast("久坐提醒消息", message);
    }

}
