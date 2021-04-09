package com.ostrowidzki.meeting;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MeetingDurationExtractor extractor = new MeetingDurationExtractor();
        InputFileToJsonFormatConverter converter = new InputFileToJsonFormatConverter();

        extractor.createMeetingDurationFile();
        converter.generateJsonFormattedFile();
    }
}
