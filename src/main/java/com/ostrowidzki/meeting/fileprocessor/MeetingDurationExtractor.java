package com.ostrowidzki.meeting.fileprocessor;

import java.io.*;

public class MeetingDurationExtractor {

    private final String directory = "./src/main/resources/";
    private final String inputFileName = "input.txt";
    private final String outputMeetingDuration = "meeting_duration.txt";
    private final String calendarFile = "calendar.txt";
    private final String stringForSearch = "meeting duration";

    /**
     * function operates on input.txt file from which information about meeting duration is taken and saved
     * into meeting_duration.txt file, the rest of original file content is saved to calendar.txt
     */
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

    /**
     *
     * @param s is a String of a following format: "meeting duration: [hh:mm]"
     * @return is a substring of s param containing hh:mm information
     */
    private String extractMeetingDurationString(String s) {
        int startIndex = s.indexOf("[") + 1;
        int endIndex = s.indexOf("]");
        return s.substring(startIndex, endIndex);
    }
}

