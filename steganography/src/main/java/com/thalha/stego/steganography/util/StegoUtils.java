package com.thalha.stego.steganography.util;

import java.awt.image.BufferedImage;

public class StegoUtils {


    public static String convertToBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            String binChar = String.format("%8s", Integer.toBinaryString(c)).replace(" ", "0");
            binary.append(binChar);
        }
        return binary.toString();
    }

    public static BufferedImage embedMessage(BufferedImage image, String message) {

        int width = image.getWidth();
        int height = image.getHeight();
        int messageIndex = 0;

        outerLoop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (messageIndex >= message.length()) {
                    break outerLoop;
                }
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                int bit = message.charAt(messageIndex) - '0';
                blue = (blue & 0XFE) | bit;

                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newRgb);

                messageIndex++;

            }
        }

        return image;
    }

    public static String decodeMessage(BufferedImage image) {
        StringBuilder binary = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int bit = blue & 1;

                binary.append(bit);
            }
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i + 8 <= binary.length(); i += 8) {
            String byteString = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);
            if (charCode == 0) {
                break;
            }
            message.append((char) charCode);
        }
        return message.toString();
    }

}