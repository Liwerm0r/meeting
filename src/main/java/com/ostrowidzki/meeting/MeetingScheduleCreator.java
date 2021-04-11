package com.ostrowidzki.meeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ostrowidzki.meeting.model.Calendar;
import com.ostrowidzki.meeting.model.PlannedMeeting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Class for computing data provided by CalendarJsonMapper.class
 */
public class MeetingScheduleCreator {

    private final CalendarJsonMapper calendarJsonMapper;
    private LocalTime meetingDuration;
    private List<Break> validMeetings;

    public MeetingScheduleCreator(CalendarJsonMapper calendarJsonMapper) {
        this.calendarJsonMapper = calendarJsonMapper;
    }

    /**
     * function printing result of algorithm, a list containing schedule of possible meetings range between
     * two workers(two calendars)
     */
    public void printPossibleMeetings() throws JsonProcessingException {
        initializeValidMeetings();
        System.out.println(validMeetings);
    }

    /**
     * function manages the data flow to find valid meetings and initialize them
     */
    private void initializeValidMeetings() throws JsonProcessingException {
        Calendar[] calendars = calendarJsonMapper.MapCalendarDataToPojos();
        loadMeetingDuration();
        List<Break> workerOneBreaks = findBreaks(calendars[0]);
        List<Break> workerTwoBreaks = findBreaks(calendars[1]);
        this.validMeetings = findValidMeetings(workerOneBreaks, workerTwoBreaks);
    }

    /**
     * Part 1 of algorithm, function checks for breaks between meetings and return them as a result
     * @param calendar object contains data for algorithm
     * @return List of time range where worker is not on his meetings(breaks between meetings)
     */
    private List<Break> findBreaks(Calendar calendar) {
        List<Break> aBreaks = new ArrayList<>();
        List<PlannedMeeting> meetings = calendar.getPlannedMeeting();
        LocalTime dayWorkStart = calendar.getWorkingHours().getStart();
        LocalTime dayWorkEnd = calendar.getWorkingHours().getEnd();

        // Add first break's start which is a start of work day
        aBreaks.add(new Break(dayWorkStart));
        // There are n - 1 breaks between meetings(including artificial 0 minutes breaks if meetings are one by another),
        // where n is equal to meeting counter
        for (PlannedMeeting meeting : meetings) {
            // Start of planned meeting is also end of break before that meeting
            aBreaks.get(aBreaks.size() - 1).setEnd(meeting.getStart());
            // and in similar way end of that meeting is a starting point of next break
            aBreaks.add(new Break(meeting.getEnd()));
        }
        // Similar to the first break last break's end is the end of work day
        aBreaks.get(aBreaks.size() - 1).setEnd(dayWorkEnd);
        return validateBreaks(aBreaks);
    }

    /**
     * Part 2 of algorithm, function is looping through each of provided lists and compare each workerOne's break
     * with each workerTwo's break and finds intersection between them and checks if
     * found intersection's start is before end if so then it is added to validMeetings list
     * @param workerOneBreaks List of breaks between meetings for first Calendar
     * @param workerTwoBreaks List of breaks between meetings for second Calendar
     * @return List of valid breaks which can be a propose of meeting
     */
    private List<Break> findValidMeetings(List<Break> workerOneBreaks, List<Break> workerTwoBreaks) {
        List<Break> validMeetings = new ArrayList<>();
        for (Break workerOneBreak : workerOneBreaks) {
            for (Break workerTwoBreak : workerTwoBreaks) {
                Break timeRange = findMeetingTimeRange(workerOneBreak, workerTwoBreak);
                if (timeRange.getStart().isBefore(timeRange.getEnd())) {
                    validMeetings.add(timeRange);
                }
            }
        }
        validMeetings = validateBreaks(validMeetings);
        return validMeetings;
    }


    /**
     * function treats each break like a set and as a result returns intersection between them
     * for example breakOne starts at 10:00 and ends at 12:00, breakTwo starts at 10:30 and ends at 11:30
     * the result of above example is a new break which stats at 10:30 and ends at 11:30
     * @param workerOneBreak break between meetings for calendar1
     * @param workerTwoBreak break between meetings for calendar2
     * @return intersection of two breaks
     */
    private Break findMeetingTimeRange(Break workerOneBreak, Break workerTwoBreak) {
        LocalTime meetingStart = workerOneBreak.getStart().isBefore(workerTwoBreak.getStart())
                ? workerTwoBreak.getStart()
                : workerOneBreak.getStart();

        LocalTime meetingEnd = workerOneBreak.getEnd().isBefore(workerTwoBreak.getEnd())
                ? workerOneBreak.getEnd()
                : workerTwoBreak.getEnd();

        return new Break(meetingStart, meetingEnd);
    }

    /**
     * function main purpose is validate which break from List of breaks are equal to or longer than meeting duration
     * @param aBreaks List of breaks between meetings
     * @return List of valid breaks between meetings
     */
    private List<Break> validateBreaks(List<Break> aBreaks) {
        List<Break> validBreaks = new ArrayList<>();
        long meetingDurationInMinutes = MINUTES.between(LocalTime.of(0, 0), this.meetingDuration);
        for (Break aBreak : aBreaks) {
            long breakTimeInMinutes = MINUTES.between(aBreak.getStart(), aBreak.getEnd());
            if (breakTimeInMinutes >= meetingDurationInMinutes) {
                validBreaks.add(aBreak);
            }
        }
        return validBreaks;
    }

    /**
     * function reads the data stored inside meeting_duration.txt and saved it inside class field meetingDuration
     */
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

    /**
     * Class for modeling data, contains information about start and the end of break between meeting
     */
    private class Break {
        private LocalTime start;
        private LocalTime end;

        public Break() {

        }

        public Break(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }

        public Break(LocalTime start) {
            this.start = start;
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

        @Override
        public String toString() {
            return "[\"" + start + "\",\"" + end + "\"]";
        }
    }
}
