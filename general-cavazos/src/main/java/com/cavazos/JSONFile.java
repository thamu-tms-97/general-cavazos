package com.cavazos;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONFile {

    /**
     * Reads a JSON resource and returns it as a String array.
     * Ideal for Maven projects where the file is in src/main/resources.
     */
    public static String[] readCommandsFromResource(String resourceName) {
        JSONParser jsonParser = new JSONParser();
        JSONArray data = null;

        try (
            InputStream inputStream = JSONFile.class.getResourceAsStream(resourceName);
            InputStreamReader reader = new InputStreamReader(inputStream)
        ) {
            if (inputStream == null) {
                return null;
            }

            Object obj = jsonParser.parse(reader);
            data = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }

        // Convert JSONArray to a clean String array
        String[] arr = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i).toString().trim();
        }
        return arr;
    }
}