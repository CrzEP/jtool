package org.dlg.win.input;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import lombok.extern.slf4j.Slf4j;

/**
 * 键入监听器
 * 需要引用项目jintellitype，开源位置：<a href="https://github.com/melloware/jintellitype">...</a>
 *
 *
 * @author lingui
 * @Date 2023/4/27 18:18
 */
public class KeyListener {

    /**
     * 监听器 单例
     */
    private static final KeyListener LISTENER = new KeyListener();

    public static final int EXIT = 10001;

    private KeyListener() {
    }

    public static KeyListener getInstance() {
        return LISTENER;
    }

    /**
     * 添加监听
     */
    public void addListener(HotkeyListener listener) {
        // 注册所有字母键+空格键
        for (Key key : Key.values()) {
            JIntellitype.getInstance().registerHotKey(key.getCode(), 0, key.getCode());
        }
        // 退出组合键
        JIntellitype.getInstance().registerHotKey(EXIT, JIntellitype.MOD_CONTROL, Key.Q.name().charAt(0));
        // 注册监听器
        JIntellitype.getInstance().addHotKeyListener(listener);
    }

    /**
     * 移除监听
     */
    public void removeListener(HotkeyListener listener) {
        // 反注册所有字母键+空格键
        for (Key key : Key.values()) {
            JIntellitype.getInstance().unregisterHotKey(key.getCode());
        }
        // 注册监听器
        JIntellitype.getInstance().removeHotKeyListener(listener);
    }

    public static void main(String[] args) {
        getInstance().addListener(new HotkeyListener() {
            @Override
            public void onHotKey(int identifier) {
                System.out.println("\""
                        + (char) identifier + "\"");
            }
        });
        System.out.println("监听开始：");
    }

}
