package com.ostrowidzki.meeting;

import java.io.*;

public class InputFileToJsonFormatConverter {

    String directory = "./src/main/resources/";
    String inputFileName = "calendar.txt";
    String outputFileName = "calendar_Json.txt";

    public void generateJsonFormattedFile() throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory + inputFileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(directory + outputFileName))) {
            StringBuilder outputFileText = new StringBuilder();
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
//                outputFileText.append(line).append(System.lineSeparator());
                outputFileText.append(line);
            }
            bw.write(String.valueOf(outputFileText));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String stringifyWord(String word) {
        return "\"" + word.substring(0, word.length() - 1) + "\"" + word.substring(word.length() - 1);
    }

}