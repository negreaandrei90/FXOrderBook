package com.profidata.utility.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.Properties;

class HttpService {
    private static final Logger logger = LogManager.getLogger(HttpService.class);
    final HttpClient client;
    final ObjectMapper mapper;
    String baseUrl;

    HttpService() {
      this.client = HttpClient.newHttpClient();
      this.mapper = new ObjectMapper();
      loadUrl();
    }

    //retrieves the URL set in 'application.properties' file
    private void loadUrl() {
        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties appProps = new Properties();
        try {
            appProps.load(input);
            this.baseUrl = appProps.getProperty("server.url");
        } catch (IOException e) {
            System.err.println("Could not establish connection to server.");
            logger.error("Server Connection: No URL");
        }
    }
}
