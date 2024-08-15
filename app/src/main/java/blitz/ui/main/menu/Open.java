package blitz.ui.main.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import blitz.configs.MainFrameConfig;
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;

public class Open {
    
    public static void open(){

        // Creating File Chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(MainFrameConfig.SAVE_FILE_EXTENSION_TYPE_DESCRIPTION, MainFrameConfig.SAVE_FILE_EXTENSION_TYPE.substring(1)));
        int userSelection = fileChooser.showOpenDialog(null);
    
        // Checking for correct file selection
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            String fileName = (new File(path).getName());
            
            String fileExtension = fileName.substring( fileName.length() - MainFrameConfig.SAVE_FILE_EXTENSION_TYPE.length() );
            if(!fileExtension.equals(MainFrameConfig.SAVE_FILE_EXTENSION_TYPE)){
                JOptionPane.showMessageDialog(null, "Unsupported file extension! Only \"" + MainFrameConfig.SAVE_FILE_EXTENSION_TYPE + "\" files can be opened.",
                 "Opening Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Opening file
            ArrayList<String> trajectoriesJSON = new ArrayList<String>();
            try (Scanner in = new Scanner(new File(path));) {
                
                in.useDelimiter(";"); // Each Trajectory is separated with ';'

                while (in.hasNext()) {
                    trajectoriesJSON.add(in.next());
                }

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File not found!", "Opening Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Filling ArrayList of unadjusted Trajectories
            ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
            Gson gson = new Gson();
            for (String trajectoryJSON : trajectoriesJSON) {
                Trajectory trajectory = gson.fromJson(trajectoryJSON, Trajectory.class);
                trajectories.add(trajectory);
            }
            TrajectoriesList.setTrajectoriesList(trajectories);

        }
    }

}
