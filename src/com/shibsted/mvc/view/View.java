package com.shibsted.mvc.view;

import java.io.*;
import java.util.Map;

public class View {

    public static final String TEMPLATES_PATH = "/Users/aleix/IdeaProjects/Shibsted/src/com/shibsted/mvc/templates/";

    private OutputStream outputStream;
    private TemplateFactory templateFactory;
    private TemplateReaderFactory templateReaderFactory;
    private TemplateTokenReplacer templateTokenReplacer;

    public View(
            OutputStream outputStream,
            TemplateFactory templateFactory,
            TemplateReaderFactory templateReaderFactory,
            TemplateTokenReplacer templateTokenReplacer
    ) {
        this.outputStream = outputStream;
        this.templateFactory = templateFactory;
        this.templateReaderFactory = templateReaderFactory;
        this.templateTokenReplacer = templateTokenReplacer;
    }

    public void render(String templateName, Map tokens) throws IOException {

        String pathname = TEMPLATES_PATH + templateName + ".html";
        File templateFile = this.templateFactory.build(pathname);

        final byte[] buffer = new byte[0x10000];
        int count = 0;

        InputStream templateInputStream = this.templateReaderFactory.build(templateFile);

        if (tokens != null) {
            templateInputStream = this.templateTokenReplacer.replace(templateFile, tokens);
        }

        while ((count = templateInputStream.read(buffer)) >= 0) {
            this.outputStream.write(buffer, 0, count);
        }

        templateInputStream.close();
        this.outputStream.close();
    }
}
