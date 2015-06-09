package com.shibsted.mvc.view;

import java.io.*;
import java.util.Map;

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

    public void render(String templateName, Map tokens) throws IOException {

        String pathname = TEMPLATES_PATH + templateName + ".html";
        File templateFile = this.templateFactory.build(pathname);

        final byte[] buffer = new byte[0x10000];
        int count = 0;

        if (tokens != null) {
            InputStream templateInputStream = this.replaceTokens(templateFile, tokens);
            while ((count = templateInputStream.read(buffer)) >= 0) {
                this.outputStream.write(buffer, 0, count);
            }
            templateInputStream.close();
        } else {
            FileInputStream templateInputStream = this.templateReaderFactory.build(templateFile);
            while ((count = templateInputStream.read(buffer)) >= 0) {
                this.outputStream.write(buffer, 0, count);
            }
            templateInputStream.close();
        }

        this.outputStream.close();
    }

    private InputStream replaceTokens(File templateFile, Map<String, String> tokens) {

        try {
            FileReader fileReader = new FileReader(templateFile);
            String s;
            String totalStr = "";
            try (BufferedReader br = new BufferedReader(fileReader)) {

                while ((s = br.readLine()) != null) {
                    totalStr += s;
                }

                for (Map.Entry<String, String> entry : tokens.entrySet()) {
                    String key = "<%" + entry.getKey().toString() + "%>";
                    String value = entry.getValue().toString();
                    totalStr = totalStr.replaceAll(key, value);
                }

                return new ByteArrayInputStream(totalStr.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
