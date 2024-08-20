package blitz.ui.main.menu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import blitz.configs.MainFrameConfig;
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;

public class Save {
    
    public static void saveAs(){

        // Create File Chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(MainFrameConfig.SAVE_FILE_EXTENSION_TYPE_DESCRIPTION, MainFrameConfig.SAVE_FILE_EXTENSION_TYPE));
        int userSelection = fileChooser.showSaveDialog(null);
    
        // Creating new file
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            String fileName = new File(path).getName();

            // Incorect File Name Check
            if(fileName == null || fileName.equals(MainFrameConfig.SAVE_FILE_EXTENSION_TYPE)){
                JOptionPane.showMessageDialog(null, "Incorrect file name!",
                 "Saving Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If user's file name does not already include extension type, add it.
            if(!path.substring( path.length() - MainFrameConfig.SAVE_FILE_EXTENSION_TYPE.length() ).equals(MainFrameConfig.SAVE_FILE_EXTENSION_TYPE)){
                path += MainFrameConfig.SAVE_FILE_EXTENSION_TYPE;
            }

            // Creating the file
            File newFile = new File(path);

            // Filling the file
            try {
                boolean fileCreated = newFile.createNewFile();
                if (fileCreated) {
                    fillSavingFile(path, TrajectoriesList.getTrajectoriesList());
                } else {
                    JOptionPane.showMessageDialog(null, "File with this name already exists. Save your file to another directory or rename it.",
                    "Saving Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file. Try again.",
                    "Saving Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    // Fill saving file
    private static void fillSavingFile(String path, ArrayList<Trajectory> trajectories) {

        if (!trajectories.isEmpty()) {
            try (var in = new BufferedWriter(new FileWriter(path))) {
    
                Gson gson = new Gson();
                // Use gson.toJson to convert the entire list into a JSON array
                String output = gson.toJson(trajectories); 
    
                in.write(output);
    
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "An error has occurred while saving the file.", "Saving Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    }

}
