package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class InfectedParser {
    Document doc;
    File outFile = new File("D:\\infectedCount.txt");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd,MM");
    FileOutputStream fos;
    String infCount;
    int actualValue = 0;
    InfectedParser() {
        while(true)
        {
            try {
                doc = Jsoup.connect("http://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2")
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
                infCount = doc.getElementById("getStatisticsService").data();// ElementsByTag("STRONG");
                infCount = infCount.substring((infCount.indexOf("confirmedCount")),(infCount.indexOf("suspectedCount")));
                infCount = infCount.substring(16,infCount.indexOf(","));
                System.out.println(infCount);
                if (actualValue != Integer.parseInt(infCount))
                {
                    actualValue = Integer.parseInt(infCount);
                    System.out.println(actualValue);
                    if (!outFile.exists())
                        outFile.createNewFile();
                    fos = new FileOutputStream(outFile,true);
                    fos.write((actualValue + " Time: " + dateFormat.format((new GregorianCalendar()).getTime()) + "\n").getBytes());
                    fos.close();
                }
                Thread.sleep(30000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}