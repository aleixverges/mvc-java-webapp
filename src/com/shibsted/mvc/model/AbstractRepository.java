package com.shibsted.mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class AbstractRepository {

    protected Connection connection = null;

    public AbstractRepository() {
        String dbURL = "jdbc:derby:/Users/aleix/IdeaProjects/Shibsted/ShibstedTest;create=true";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            this.connection = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }
}
