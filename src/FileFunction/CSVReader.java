package FileFunction;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

//public class CSVReader {
//    // Generic method to read CSV and map to objects of type T
//    public static <T> List<T> readCSV(String filePath, Class<T> clazz) throws Exception {
//        List<T> resultList = new ArrayList<>();
//        List<String> malformedLines = new ArrayList<>(); // To track malformed lines
//        BufferedReader reader = new BufferedReader(new FileReader(filePath));
//        String line;
//        int lineNumber = 0; // To track the line number
//        // Get declared fields of the class (including superclass if exists)
//        Field[] allFields;
//        if (clazz.getSuperclass()!= null && clazz.getSuperclass()!= Object.class){
//            System.out.println("here super");
//            // If there is a superclass, combine superclass and subclass fields
//            Field[] superclassFields = clazz.getSuperclass().getDeclaredFields();
//            Field[] subclassFields = clazz.getDeclaredFields();
//            // Create a new array to hold both superclass and subclass fields
//            allFields = new Field[superclassFields.length + subclassFields.length];
//
//            // Copy superclass fields to the beginning of the array
//            System.arraycopy(superclassFields, 0, allFields, 0, superclassFields.length);
//            // Copy subclass fields to the array after superclass fields
//            System.arraycopy(subclassFields, 0, allFields, superclassFields.length, subclassFields.length);
//        } else {
//            // If no superclass, just get the fields of the class
//            System.out.println("here bases class ");
//            allFields = clazz.getDeclaredFields();
//        }
//
//        // Read data lines
//        while ((line = reader.readLine()) != null) {
//            lineNumber++;
//
//            // Skip empty or blank lines
//            if (line.trim().isEmpty()) {
//                continue;
//            }
//
//            String[] values = line.split("\\|");
//
//            // Check if the line is malformed (wrong number of values)
//            if (values.length != allFields.length) {
//                System.err.println("Malformed line at " + lineNumber + ": " + line);
//                malformedLines.add(line);
//                continue; // Skip the malformed line and move to the next one
//            }
//
//            try {
//                T obj = clazz.getDeclaredConstructor().newInstance(); // Create a new instance of T
//
//                for (int i = 0; i < allFields.length; i++) {
//                    Field field = allFields[i]; // Get field directly from allFields
//                    String value = values[i].trim();
//
//                    field.setAccessible(true); // Allow access to private fields
//
//                    // Convert and set the value based on the field type
//                    if (field.getType() == int.class || field.getType() == Integer.class) {
//                        field.set(obj, Integer.parseInt(value));
//                    } else if (field.getType() == double.class || field.getType() == Double.class) {
//                        field.set(obj, Double.parseDouble(value));
//                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
//                        field.set(obj, Boolean.parseBoolean(value));
//                    } else {
//                        field.set(obj, value);
//                    }
//                }
//
//                resultList.add(obj); // Add the valid object to the list
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.err.println("Error processing line at " + lineNumber + ": " + line);
//                malformedLines.add(line);
//
//            }
//        }
//
//        reader.close();
//
//        // Optionally, print or log the malformed lines
//        if (!malformedLines.isEmpty()) {
//            System.err.println("Malformed lines encountered:");
//            for (String malformedLine : malformedLines) {
//                System.err.println(malformedLine);
//            }
//        }
//
//        return resultList; // Return the successfully parsed objects
//    }
//
//}
public class CSVReader {
    public static <T> List<T> readCSV(String filePath, Class<T> clazz) throws Exception {
        List<T> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineNumber = 0; // To track the line number

        // Get declared fields of the class
        Field[] allFields;
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            Field[] superclassFields = clazz.getSuperclass().getDeclaredFields();
            Field[] subclassFields = clazz.getDeclaredFields();
            allFields = new Field[superclassFields.length + subclassFields.length];
            System.arraycopy(superclassFields, 0, allFields, 0, superclassFields.length);
            System.arraycopy(subclassFields, 0, allFields, superclassFields.length, subclassFields.length);
        } else {
            allFields = clazz.getDeclaredFields();
        }

        // Read data lines
        while ((line = reader.readLine()) != null) {
            lineNumber++;

            // Skip empty or blank lines
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] values = line.split("\\|");

            // Check if the line is malformed (wrong number of values)
            if (values.length != allFields.length) {
                throw new MalformedCSVException("Malformed line at " + lineNumber + ": " + line);
            }

            try {
                T obj = clazz.getDeclaredConstructor().newInstance(); // Create a new instance of T

                for (int i = 0; i < allFields.length; i++) {
                    Field field = allFields[i];
                    String value = values[i].trim();

                    field.setAccessible(true); // Allow access to private fields

                    // Convert and set the value based on the field type
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        field.set(obj, Integer.parseInt(value));
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        field.set(obj, Double.parseDouble(value));
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        field.set(obj, Boolean.parseBoolean(value));
                    } else {
                        field.set(obj, value);
                    }
                }

                resultList.add(obj); // Add the valid object to the list

            } catch (Exception e) {
                e.printStackTrace();
                throw new MalformedCSVException("Error processing line at " + lineNumber + ": " + line);
            }
        }

        reader.close();
        return resultList; // Return the successfully parsed objects
    }
}