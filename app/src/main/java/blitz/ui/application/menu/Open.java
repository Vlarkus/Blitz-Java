package blitz.ui.application.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import blitz.configs.Config;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;

/**
 * Manages the functionality to open and import trajectories from JSON files.
 * 
 * This class provides a dialog for users to select a JSON file containing trajectory data.
 * It parses the selected file and updates the application's trajectory list accordingly.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     Open.open();
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class Open {
    
    // -=-=-=- FIELDS -=-=-=-
    
    /**
     * Logger instance for logging information and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(Open.class.getName());
    
    // -=-=-=- CONSTRUCTORS -=-=-=-
    
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Open() {
        throw new UnsupportedOperationException("Open class cannot be instantiated.");
    }
    
    // -=-=-=- METHODS -=-=-=-
    
    /**
     * Opens a file chooser dialog for the user to select a JSON file containing trajectories.
     * 
     * The method validates the selected file's extension, parses the JSON content to extract
     * trajectory data, and updates the application's trajectory list. It provides user feedback
     * through dialog messages in case of errors.
     */
    public static void open() {
        // Create and configure the file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Trajectories");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
            Config.SAVE_FILE_EXTENSION_TYPE_DESCRIPTION, 
            Config.SAVE_FILE_EXTENSION_TYPE.substring(1) // Remove the dot from the extension
        ));
        
        int userSelection = fileChooser.showOpenDialog(null);
        
        // Proceed if the user approved the file selection
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            
            // Validate the file extension
            if (!hasValidExtension(fileName)) {
                JOptionPane.showMessageDialog(null, 
                    "Unsupported file extension! Only \"" + Config.SAVE_FILE_EXTENSION_TYPE + "\" files can be opened.",
                    "Opening Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                LOGGER.log(Level.WARNING, "User selected file with invalid extension: {0}", fileName);
                return;
            }
            
            // Attempt to parse and import the trajectories from the selected file
            try {
                ArrayList<Trajectory> trajectories = parseTrajectoriesFromFile(path);
                TrajectoriesList.setTrajectoriesList(trajectories);
                JOptionPane.showMessageDialog(null, 
                    "Trajectories imported successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                LOGGER.log(Level.INFO, "Successfully imported trajectories from file: {0}", path);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, 
                    "File not found!", 
                    "Opening Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                LOGGER.log(Level.SEVERE, "File not found: {0}", path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error reading the file!", 
                    "Opening Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                LOGGER.log(Level.SEVERE, "Error reading file: {0}", path);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "An unexpected error occurred while importing trajectories.", 
                    "Opening Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                LOGGER.log(Level.SEVERE, "Unexpected error while importing trajectories from file: " + path, e);
            }
        } else {
            LOGGER.log(Level.INFO, "User canceled the open file dialog.");
        }
    }
    
    // -=-=-=- PRIVATE METHODS -=-=-=-
    
    /**
     * Checks if the given file name has a valid extension as defined in the configuration.
     * 
     * @param fileName the name of the file to check
     * @return {@code true} if the file has a valid extension, {@code false} otherwise
     */
    private static boolean hasValidExtension(String fileName) {
        if (fileName.length() < Config.SAVE_FILE_EXTENSION_TYPE.length()) {
            return false;
        }
        String fileExtension = fileName.substring(fileName.length() - Config.SAVE_FILE_EXTENSION_TYPE.length());
        return fileExtension.equalsIgnoreCase(Config.SAVE_FILE_EXTENSION_TYPE);
    }
    
    /**
     * Parses trajectories from the specified JSON file.
     * 
     * @param path the file path of the JSON file
     * @return an {@link ArrayList} of {@link Trajectory} objects parsed from the file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if an I/O error occurs during reading
     */
    private static ArrayList<Trajectory> parseTrajectoriesFromFile(String path) throws FileNotFoundException, IOException {
        ArrayList<Trajectory> trajectories = new ArrayList<>();
        Gson gson = new Gson();
        
        try (JsonReader jsonReader = new JsonReader(new FileReader(path))) {
            jsonReader.beginArray(); // Begin reading the array of JSON objects
            while (jsonReader.hasNext()) {
                Trajectory trajectory = gson.fromJson(jsonReader, Trajectory.class);
                trajectories.add(trajectory);
            }
            jsonReader.endArray(); // End reading the array of JSON objects
        }
        
        return trajectories;
    }
    
    // -=-=-=- INNER CLASSES -=-=-=-
    
    /**
     * (Optional) Inner classes or additional helper classes can be added here if needed.
     */
}
