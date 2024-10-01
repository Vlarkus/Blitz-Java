package blitz.ui.application.menu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import blitz.configs.Config;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;

/**
 * Manages the functionality to save trajectories to JSON files.
 * 
 * This class provides a dialog for users to specify the save location and file name.
 * It handles file extension validation, directory creation, and serialization of trajectories
 * into JSON format using Gson. User feedback is provided through dialog messages in case
 * of errors or successful operations.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     Save.saveAs();
 * </pre>
 * </p>
 * 
 * @author Valery
 */
public class Save {
    
    // -=-=-=- FIELDS -=-=-=-
    
    /**
     * Logger instance for logging information and errors.
     * (Optional: Uncomment if you decide to use logging instead of or in addition to JOptionPane)
     */
    // private static final Logger LOGGER = Logger.getLogger(Save.class.getName());
    
    // -=-=-=- CONSTRUCTORS -=-=-=-
    
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Save() {
        throw new UnsupportedOperationException("Save class cannot be instantiated.");
    }
    
    // -=-=-=- METHODS -=-=-=-
    
    /**
     * Opens a "Save As" dialog allowing the user to select the destination and name for saving trajectories.
     * 
     * This method performs the following steps:
     * <ol>
     *     <li>Opens a {@link JFileChooser} dialog configured for saving files with the specified extension.</li>
     *     <li>Validates the selected file's name and extension.</li>
     *     <li>Creates the file if it does not already exist.</li>
     *     <li>Serializes the current list of trajectories into JSON format and writes it to the file.</li>
     *     <li>Provides user feedback through dialog messages in case of success or failure.</li>
     * </ol>
     */
    public static void saveAs() {
        // Create and configure the file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
            Config.SAVE_FILE_EXTENSION_TYPE_DESCRIPTION, 
            Config.SAVE_FILE_EXTENSION_TYPE.substring(1) // Remove the dot from the extension
        ));
        
        int userSelection = fileChooser.showSaveDialog(null);
    
        // Proceed if the user approved the file selection
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
    
            // Validate the file name
            if (fileName == null || fileName.trim().isEmpty() || fileName.equals(Config.SAVE_FILE_EXTENSION_TYPE)) {
                JOptionPane.showMessageDialog(null, 
                    "Incorrect file name!",
                    "Saving Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                // LOGGER.log(Level.WARNING, "User provided an incorrect file name: {0}", fileName);
                return;
            }
    
            // Ensure the file has the correct extension
            if (!hasValidExtension(path)) {
                path += Config.SAVE_FILE_EXTENSION_TYPE;
            }
    
            File newFile = new File(path);
    
            // Check if the file already exists
            if (newFile.exists()) {
                int overwrite = JOptionPane.showConfirmDialog(null, 
                    "File already exists. Do you want to overwrite it?", 
                    "Confirm Overwrite", 
                    JOptionPane.YES_NO_OPTION
                );
                if (overwrite != JOptionPane.YES_OPTION) {
                    return; // User chose not to overwrite
                }
            }
    
            // Attempt to create the file and write data
            try {
                boolean fileCreated = newFile.createNewFile();
                if (fileCreated || newFile.exists()) { // If file was created or already exists
                    fillSavingFile(path, TrajectoriesList.getTrajectoriesList());
                    JOptionPane.showMessageDialog(null, 
                        "File saved successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    // LOGGER.log(Level.INFO, "File saved successfully at: {0}", path);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to create the file.", 
                        "Saving Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    // LOGGER.log(Level.SEVERE, "Failed to create the file at: {0}", path);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error saving file. Try again.",
                    "Saving Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                // LOGGER.log(Level.SEVERE, "IOException occurred while creating the file: {0}", path);
            }
        } else {
            // User canceled the save dialog
            // LOGGER.log(Level.INFO, "User canceled the save dialog.");
        }
    }
    
    // -=-=-=- PRIVATE METHODS -=-=-=-
    
    /**
     * Validates whether the given file path ends with the correct file extension.
     * 
     * @param path the file path to validate
     * @return {@code true} if the file has a valid extension, {@code false} otherwise
     */
    private static boolean hasValidExtension(String path) {
        if (path.length() < Config.SAVE_FILE_EXTENSION_TYPE.length()) {
            return false;
        }
        String fileExtension = path.substring(path.length() - Config.SAVE_FILE_EXTENSION_TYPE.length());
        return fileExtension.equalsIgnoreCase(Config.SAVE_FILE_EXTENSION_TYPE);
    }
    
    /**
     * Serializes the list of trajectories into JSON format and writes it to the specified file.
     * 
     * @param path         the file path where the JSON data will be written
     * @param trajectories the {@link ArrayList} of {@link Trajectory} objects to serialize
     */
    private static void fillSavingFile(String path, ArrayList<Trajectory> trajectories) {
        if (trajectories == null || trajectories.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No trajectories to save.", 
                "Saving Error", 
                JOptionPane.ERROR_MESSAGE
            );
            // LOGGER.log(Level.WARNING, "Attempted to save an empty trajectory list.");
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonOutput = gson.toJson(trajectories); 
            writer.write(jsonOutput);
            // LOGGER.log(Level.INFO, "Trajectories successfully written to file: {0}", path);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "An error has occurred while saving the file.", 
                "Saving Error", 
                JOptionPane.ERROR_MESSAGE
            );
            // LOGGER.log(Level.SEVERE, "IOException occurred while writing to file: {0}", path);
        }
    }
    
    // -=-=-=- INNER CLASSES -=-=-=-
    
    /**
     * (Optional) Inner classes or additional helper classes can be added here if needed.
     */
}
