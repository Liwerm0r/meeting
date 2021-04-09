package com.ostrowidzki.meeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ostrowidzki.meeting.model.Calendar;

import java.util.List;

public class MeetingScheduleCreator {

    private final CalendarJsonMapper calendarJsonMapper;

    public MeetingScheduleCreator(CalendarJsonMapper calendarJsonMapper) {
        this.calendarJsonMapper = calendarJsonMapper;
    }

    public String findPossibleMeetings() throws JsonProcessingException {
        Calendar calendar = calendarJsonMapper.MapCalendarDataToPojo();
        return calendar.toString();
    }
}
