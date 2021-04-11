package com.ostrowidzki.meeting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class contains data provided by unwrapping json document with jackson library
 */
public class Calendar {
    @JsonProperty("working_hours")
    private WorkingHours workingHours;
    @JsonProperty("planned_meeting")
    private List<PlannedMeeting> plannedMeeting;

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(WorkingHours workingHours) {
        this.workingHours = workingHours;
    }

    public List<PlannedMeeting> getPlannedMeeting() {
        return plannedMeeting;
    }

    public void setPlannedMeeting(List<PlannedMeeting> plannedMeeting) {
        this.plannedMeeting = plannedMeeting;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "workingHours=" + workingHours +
                ", plannedMeeting=" + plannedMeeting +
                '}';
    }
}
