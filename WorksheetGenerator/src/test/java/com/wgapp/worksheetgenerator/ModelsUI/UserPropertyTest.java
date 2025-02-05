package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserPropertyTest {
    public UserProperty userProperty= new UserProperty(new SimpleStringProperty("testUserName"), new SimpleStringProperty("testPassword"));

    @BeforeEach
    void setUp() {

    }

    @Test
    void test() {
        assertEquals("testUserName", userProperty.getUsername());
        assertEquals("testPassword", userProperty.getPassword());

    }

}
