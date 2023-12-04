package org.dlg.win.input;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

/**
 * 鼠标监听器
 * 需要引入 键盘鼠标监听相关包
 *
 * @author lingui
 * @Date 2023/6/13 16:21
 */
public class MouseListener implements NativeKeyListener , NativeMouseInputListener {

    public static void main(String[] args) throws NativeHookException {
        GlobalScreen.registerNativeHook();
        MouseListener keyUtil = new MouseListener();
        GlobalScreen.addNativeKeyListener(keyUtil);
        GlobalScreen.addNativeMouseListener(keyUtil);
        GlobalScreen.addNativeMouseMotionListener(keyUtil);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        System.out.println(nativeEvent.getKeyCode()+" "+nativeEvent.getWhen());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
        System.out.println(nativeEvent.getX()+" "+nativeEvent.getWhen());
    }
}