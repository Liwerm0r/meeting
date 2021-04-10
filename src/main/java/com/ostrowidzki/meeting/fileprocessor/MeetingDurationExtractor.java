package com.ostrowidzki.meeting.fileprocessor;

import java.io.*;

public class MeetingDurationExtractor {

    private final String directory = "./src/main/resources/";
    private final String inputFileName = "input.txt";
    private final String outputMeetingDuration = "meeting_duration.txt";
    private final String calendarFile = "calendar.txt";
    private final String stringForSearch = "meeting duration";

    public void createMeetingDurationFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(directory + inputFileName));
             BufferedWriter bw = new BufferedWriter(new FileWriter(directory + outputMeetingDuration));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory + calendarFile))) {
            StringBuilder calendarBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains(stringForSearch)) {
                    bw.write(extractMeetingDurationString(line));
                } else {
                    calendarBuilder.append(line);
                }
            }
            bufferedWriter.write(String.valueOf(calendarBuilder));
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

