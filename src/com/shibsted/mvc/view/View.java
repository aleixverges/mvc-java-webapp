package com.shibsted.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class View {

    public static final String TEMPLATES_PATH = "/Users/aleix/IdeaProjects/Shibsted/src/com/shibsted/mvc/templates";

    private OutputStream os;

    public void setOutputStream(OutputStream os) {
        this.os = os;
    }

    public void render(String template) throws IOException {
        String pathname = TEMPLATES_PATH + template + ".html";
        File file = new File(pathname).getCanonicalFile();
        FileInputStream fs = new FileInputStream(file);
        final byte[] buffer = new byte[0x10000];
        int count = 0;
        while ((count = fs.read(buffer)) >= 0) {
            os.write(buffer, 0, count);
        }
        fs.close();
        os.close();
    }
}
