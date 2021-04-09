package com.ostrowidzki.meeting.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import java.util.List;

public class PlannedMeeting {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime end;

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
        return "PlannedMeeting{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
