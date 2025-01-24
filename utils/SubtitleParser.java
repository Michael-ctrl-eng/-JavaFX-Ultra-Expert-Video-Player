package com.example.videoplayer.framework.utils;

import com.example.videoplayer.core.model.Subtitle;
import java.io.BufferedReader;
import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
import java.util.ArrayList;
 import java.util.List;
import java.util.regex.Matcher;
 import java.util.regex.Pattern;
import java.util.logging.Level;
 import java.util.logging.Logger;

 /**
 * Class to parse subtitles from files
 */
 public class SubtitleParser {
       private static final Logger logger = Logger.getLogger(SubtitleParser.class.getName());
    private static final Pattern SUBTITLE_LINE_PATTERN = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) --> (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})");

      /**
        * Method to parse subtitles from file
        * @param file File to parse
        * @return List of Subtitle instances
      * @throws IOException IOException if there are any issues during parsing.
        */
       public List<Subtitle> parseSubtitles(File file) throws IOException {
           List<Subtitle> subtitles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
             String line;
              while ((line = reader.readLine()) != null) {
                  Matcher matcher = SUBTITLE_LINE_PATTERN.matcher(line);
                if (matcher.find()) {
                     double startTime = parseTime(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                    double endTime = parseTime(matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8));
                    StringBuilder text = new StringBuilder();
                    String textLine;
                    while ((textLine = reader.readLine()) != null) {
                       if (textLine.trim().isEmpty()) {
                          break; // end of the subtitle block.
                      }
                     text.append(textLine).append("\n");
                   }
                     subtitles.add(new Subtitle(startTime, endTime, text.toString().trim()));
                }
             }
          } catch (Exception e) {
               logger.log(Level.SEVERE, "Error parsing subtitle file: " + file.getAbsolutePath() + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber(), e);
             throw e;
          }
       return subtitles;
     }
      /**
       * Method to get the time from regex.
     * @param hours hours value
       * @param minutes minutes value
       * @param seconds second values
       * @param milliseconds milliseconds value
      * @return double value of the time.
      */
      private double parseTime(String hours, String minutes, String seconds, String milliseconds) {
        try {
              int h = Integer.parseInt(hours);
             int m = Integer.parseInt(minutes);
           int s = Integer.parseInt(seconds);
             int ms = Integer.parseInt(milliseconds);
           return h * 3600.0 + m * 60.0 + s + ms / 1000.0;
         } catch (NumberFormatException e){
             logger.log(Level.SEVERE, "Invalid input to parseTime" + " at " + this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber(), e);
              throw e;
        }
    }
}
