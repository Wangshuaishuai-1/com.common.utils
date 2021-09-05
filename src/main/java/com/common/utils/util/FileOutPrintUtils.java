package com.common.utils.util;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

/**
 * 文件输出工具类
 *
 * @author nanyanqing
 */
public class FileOutPrintUtils {


    private static Messager messager;

    /**
     * 将内容输出到文件
     */
    public static void generateFile(String msg, String path) {
        try {
            LocalDate date = LocalDate.now();
            path = path + "-" + date.toString() + ".txt";
            //这是mac环境下的路径
            File file = new File(path);
            if (file.isFile()) {
                if (!file.exists()) {
                    file.mkdirs();
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file);
                fw.append(msg);
                fw.flush();
                fw.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            printMsg(e.toString());
        }
    }

    private static void printMsg(String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
