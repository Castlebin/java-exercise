package com.heller.bg;

import org.junit.Test;

import java.io.IOException;

public class BingImageTest {

    @Test
    public void testDownloadBingBackgroundImg() throws IOException {
        BingImage.downloadBingBackgroundImg("1.jpg");
    }

}
