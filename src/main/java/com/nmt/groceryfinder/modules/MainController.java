package com.nmt.groceryfinder.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/9/2024
 */
@RestController
public class MainController {
    private final ResourceLoader resourceLoader;

    @Autowired
    public MainController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/")
    public ResponseEntity<Resource> getHello() {
        Resource resource = resourceLoader.getResource("classpath:/static/index.html");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .body(resource);
    }

    @GetMapping("/v1/health-check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}