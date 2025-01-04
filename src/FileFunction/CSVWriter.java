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
import java.util.*;
import java.lang.reflect.Field;
import java.util.List;


public class CSVWriter {

    // Generic method to write a list of objects to a CSV file with control over write mode
    public static <T> void writeObjectsToCSV(String filePath, List<T> objects, int mode) throws Exception {
        // Determine the mode: 0 for append mode, 1 for overwrite mode
        boolean appendMode = (mode == 0); // if mode is 0, set to append mode

        // Check if the file exists and if it has content
        File file = new File(filePath);
        boolean isFileEmpty = !file.exists() || file.length() == 0; // true if file doesn't exist or is empty

        // Open the file with the specified mode
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendMode));

        if (objects == null || objects.isEmpty()) {
            writer.close();
            System.out.println("No objects to write");
            return; // No objects to write
        }

        // Check if the file ends with a newline when appending
        if (appendMode && !isFileEmpty) {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(file.length() - 1); // Seek to the last character
            char lastChar = (char) raf.read(); // Read the last character
            raf.close();

            // Add a new line only if the last character is not a newline or the file is not empty
            if (lastChar != '\n' && lastChar != '\r') {
                writer.newLine();
            }
        }

        // Loop through each object in the list
        for (T object : objects) {
            if (object == null) {
                continue; // Skip null objects
            }

            // Get declared fields of the class (including superclass fields)
            Class<?> clazz = object.getClass();
            Field[] superclassFields = clazz.getSuperclass() != null ? clazz.getSuperclass().getDeclaredFields() : new Field[0];
            Field[] subclassFields = clazz.getDeclaredFields();

            // Create a combined array of fields (superclass fields first, then subclass fields)
            Field[] allFields = new Field[superclassFields.length + subclassFields.length];
            System.arraycopy(superclassFields, 0, allFields, 0, superclassFields.length);
            System.arraycopy(subclassFields, 0, allFields, superclassFields.length, subclassFields.length);

            // Write the object's field values without headers
            for (int i = 0; i < allFields.length; i++) {
                allFields[i].setAccessible(true); // Allow access to private fields
                Object value = allFields[i].get(object);
                writer.write(value != null ? value.toString() : ""); // Handle null values
                if (i < allFields.length - 1) {
                    writer.write("|"); // Separator for fields
                }
            }
            writer.newLine(); // Ensure a newline after writing each object
        }

        writer.flush();
        writer.close(); // Close the writer after writing all objects
        //System.out.println("Data successfully written to " + filePath);
    }
}
