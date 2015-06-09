package com.shibsted.mvc.view;

import java.io.File;
import java.io.IOException;

public class TemplateFactory {

    public File build(String path) {
        try {
            return new File(path).getCanonicalFile();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
