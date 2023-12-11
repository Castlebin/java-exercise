package com.heller.bg;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BingImage {
    private static final String BING_BASE_URL = "https://cn.bing.com";

    public static void downloadBingBackgroundImg(String localFilePath) throws IOException {
        String imgUrl = getBingImgUrl();
        URL url = new URL(imgUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        File newImageFile = new File(localFilePath);
        try (InputStream is = conn.getInputStream();
             BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(newImageFile))) {
            byte[] buf = new byte[8192];
            int size;
            while ((size = is.read(buf)) != -1) {
                os.write(buf, 0, size);
            }
        }
    }

    private static String getBingImgUrl() throws IOException {
        String bingHomeHtml = getBingHomeHtml();
        return parseBingBackgroundImgUrl(bingHomeHtml);
    }

    private static String parseBingBackgroundImgUrl(String bingHomeHtml) {
        String tag = "meta property=\"og:image\" content=\"";
        int start = bingHomeHtml.indexOf(tag);
        int end = bingHomeHtml.indexOf("&amp;rf=", start);
        return bingHomeHtml.substring(start + tag.length(), end);
    }

    private static String getBingHomeHtml() throws IOException {
        URL url = new URL(BING_BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        try (InputStream inputStream = conn.getInputStream()) {
            byte[] buf = new byte[8192];
            StringBuilder sb = new StringBuilder();
            int length;
            while ((length = inputStream.read(buf)) != -1) {
                sb.append(new String(buf, 0, length, StandardCharsets.UTF_8));
            }
            return sb.toString();
        }
    }

}
