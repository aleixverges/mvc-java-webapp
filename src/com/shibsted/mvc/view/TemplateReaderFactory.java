package com.shibsted.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TemplateReaderFactory {

    public FileInputStream build(File file) {

        try {
            return new FileInputStream(file);
        } catch(FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }
}
