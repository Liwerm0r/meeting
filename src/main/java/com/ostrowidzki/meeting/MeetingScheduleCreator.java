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


public class MeetingScheduleCreator {

    private final CalendarJsonMapper calendarJsonMapper;
    private LocalTime meetingDuration;
    private List<Gap> validMeetings;

    public MeetingScheduleCreator(CalendarJsonMapper calendarJsonMapper) {
        this.calendarJsonMapper = calendarJsonMapper;
    }

    public void printPossibleMeetings() throws JsonProcessingException {
        findPossibleMeetings();
        System.out.println(validMeetings);
    }

    private void findPossibleMeetings() throws JsonProcessingException {
        Calendar[] calendars = calendarJsonMapper.MapCalendarDataToPojos();
        loadMeetingDuration();
        List<Gap> workerOneGaps = findGaps(calendars[0]);
        List<Gap> workerTwoGaps = findGaps(calendars[1]);
        this.validMeetings = findValidMeetings(workerOneGaps, workerTwoGaps);
    }

    private List<Gap> findValidMeetings(List<Gap> workerOneGaps, List<Gap> workerTwoGaps) {
        List<Gap> validMeetings = new ArrayList<>();
        for (Gap workerOneGap : workerOneGaps) {
            for (Gap workerTwoGap : workerTwoGaps) {
                Gap timeRange = findMeetingTimeRange(workerOneGap, workerTwoGap);
                if (timeRange.getStart().isBefore(timeRange.getEnd())) {
                    validMeetings.add(timeRange);
                }
            }
        }
        validMeetings = validateGaps(validMeetings);
        return validMeetings;
    }

    private Gap findMeetingTimeRange(Gap workerOneGap, Gap workerTwoGap) {
        LocalTime meetingStart = workerOneGap.getStart().isBefore(workerTwoGap.getStart())
                ? workerTwoGap.getStart()
                : workerOneGap.getStart();

        LocalTime meetingEnd = workerOneGap.getEnd().isBefore(workerTwoGap.getEnd())
                ? workerOneGap.getEnd()
                : workerTwoGap.getEnd();

        return new Gap(meetingStart, meetingEnd);
    }

    private List<Gap> findGaps(Calendar calendar) {
        List<Gap> gaps = new ArrayList<>();
        List<PlannedMeeting> meetings = calendar.getPlannedMeeting();
        LocalTime dayWorkStart = calendar.getWorkingHours().getStart();
        LocalTime dayWorkEnd = calendar.getWorkingHours().getEnd();

        // Add first gap's start which is a start of work day
        gaps.add(new Gap(dayWorkStart));
        // There are n - 1 gaps between meetings(including artificial 0 minutes gaps if meetings are one by another),
        // where n is equal to meeting counter
        for (PlannedMeeting meeting : meetings) {
            // Start of planned meeting is also end of gap before that meeting
            gaps.get(gaps.size() - 1).setEnd(meeting.getStart());
            // and in similar way end of that meeting is a starting point of next gap
            gaps.add(new Gap(meeting.getEnd()));
        }
        // Similar to the first gap last gap's end is the end of work day
        gaps.get(gaps.size() - 1).setEnd(dayWorkEnd);
        return validateGaps(gaps);
    }

    private List<Gap> validateGaps(List<Gap> gaps) {
        List<Gap> validGaps = new ArrayList<>();
        long meetingDurationInMinutes = MINUTES.between(LocalTime.of(0, 0), this.meetingDuration);
        for (Gap gap : gaps) {
            long gapTimeInMinutes = MINUTES.between(gap.getStart(), gap.getEnd());
            if (gapTimeInMinutes >= meetingDurationInMinutes) {
                validGaps.add(gap);
            }
        }
        return validGaps;
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

        public Gap(LocalTime start) {
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
