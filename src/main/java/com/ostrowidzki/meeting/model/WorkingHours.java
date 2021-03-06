package com.ostrowidzki.meeting.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

/**
 * Class contains data provided by unwrapping json document with jackson library
 */
public class WorkingHours {
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
        return "WorkingHours{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
