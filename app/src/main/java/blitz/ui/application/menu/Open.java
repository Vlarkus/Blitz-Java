package blitz.ui.application.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import blitz.configs.Config;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;

public class Open {
    
    public static void open(){

        // Creating File Chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(Config.SAVE_FILE_EXTENSION_TYPE_DESCRIPTION, Config.SAVE_FILE_EXTENSION_TYPE.substring(1)));
        int userSelection = fileChooser.showOpenDialog(null);
    
        // Checking for correct file selection
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            String fileName = (new File(path).getName());
            
            String fileExtension = fileName.substring( fileName.length() - Config.SAVE_FILE_EXTENSION_TYPE.length() );
            if(!fileExtension.equals(Config.SAVE_FILE_EXTENSION_TYPE)){
                JOptionPane.showMessageDialog(null, "Unsupported file extension! Only \"" + Config.SAVE_FILE_EXTENSION_TYPE + "\" files can be opened.",
                 "Opening Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Opening file
             ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
                Gson gson = new Gson();
                try (JsonReader jsonReader = new JsonReader(new FileReader(path))) {
                    jsonReader.beginArray(); // Begin reading the array of JSON objects
                    while (jsonReader.hasNext()) {
                        Trajectory trajectory = gson.fromJson(jsonReader, Trajectory.class);
                        trajectories.add(trajectory);
                    }
                    jsonReader.endArray(); // End reading the array of JSON objects
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "File not found!", "Opening Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (IOException e) { // Handle IOException
                    JOptionPane.showMessageDialog(null, "Error reading the file!", "Opening Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TrajectoriesList.setTrajectoriesList(trajectories);

        }
    }

}
