/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.massiva.sms;

import com.alodiga.massiva.utils.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;


/**
 *
 * @author ltoro
 */
public class SendSmsMassiva {
    
    
    public static String sendSmsMassiva(String text, String phoneNumber) {
     
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            // set up URL connection
            java.net.URL url = new java.net.URL("http://www.sistema.massivamovil.com/webservices/SendSms");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // write out form parameters
            String postParamaters = "usuario="+Constants.USER_MASSIVA+"&clave="+Constants.PASSWORD_MASSIVA+"&telefonos="+phoneNumber+"&texto="+text+"";
            System.out.println(postParamaters);
            connection.setFixedLengthStreamingMode(postParamaters.getBytes().length);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(postParamaters);
            out.close();
            //Get Response  
            try {
                is = connection.getInputStream();
            } catch (IOException ioe) {
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int statusCode = httpConn.getResponseCode();
                    System.out.println(httpConn.getResponseCode());
                    System.out.println(statusCode);
                    if (statusCode != 200) {
                        is = httpConn.getErrorStream();
                    }
                }
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
                System.out.println(response);
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
             return e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
}
