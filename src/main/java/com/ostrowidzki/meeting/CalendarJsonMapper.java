package com.ostrowidzki.meeting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ostrowidzki.meeting.model.Calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CalendarJsonMapper {

    private final String path = "./src/main/resources/test.txt";

//    public List<Calendar> MapCalendarDataToPojo() throws JsonProcessingException {
//        String json = readJsonFromFile();
//        ObjectMapper mapper = new ObjectMapper();
//        Calendar[] calendarArray = mapper.readValue(json, Calendar[].class);
//        List<Calendar> calendarList = Arrays.asList(calendarArray);
//        return calendarList;
//    }

    public Calendar MapCalendarDataToPojo() throws JsonProcessingException {
        String json = readJsonFromFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        System.out.println(json);
        return mapper.readValue(json, Calendar.class);
    }

    private String readJsonFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
