package com.fratics.ecom.similarity.utils.sanitize;

public class Sanitizer {

    public static String addressSanitizer(String entity) {
        if (entity == null || entity.isEmpty())
            throw new IllegalArgumentException("Address for Sanitization Cannot be Empty or null");
        //replace all characters except alphabets
        entity = entity.replaceAll("[^a-zA-Z]", "");
        //convert alphabets to small
        entity = entity.toLowerCase();
        return entity;
    }

    public static String emailSanitizer(String entity) {
        if (entity == null || entity.isEmpty())
            throw new IllegalArgumentException("Email Address for Sanitization Cannot be Empty or null");
        //remove domain
        entity = entity.substring(0, entity.indexOf('@'));
        //replace all characters except alphabets
        entity = entity.replaceAll("[^a-zA-Z]", "");
        //convert alphabets to small
        entity = entity.toLowerCase();
        return entity;
    }

    public static String nameSanitizer(String entity) {
        if (entity == null || entity.isEmpty())
            throw new IllegalArgumentException("Name for Sanitization Cannot be Empty or null");
        //replace all characters except alphabets
        entity = entity.replaceAll("[^a-zA-Z]", "");
        //convert alphabets to small
        entity = entity.toLowerCase();
        return entity;
    }

    public static void main(String[] args) {
        String x = "h.no 23, Mahatma gandi road, Indira nagar, Near Bus Stop, Bangalore-78";
        System.err.println(addressSanitizer(x));

        String y = "Fal123tu1234@abc.com";
        System.err.println(emailSanitizer(y));

        String z = "Tej123as Thirivik$$$raman";
        System.err.println(nameSanitizer(z));
    }
}
