package com.acp.controller;
//local
import com.acp.model.UrlRequest;
import com.acp.service.ValueManagerService;
//Java
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class ApiController {

    private final ValueManagerService valueManagerService;

    public ApiController(ValueManagerService valueManagerService) {
        this.valueManagerService = valueManagerService;
    }

    // Endpoint 1: UUID (HTTP GET)
    @GetMapping("/uuid")
    public ResponseEntity<String> getUuid() {
        String htmlResponse = "<!DOCTYPE html><html><head><title>Student ID</title></head><body><h1>2771099</h1></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlResponse);
    }

    // Endpoint 2: Write (HTTP POST)
    @PostMapping("/valuemanager")
    public ResponseEntity<String> writeValueQueryParam(@RequestParam String key, @RequestParam String value) {
        String response = valueManagerService.writeValue(key, value);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/valuemanager/{key}/{value}")
    public ResponseEntity<String> writeValuePathParam(@PathVariable String key, @PathVariable String value) {
        String response = valueManagerService.writeValue(key, value);
        return ResponseEntity.ok(response);
    }

    // Endpoint 3: Delete (HTTP DELETE)
    @DeleteMapping("/valuemanager/{key}")
    public ResponseEntity<String> deleteValue(@PathVariable String key) {
        String response = valueManagerService.deleteValue(key);
        if (response.equals("Key not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // Endpoint 4: Read (HTTP GET)
    @GetMapping(value = "/valuemanager/{key}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> readValue(@PathVariable String key) {
        String value = valueManagerService.readValue(key);
        if (value.equals("Key not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(value);
        }
        return ResponseEntity.ok(value);
    }

    @GetMapping("/valuemanager")
    public ResponseEntity<Map<String, String>> getAllValues() {
        return ResponseEntity.ok(valueManagerService.getAllValues());
    }

    // Endpoint 5: Call Service (HTTP POST)
    @PostMapping("/callservice")
    public ResponseEntity<String> callService(@RequestBody UrlRequest request) {
        try {
            String url = request.getExternalBaseUrl() + request.getParameters();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid URL or request");
        }
    }
}
