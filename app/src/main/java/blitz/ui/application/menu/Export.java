/*
 * Copyright 2024 Valery Rabchanka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blitz.ui.application.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import blitz.configs.Config;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;
import blitz.utils.FormatManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the export functionality for trajectories within the application.
 * 
 * This class provides a dialog for users to select a trajectory and export it
 * in a chosen format. It handles the creation of the export dialog, file
 * selection, and the actual export process using the {@link FormatManager}.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     Export.createExportDialogue();
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class Export {

    // -=-=-=- FIELDS -=-=-=-

    /**
     * The export dialog instance. Ensures only one export dialog is open at a time.
     */
    private static JDialog exportDialogue;

    /**
     * Logger instance for logging information and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(Export.class.getName());

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Export() {
        throw new UnsupportedOperationException("Export class cannot be instantiated.");
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Creates and displays the export dialog, allowing the user to select a trajectory
     * and export it in a chosen format.
     * 
     * If an export dialog is already open, this method will not create another one.
     */
    public static void createExportDialogue() {
        if (exportDialogue != null && exportDialogue.isShowing()) {
            return; // Do not create another dialog if one is already open
        }

        // Create a modal dialog with the title "Select Trajectory"
        exportDialogue = new JDialog((JFrame) null, "Select Trajectory", true);
        exportDialogue.setSize(Config.EXPORT_DIALOGUE_DIMENSIONS);
        exportDialogue.setLocationRelativeTo(null);
        exportDialogue.setLayout(new BorderLayout(10, 10)); // Add padding between edges and components
        exportDialogue.setResizable(false);

        // Main content panel with GridBagLayout for flexible component arrangement
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Padding around content

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Trajectory selection label
        JLabel trajectoryLabel = new JLabel("Trajectory:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 10);
        contentPanel.add(trajectoryLabel, gbc);

        // Trajectory selection combo box
        JComboBox<String> trajectoryComboBox = new JComboBox<>(
            TrajectoriesList.getAllTrajectoriesNames()
        );
        trajectoryComboBox.setPreferredSize(new Dimension(200, 25)); // Adjust height for accessibility
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 5);
        contentPanel.add(trajectoryComboBox, gbc);

        // Export button, initially disabled until a valid trajectory is selected
        JButton exportButton = new JButton("Export");
        exportButton.setEnabled(false); // Initially disabled

        // Enable export button only when a trajectory is selected
        trajectoryComboBox.addActionListener(e -> {
            exportButton.setEnabled(trajectoryComboBox.getSelectedIndex() != -1);
        });

        // Action listener for the export button
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTrajectory = (String) trajectoryComboBox.getSelectedItem();
                if (selectedTrajectory != null) {
                    exportDialogue.dispose(); // Close the export dialog
                    showFileChooser(selectedTrajectory); // Open the file chooser dialog
                }
            }
        });

        exportButton.setPreferredSize(new Dimension(100, 30)); // Fixed size for consistency
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 0, 5); // Padding between components
        contentPanel.add(exportButton, gbc);

        // Add content panel to the dialog
        exportDialogue.add(contentPanel, BorderLayout.CENTER);

        // Window listener to reset the dialog reference when closed
        exportDialogue.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                exportDialogue = null; // Reset the dialog reference when it is closed
            }
        });

        // Display the export dialog
        exportDialogue.setVisible(true);
    }

    /**
     * Displays a file chooser dialog for the user to select the destination file for export.
     * 
     * @param trajectory the name of the trajectory to export
     */
    private static void showFileChooser(String trajectory) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        fileChooser.setSelectedFile(new File(trajectory + Config.EXPORT_FILE_EXTENSION_TYPE));

        // Set up file filters based on the formats available in FormatManager
        for (String format : FormatManager.getAllFormats()) {
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(format, getExtensionForFormat(format)));
        }
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
            String selectedFormat = selectedFilter.getDescription(); // Format chosen by the user
            String filePath = fileToSave.getAbsolutePath();

            // Ensure the file has the correct extension
            String expectedExtension = Config.EXPORT_FILE_EXTENSION_TYPE;
            if (!filePath.toLowerCase().endsWith(expectedExtension.toLowerCase())) {
                filePath += expectedExtension;
            }

            export(filePath, selectedFormat, trajectory);
        } else {
            LOGGER.log(Level.INFO, "Save command cancelled by user.");
        }
    }

    /**
     * Exports the specified trajectory to a file in the chosen format.
     * 
     * @param path        the file path where the trajectory will be saved
     * @param format      the format in which to export the trajectory
     * @param trajectory  the name of the trajectory to export
     */
    private static void export(String path, String format, String trajectory) {
        File file = new File(path);

        // Create parent directories if they don't exist
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            if (!dir.mkdirs()) {
                LOGGER.log(Level.SEVERE, "Failed to create directories for path: " + dir.getAbsolutePath());
                return;
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            Trajectory tr = TrajectoriesList.getTrajectoryByName(trajectory);
            String data = FormatManager.formatTrajectory(tr, format);
            if (data != null) {
                writer.write(data);
            } else {
                LOGGER.log(Level.WARNING, "Failed to format trajectory. Export aborted.");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while writing the file: " + path, e);
        }
    }

    /**
     * Retrieves the file extension associated with a given format.
     * 
     * This method maps format descriptions to their corresponding file extensions.
     * 
     * @param format the format description
     * @return the corresponding file extension, or a default extension if not found
     */
    private static String getExtensionForFormat(String format) {
        // Map formats to their corresponding extensions
        // This should be adjusted based on actual formats and their extensions
        switch (format) {
            case "LemLib v0.4.0":
                return "lem";
            // Add more cases as new formats are supported
            default:
                return Config.EXPORT_FILE_EXTENSION_TYPE.replace(".", ""); // Default without the dot
        }
    }

    // -=-=-=- INNER CLASSES -=-=-=-

    /**
     * (Optional) Inner classes can be added here if needed.
     */
}
