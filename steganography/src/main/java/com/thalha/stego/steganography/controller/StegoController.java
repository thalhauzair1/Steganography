package com.thalha.stego.steganography.controller;

import com.thalha.stego.steganography.util.StegoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class StegoController {

  @PostMapping(value = "/encode", produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<byte[]> encode(
          @RequestParam("image") MultipartFile file,
          @RequestParam("message") String message) {
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      if (image == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid image format. Please upload a PNG.".getBytes());
      }

      String binary = StegoUtils.convertToBinary(message) + "00000000";

      int maxBits = image.getWidth() * image.getHeight();
      if (binary.length() > maxBits) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Message too long for this image.".getBytes());
      }

      BufferedImage stego = StegoUtils.embedMessage(image, binary);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(stego, "png", baos);

      return ResponseEntity.ok()
              .contentType(MediaType.IMAGE_PNG)
              .body(baos.toByteArray());

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Failed to process image.".getBytes());
    }
  }

  @PostMapping("/decode")
  public ResponseEntity<String> decode(@RequestParam("image") MultipartFile file) {
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      if (image == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid image format.");
      }

      String message = StegoUtils.decodeMessage(image);
      return ResponseEntity.ok(message);

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Failed to decode image.");
    }
  }
}
