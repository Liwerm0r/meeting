package com.ostrowidzki.meeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ostrowidzki.meeting.model.Calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;


public class MeetingScheduleCreator {

    private final CalendarJsonMapper calendarJsonMapper;
    private LocalTime meetingDuration;

    public MeetingScheduleCreator(CalendarJsonMapper calendarJsonMapper) {
        this.calendarJsonMapper = calendarJsonMapper;
    }

    public String findPossibleMeetings() throws JsonProcessingException {
        Calendar[] calendars = calendarJsonMapper.MapCalendarDataToPojos();
        loadMeetingDuration();








        return null;
    }

    private List<Gap> findGaps(Calendar calendar) {
        return null;
    }

    private void loadMeetingDuration() {
        String path = "./src/main/resources/meeting_duration.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String meetingDuration;
            if ((meetingDuration = br.readLine()) != null) {
                String[] timeValues = meetingDuration.split(":");
                this.meetingDuration = LocalTime.of(Integer.parseInt(timeValues[0]), Integer.parseInt(timeValues[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Gap {
        private LocalTime start;
        private LocalTime end;

        public Gap() {

        }

        public Gap(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }

        public LocalTime getStart() {
            return start;
        }

        public void setStart(LocalTime start) {
            this.start = start;
        }

        public LocalTime getEnd() {
            return end;
        }

        public void setEnd(LocalTime end) {
            this.end = end;
        }
    }
}
