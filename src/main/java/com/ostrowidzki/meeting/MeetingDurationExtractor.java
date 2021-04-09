package com.ostrowidzki.meeting;

import java.io.*;

public class MeetingDurationExtractor {

    String directory = "./src/main/resources/";
    String inputFileName = "calendar.txt";
    String outputFileName = "meeting_duration.txt";
    String stringForSearch = "meeting duration";

    public void createMeetingDurationFile() throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(directory + inputFileName));
             BufferedWriter bw = new BufferedWriter(new FileWriter(directory + outputFileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains(stringForSearch)) {
                    bw.write(extractMeetingDurationString(line));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractMeetingDurationString(String s) {
        int startIndex = s.indexOf("[") + 1;
        int endIndex = s.indexOf("]");
        return s.substring(startIndex, endIndex);
    }
}

