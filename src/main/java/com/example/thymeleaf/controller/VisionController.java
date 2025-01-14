package com.example.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.thymeleaf.service.Visionservice;

@RestController
public class VisionController {
  @Autowired
  private Visionservice visionService;

  @GetMapping("/extract-text")
  public String extractText(@RequestParam("imageUrl") String imageUrl) {
      try {
        // return visionService.extractTextFromImageUrl(imageUrl);
        // return visionService.analyzeLabelsFromImageUrl(imageUrl);
        return visionService.detectObjectsFromImageUrl(imageUrl);
      } catch (Exception e) {
        //e.printStackTrace();
        return "Failed to extract text: " + e.getMessage();
      }
  }
}
