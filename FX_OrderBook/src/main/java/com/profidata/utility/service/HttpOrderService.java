package com.profidata.utility.service;

import com.profidata.interview.order.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpOrderService extends HttpService{
    private static final Logger logger = LogManager.getLogger(HttpOrderService.class);
    private static HttpOrderService INSTANCE;

    //SINGLETON
    private HttpOrderService(){}

    /*
    CREATE ORDER
    Request Method - POST
    Request Body: Requires Order object (without id)
    Response Body: Newly created order (with corresponding id)
     */
    public Order newOrder(Order order) {
        try{
            String json = mapper.writeValueAsString(order);
            int statusCode;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/createOrder"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();

            if(statusCode >=200 && statusCode < 300) {
                logger.info("HTTP Request: 'newOrder' | Body:{}; status code: {}", json, statusCode);
                return mapper.readValue(response.body(), Order.class);
            } else {
                logger.error("HTTP Request: 'newOrder' | Body:{} - status code: {}\nMessage: {}", json, statusCode, response);
                return null;
            }

        } catch (InterruptedException | IOException e) {
            logger.error("HTTP Request: 'newOrder' - failed: {}", e.getMessage());
            return null;
        }
    }

    /*
    CANCEL ORDER
    Request Method - POST
    Request Body: Requires Order ID (as String)
    Response Body: true/false (as String)
     */
    public String cancelOrder(String id) {
        try{
            int statusCode;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/cancelOrder"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(id))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();

            if(statusCode >=200 && statusCode < 300) {
                logger.info("HTTP Request: 'cancelOrder' | Body:{}; status code: {}", id, statusCode);
                return response.body();
            } else {
                logger.error("HTTP Request: 'cancelOrder' | Body:{} - status code: {}\nMessage: {}", id, statusCode, response);
                return "false";
            }
        } catch (Exception e) {
            logger.error("HTTP Request: 'cancelOrder' - failed: {}", e.getMessage());
            return null;
        }
    }

    /*
    RETRIEVE ORDERS
    Request Method - GET
    Request Body: -
    Response Body: Array of currently existing orders
     */
    public Order[] retrieveOrders() {
        try{
            int statusCode;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/retrieveOrders"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();

            if(statusCode >=200 && statusCode < 300) {
                logger.info("HTTP Request: 'retrieveOrders'; status code: {}", statusCode);
                return mapper.readValue(response.body(), Order[].class);
            } else {
                logger.info("HTTP Request: 'retrieveOrders'; status code: {}\nMessage: {}", statusCode, response);
                return null;
            }
        } catch (Exception e) {
            logger.error("HTTP Request: 'retrieveOrders' - failed: {}", e.getMessage());
            return null;
        }
    }

    public static HttpOrderService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HttpOrderService();
        }

        return INSTANCE;
    }
}
