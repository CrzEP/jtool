package org.dlg.win.player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

/**
 *
 * zoom播放器
 * 播放音频文件
 *
 * @author lingui
 * @Date 2023/5/4 10:08
 */
public class ZoomPlayer {

    public static void main(String[] args) throws FileNotFoundException {
        String path = "D:\\document\\javaCode\\mvcdemo\\slvoice\\cache\\joy.wav";
        String beep = "D:\\cache\\cloudMusic\\Andreas Waldetoft - Countryside.mp3";
        File file = new File(beep);
        play(new FileInputStream(file));
    }

    /**
     * 播放
     * @param path 文件地址
     */
    public static void play(String path){
        File file = new File(path);
        play(file);
    }

    /**
     * 播放
     * @param file 文件
     */
    public static void play(File file) {
        try {
            play(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("播放失败，文件未找到");
        }
    }

    /**
     * 播放
     * @param inputStream 输入流
     */
    public static void play(InputStream inputStream) {
        try {
            if (inputStream.read() == -1) {
                throw new RuntimeException("不可用的流");
            }
            Player player = new Player(inputStream);
            player.play();
            if (player.isComplete()) {
                player.close();
            }
        } catch (IOException | JavaLayerException e) {
            throw new RuntimeException("播放失败");
        }
    }

}
