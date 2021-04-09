package com.ostrowidzki.meeting;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        InputFileToJsonFormatConverter converter = new InputFileToJsonFormatConverter();

        converter.generateJsonFormattedFile();
    }
}
