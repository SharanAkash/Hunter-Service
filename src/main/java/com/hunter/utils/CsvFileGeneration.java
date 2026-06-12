package com.hunter.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.util.Iterator;

public class CsvFileGeneration {

    public static void getUserApiResponseCsv(String jsonResponse) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonArray = mapper.readTree(jsonResponse);

        String filePath = "data/GetUserData.csv";

        try (FileWriter writer = new FileWriter(filePath)) {

            // Get headers from first object
            JsonNode firstRow = jsonArray.get(0);

            Iterator<String> fieldNames = firstRow.fieldNames();

            StringBuilder header = new StringBuilder();

            while (fieldNames.hasNext()) {
                String field = fieldNames.next();
                header.append(field);

                if (fieldNames.hasNext()) {
                    header.append(",");
                }
            }

            writer.write(header.toString());
            writer.write("\n");

            // Write data rows
            for (JsonNode row : jsonArray) {

                fieldNames = firstRow.fieldNames();

                StringBuilder csvRow = new StringBuilder();

                while (fieldNames.hasNext()) {
                    String field = fieldNames.next();

                    JsonNode value = row.get(field);

                    csvRow.append(value != null ? value.asText() : "");

                    if (fieldNames.hasNext()) {
                        csvRow.append(",");
                    }
                }

                writer.write(csvRow.toString());
                writer.write("\n");
            }
        }
    }
}
