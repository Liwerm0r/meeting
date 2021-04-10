package com.ostrowidzki.meeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ostrowidzki.meeting.model.Calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CalendarJsonMapper {

    private final String directory = "./src/main/resources/";
    private final String calendarOneFileName = "calendar1.json";
    private final String calendarTwoFileName = "calendar2.json";

    public Calendar[] MapCalendarDataToPojos() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String calendarOneJson = readJsonFromFile(directory + calendarOneFileName);
        String calendarTwoJson = readJsonFromFile(directory + calendarTwoFileName);
        Calendar calendarOnePojo = mapper.readValue(calendarOneJson, Calendar.class);
        Calendar calendarTwoPojo = mapper.readValue(calendarTwoJson, Calendar.class);
        return new Calendar[]{calendarOnePojo, calendarTwoPojo};
    }

    private String readJsonFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
