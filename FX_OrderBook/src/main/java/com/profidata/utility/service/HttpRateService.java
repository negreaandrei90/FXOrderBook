package com.profidata.utility.service;

import com.profidata.interview.order.CurrencyPair;
import com.profidata.interview.order.rate.FXRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRateService extends HttpService{
    private static final Logger logger = LogManager.getLogger(HttpRateService.class);
    private static HttpRateService INSTANCE;

    //SINGLETON
    private HttpRateService(){}

    /*
    SUPPORTED CURRENCY PAIRS
    Request Method - GET
    Request Body: -
    Response Body: Array of all supported pairs
     */
    public CurrencyPair[] supportedCurrencyPairs() {
        try{
            int statusCode;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/supportedCurrencyPairs"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();

            if(statusCode >=200 && statusCode < 300) {
                logger.info("HTTP Request: 'supportedCurrencyPair'; status code: {}", statusCode);
                return mapper.readValue(response.body(), CurrencyPair[].class);
            } else {
                logger.info("HTTP Request: 'supportedCurrencyPair'; status code: {}\nMessage: {}", statusCode, response);
                return null;
            }
        } catch (Exception e) {
            logger.error("HTTP Request: 'supportedCurrencyPairs' - failed: {}", e.getMessage());
            return null;
        }
    }

    /*
    RATE SNAPSHOT
    Request Method - GET
    Request Body: -
    Response Body: Array of all current FX rates
     */
    public FXRate[] rateSnapshot() {
        try{
            int statusCode;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/rateSnapshot"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();

            if(statusCode >=200 && statusCode < 300) {
                logger.info("HTTP Request: 'rateSnapshot'; status code: {}", statusCode);
                return mapper.readValue(response.body(), FXRate[].class);
            } else {
                logger.info("HTTP Request: 'rateSnapshot'; status code: {}\nMessage: {}", statusCode, response);
                return null;
            }
        } catch (Exception e) {
            logger.error("HTTP Request: 'rateSnapshot' - failed: {}", e.getMessage());
            return null;
        }
    }

    public static HttpRateService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HttpRateService();
        }

        return INSTANCE;
    }
}
