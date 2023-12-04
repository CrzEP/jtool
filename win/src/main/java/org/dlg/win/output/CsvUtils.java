package org.dlg.win.output;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * csv 操作工具类
 *
 * @author lingui
 * @Date 2022-09-05 15:32:50
 */
@Slf4j
public class CsvUtils {

    /**
     * 追加写入csv文件
     *
     * @param arrays  数组
     * @param csvFile 文件
     * @throws IOException IO异常
     */
    public static void w2csvApp(String[][] arrays, String csvFile) throws IOException {
        FileWriter fileWriter = new FileWriter(csvFile, true);
        wCsv(arrays,fileWriter);
        log.info("已写入 ： {} {}",csvFile, Arrays.toString(arrays));
    }


    /**
     * 写入新csv文件
     *
     * @param arrays  数组
     * @param csvFile 文件
     * @throws IOException IO异常
     */
    public static void w2csvNew(String[][] arrays, String csvFile) throws IOException {
        FileWriter fileWriter = new FileWriter(csvFile, false);
        wCsv(arrays,fileWriter);
        log.info("已写入 ： {} {}",csvFile, Arrays.toString(arrays));
    }

    /**
     * 写入csv文件
     * @param arrays 数据
     * @param fileWriter 文件对象
     * @throws IOException
     */
    private static void wCsv(String[][] arrays, FileWriter fileWriter) throws IOException {
        CSVWriter writer = new CSVWriter(fileWriter);
        for (String[] arr : arrays) {
            writer.writeNext(arr);
            writer.flush();
        }
        writer.close();
    }


}
