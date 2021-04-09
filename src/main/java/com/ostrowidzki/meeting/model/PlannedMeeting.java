package com.ostrowidzki.meeting.model;

import java.util.List;

public class PlannedMeeting {
    List<Meeting> meetings;

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
