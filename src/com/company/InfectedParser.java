package com.company;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

class InfectedParser {
    private String url = "https://coronavirus-monitor.ru/api/v1/statistics/get-cities";
    private HttpURLConnection connection;
    File outFile = new File("D:\\infectedCount.txt");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd,MM");
    private FileOutputStream fos;
    int currentValue,actualValue = 0,INTERVAL = 50000;
    InfectedParser() {
        while(true)
        {
            try {
                URL query = new URL(url);
                connection = (HttpURLConnection) query.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                currentValue = new JSONObject(response.toString()).getJSONObject("data").getInt("totalConfirmed");
                if (actualValue != currentValue)
                {
                    actualValue = currentValue;
                    System.out.println(actualValue);
                    if (!outFile.exists())
                        outFile.createNewFile();
                    fos = new FileOutputStream(outFile,true);
                    fos.write((actualValue + " Time: " + dateFormat.format((new GregorianCalendar()).getTime()) + "\n").getBytes());
                    fos.close();
                }
                Thread.sleep(INTERVAL);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}