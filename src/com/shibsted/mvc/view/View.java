package com.shibsted.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class View {

    public static final String TEMPLATES_PATH = "/Users/aleix/IdeaProjects/Shibsted/src/com/shibsted/mvc/templates/";

    private OutputStream outputStream;
    private TemplateFactory templateFactory;
    private TemplateReaderFactory templateReaderFactory;

    public View(OutputStream outputStream, TemplateFactory templateFactory, TemplateReaderFactory templateReaderFactory) {
        this.outputStream = outputStream;
        this.templateFactory = templateFactory;
        this.templateReaderFactory = templateReaderFactory;
    }

    public void render(String templateName) throws IOException {

        String pathname = TEMPLATES_PATH + templateName + ".html";
        File templateFile = this.templateFactory.build(pathname);
        FileInputStream templateInputStream = this.templateReaderFactory.build(templateFile);

        final byte[] buffer = new byte[0x10000];
        int count = 0;

        while ((count = templateInputStream.read(buffer)) >= 0) {
            this.outputStream.write(buffer, 0, count);
        }

        templateInputStream.close();
        this.outputStream.close();
    }
}
