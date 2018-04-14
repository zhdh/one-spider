package com.one.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


/**
 * Image Utils
 *
 * @author zhdh
 */
public class ImageUtils {

    /**
     * file
     */
    private static File file = null;

    private ImageUtils() {
    }

    /**
     * read image stream
     *
     * @param infile file url
     * @return FileInputStream
     */
    public static FileInputStream getByteImage(String infile) {
        FileInputStream inputImage = null;
        file = new File(infile);
        try {
            inputImage = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputImage;
    }

    /**
     * read image
     *
     * @param inputStream InputStream
     * @param path        path
     */
    private static void readImage(InputStream inputStream, String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, length);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * download image
     *
     * @param imgPath  image url
     * @param savePath save path
     */
    public static void downLoadImg(String imgPath, String savePath) {
        try {
            URL url = new URL(imgPath);
            URLConnection connection = url.openConnection();
            ImageUtils.readImage(connection.getInputStream(), savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
