package com.ostrowidzki.meeting;

import java.io.*;

public class InputFileToJsonFormatConverter {

    private final String directory = "./src/main/resources/";
    private final String inputFileName = "calendar.txt";
    private final String outputFileName = "calendar_Json.txt";

    public void generateJsonFormattedFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(directory + inputFileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(directory + outputFileName))) {
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
//                outputFileBuilder.append(line).append(System.lineSeparator());
                outputFileBuilder.append(line);
            }
            bw.write(String.valueOf(outputFileBuilder));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String stringifyWord(String word) {
        return "\"" + word.substring(0, word.length() - 1) + "\"" + word.substring(word.length() - 1);
    }

}