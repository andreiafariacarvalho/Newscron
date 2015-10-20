package ch.newscron.newscronjsp;

import ch.newscron.encryption.ShortenerURL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Din
 */
public class shortUrlStatistics {
    
    private String shortURL;
    
    public void saveURL(String longURL) throws IOException {
        shortURL = ShortenerURL.getShortURL(longURL);
        saveToFile(shortURL);
    }
    
    public String getShortURL() {
        return shortURL;
    }
    
    public ArrayList<String> readFile() throws IOException {
        ArrayList<String> shortURLsList = new ArrayList<>();
        String filePath = "/Applications/NetBeans/glassfish-4.1/glassfish/domains/domain1/config/shortURLs.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                shortURLsList.add(line);
            }
        }
        System.out.println(shortURLsList.toString());
        return shortURLsList;
    }
    
    
    private void saveToFile(String shortURL) {
        System.out.println("Saving " + shortURL + " to file " + "shortURLS.txt");
        FileWriter fw = null;
        try {
            String filename= "shortURLs.txt";
            fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(shortURL);//appends the string to the file
            fw.write('\n');
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(shortUrlStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String showStatisticsTable() throws IOException {
        ArrayList<String> shortURLs = readFile();
        String toReturn = "<table border='1' class=\"center\"> <tr> <td> shortURLs </td> <td> # of Clicks </td> </tr>";
        for (String shortUrl : shortURLs) {
            toReturn += "<tr> <td> <a href='" + shortUrl + "'>" + shortUrl + "</a> </td>";
            toReturn += "<td>" + ShortenerURL.getClicksShortURL(shortUrl) + "</td> </tr>";
        }
        toReturn += "</table>";
        return toReturn;
    }
    
    
    
}
