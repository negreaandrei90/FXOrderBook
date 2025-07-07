package com.profidata.utility.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HttpServiceTest {
    private HttpService service;
    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:8888";
    }

    @Test
    public void testUrlLoading() {
        assertDoesNotThrow(() -> {
            service = new HttpService();
        });
    }
}
