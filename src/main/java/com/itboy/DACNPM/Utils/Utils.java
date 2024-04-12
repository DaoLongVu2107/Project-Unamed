package com.itboy.DACNPM.Utils;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import java.util.Properties;

public class Utils {
    public static Utils instance;
    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }
//    public String getHashPass(String password) throws
//            NoSuchAlgorithmException{
//        String hashpass="";
//        String plainText = password;
//        MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
//        mdAlgorithm.update(plainText.getBytes());
//
//        byte[] digest = mdAlgorithm.digest();
//        StringBuffer hexString = new StringBuffer();
//
//        for (int i = 0; i < digest.length; i++) {
//            plainText = Integer.toHexString(0xFF & digest[i]);
//
//            if (plainText.length() < 2) {
//                plainText = "0" + plainText;
//            }
//
//            hexString.append(plainText);
//        }
//        hashpass = hexString.toString();
//
//        return hashpass;
//    }
//

//    public boolean sendMail(String to, String subject, String text) {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("quangvuakai@gmail.com", "vuxrzcwstgmbxsoc");
//            }
//        });
//        try {
//            Message message = new MimeMessage(session);
//            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
//            message.setFrom(new InternetAddress("quangvuakai@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject(subject);
//            message.setContent(text, "text/html");
//            Transport.send(message);
//        } catch (MessagingException e) {
//            return false;
//        }
//        return true;
//    }
    public String toSHA1(String a) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String salt ="sdfsafmds5defgf5sd334%";
        String result= "";
        a=a+salt;

        byte[]dataBytes=a.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        result= Base64.getEncoder().encodeToString(md.digest(dataBytes));

        return result;
    }}