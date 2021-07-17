/*
 * Copyright (C) 2021 Justin Babilino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team2363.lib.json;

import com.team2363.lib.file.TextFileReader;
import com.team2363.lib.file.TextFileWriter;
import java.io.File;

/**
 *
 * @author Justin Babilino
 */
public class JSONTest {
    public static void main(String[] args) {
        try {
            TextFileReader reader = new TextFileReader("A:/numtest.json");
            JSONParser test = new JSONParser(reader.read());
            String jsonString = test.getJSON().exportJSON(JSONFormatOption.ObjectBeginOnNewline.FALSE);
            
            TextFileWriter writer = new TextFileWriter(new File("A:/outjson.json"));
            writer.printString(jsonString);
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

//        int[] intArray = new int[10000000];
//        arrayLength = intArray.length;
//                
//        long slowFirstTimestamp = System.nanoTime();
//        for (int i = 0; i < intArray.length; i++) {}
//        long slowSecondTimestamp = System.nanoTime();
//        
//        long optimizedFirstTimestamp = System.nanoTime();
//        for (int i = 0; i < arrayLength; i++) {}
//        long optimizedSecondTimestamp = System.nanoTime();
//        
//        
//        long slowPerformance = slowSecondTimestamp - slowFirstTimestamp;
//        long optimizedPerformance = optimizedSecondTimestamp - optimizedFirstTimestamp;
//        
//        System.out.println("Optimized Performance: " + optimizedPerformance);
//        System.out.println("Slow Performance: " + slowPerformance);
    }
}
