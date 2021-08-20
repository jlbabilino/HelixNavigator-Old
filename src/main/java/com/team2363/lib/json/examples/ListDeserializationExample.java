package com.team2363.lib.json.examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.team2363.lib.file.TextFileReader;
import com.team2363.lib.json.DeserializedGenericType;
import com.team2363.lib.json.DeserializedType;
import com.team2363.lib.json.JSON;
import com.team2363.lib.json.JSONDeserializer;
import com.team2363.lib.json.JSONDeserializerException;
import com.team2363.lib.json.JSONParser;
import com.team2363.lib.json.JSONParserException;

public class ListDeserializationExample {
    public static void main(String[] args) throws IOException {
        String jsonString = new TextFileReader("src\\main\\java\\com\\team2363\\lib\\json\\examples\\intarray.json").read();
        try {
            JSON json = JSONParser.parseString(jsonString);
            DeserializedType type = new DeserializedGenericType(List.class, new DeserializedGenericType(List.class, int.class));
            Object obj = JSONDeserializer.deserializeJSON(json, type);
            List<List<Integer>> list = (List<List<Integer>>) obj;
            System.out.println(list);
            System.out.println(list.get(1).get(2));
        } catch (JSONParserException | JSONDeserializerException e) {
            System.out.println(e);
        }


    }

}
