package com.ostrowidzki.meeting.model;

public class Calendar {
    private WorkingHours workingHours;
    private PlannedMeeting plannedMeeting;

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(WorkingHours workingHours) {
        this.workingHours = workingHours;
    }

    public PlannedMeeting getPlannedMeeting() {
        return plannedMeeting;
    }

    public void setPlannedMeeting(PlannedMeeting plannedMeeting) {
        this.plannedMeeting = plannedMeeting;
    }
}
