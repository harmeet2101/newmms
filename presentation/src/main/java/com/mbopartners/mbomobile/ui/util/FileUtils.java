package com.mbopartners.mbomobile.ui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static File createTempFileFromInputStream(InputStream in, File outputDir, String filename) throws IOException {
        //File tempFile = File.createTempFile(filename, null, outputDir);
        File tempFile01=new File(outputDir,filename);
        FileOutputStream fout = null;
        try {

            fout = new FileOutputStream(tempFile01);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                fout.write(buf, 0, len);
            }

        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }

        }
        tempFile01.getName();
        tempFile01.getAbsolutePath();
        return tempFile01;
    }
}
