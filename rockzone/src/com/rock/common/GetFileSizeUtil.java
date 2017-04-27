package com.rock.common;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * @项目名:TongChengHui
 * 
 * @类名:GetFileSizeUtil.java
 * 
 * @创建人:pangerwei
 * 
 * @类描述:获取文件工具类
 * 
 * @date:2013-10-17
 * 
 * @Version:1.0
 */
public class GetFileSizeUtil {
    private static GetFileSizeUtil instance;

    public GetFileSizeUtil() {

    }

    public static GetFileSizeUtil getInstance() {
        if (instance == null) {
            instance = new GetFileSizeUtil();
        }
        return instance;
    }

    /**
     * 获取文件大小
     * 
     * @param f
     * @return
     * @throws Exception
     */
    public long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    /**
     * 获取文件夹大小
     * 
     * @param f
     * @return
     * @throws Exception
     */
    public long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 获取文件大小
     * 
     * @param fileS
     * @return
     */
    public String FormetFileSize(long fileS) {// 转换文件大小
        if (fileS != 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            String fileSizeString = "";
            if (fileS < 1024) {
                fileSizeString = df.format((double) fileS) + "B";
            } else if (fileS < 1048576) {
                fileSizeString = df.format((double) fileS / 1024) + "K";
            } else if (fileS < 1073741824) {
                fileSizeString = df.format((double) fileS / 1048576) + "M";
            } else {
                fileSizeString = df.format((double) fileS / 1073741824) + "G";
            }
            return fileSizeString;
        }
        return "";
    }

    /**
     * 获取文件夹中文件个数
     * 
     * @param f
     * @return
     */
    public long getlist(File f) {// 递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
