package com.shibsted.mvc.view;

import java.io.*;
import java.util.Map;

public class TemplateTokenReplacer {

    public InputStream replace(File template, Map<String, String> tokens) {

        try {

            FileReader fileReader = new FileReader(template);
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

            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }

        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }
}
