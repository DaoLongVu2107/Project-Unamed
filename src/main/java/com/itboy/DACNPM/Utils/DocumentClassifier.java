package com.itboy.DACNPM.Utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentClassifier {


    public static void main(String[] args) {
        File imageFile = new File("C:/Users/vu/Downloads/aaaaas.png");
        ITesseract instance = new Tesseract();
        instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        instance.setLanguage("vie");

        try {
            String result = instance.doOCR(imageFile);
            String loaiVanBan = extractLoaiVanBan(result);
            String soHieu = extractSoHieu(result);
            String coQuanBanHanh = extractCoQuanBanHanh(result);
            String ngayBanHanh = extractNgayBanHanh(result);

            System.out.println("Loại văn bản: " + loaiVanBan);
            System.out.println("Số hiệu: " + soHieu);
            System.out.println("Cơ quan ban hành: " + coQuanBanHanh);
            System.out.println("Ngày ban hành: " + ngayBanHanh);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String extractLoaiVanBan(String text) {
        Pattern pattern = Pattern.compile("(Quyết định|Thông tư|Công văn|Nghị định)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "Không xác định";
    }

    private static String extractSoHieu(String text) {
        Pattern pattern = Pattern.compile("(Số:\\s*)(\\d+/\\d+)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(2) : "Không xác định";
    }

    private static String extractCoQuanBanHanh(String text) {
        Pattern pattern = Pattern.compile("(\\bBộ|Sở|UBND|Tỉnh ủy|Thành ủy\\b)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "Không xác định";
    }

    private static String extractNgayBanHanh(String text) {
        Pattern pattern = Pattern.compile("(ngày\\s+)(\\d{1,2}/\\d{1,2}/\\d{4})");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(2) : "Không xác định";
    }
}