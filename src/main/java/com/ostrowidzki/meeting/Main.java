package com.ostrowidzki.meeting;

import com.ostrowidzki.meeting.fileprocessor.InputFileToJsonFormatConverter;
import com.ostrowidzki.meeting.fileprocessor.MeetingDurationExtractor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MeetingDurationExtractor extractor = new MeetingDurationExtractor();
        InputFileToJsonFormatConverter converter = new InputFileToJsonFormatConverter();
        CalendarJsonMapper calendarJsonMapper = new CalendarJsonMapper();
        MeetingScheduleCreator meetingScheduleCreator = new MeetingScheduleCreator(calendarJsonMapper);

        extractor.createMeetingDurationFile();
        converter.generateJsonFormattedFile();
        String output = meetingScheduleCreator.findPossibleMeetings();
        System.out.println(output);
    }
}
