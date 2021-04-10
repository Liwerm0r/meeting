package com.ostrowidzki.meeting.fileprocessor;

import java.io.*;

public class InputFileToJsonFormatConverter {

    private final String directory = "./src/main/resources/";
    private final String inputFileName = "calendar.txt";
    private final String calendarOne = "calendar1.json";
    private final String calendarTwo = "calendar2.json";
    private final String regex = "\\}\\{";
    private final String splitter = "!,!";

    public void generateJsonFormattedFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(directory + inputFileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(directory + calendarOne));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory + calendarTwo))) {

            StringBuilder outputFileBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains(":")) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        if (word.endsWith(":")) {
                            String formatWord = stringifyWord(word);
                            line = line.replace(word, formatWord);
                        }
                    }
                }
                outputFileBuilder.append(line);
            }
            String data = addCommaBetweenCalendarRootElement(outputFileBuilder);
            String[] calendars = data.split(splitter);
            bw.write(calendars[0]);
            bufferedWriter.write(calendars[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String stringifyWord(String word) {
        return "\"" + word.substring(0, word.length() - 1) + "\"" + word.substring(word.length() - 1);
    }

    private String addCommaBetweenCalendarRootElement(StringBuilder builder) {
        return String.valueOf(builder).replaceFirst(regex, "}" + splitter + "{");
    }

}