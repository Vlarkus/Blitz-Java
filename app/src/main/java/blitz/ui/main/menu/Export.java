package blitz.ui.main.menu;

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
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;
import blitz.services.FormatManager;

public class Export {

    private static JDialog exportDialogue;

    public static void createExportDialogue() {
        if (exportDialogue != null && exportDialogue.isShowing()) {
            return; // Do not create another dialog if one is already open
        }

        exportDialogue = new JDialog((JFrame) null, "Select Trajectory", true);

        exportDialogue.setSize(Config.EXPORT_DIALOGUE_DIMENSIONS);
        exportDialogue.setLocationRelativeTo(null);
        exportDialogue.setLayout(new BorderLayout(10, 10)); // Add padding between the edges and the components
        exportDialogue.setResizable(false);

        // Main content panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Add padding around the content, with extra space at the top

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Trajectory selection panel
        JLabel trajectoryLabel = new JLabel("Trajectory:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 10);
        contentPanel.add(trajectoryLabel, gbc);

        JComboBox<String> trajectoryComboBox = new JComboBox<>(
            TrajectoriesList.getAllTrajectoriesNames()
        );
        trajectoryComboBox.setPreferredSize(new Dimension(200, 25)); // Adjust height to make it more accessible
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 5, 5);
        contentPanel.add(trajectoryComboBox, gbc);

        // Export button
        JButton exportButton = new JButton("Export");
        exportButton.setEnabled(false); // Initially disabled

        trajectoryComboBox.addActionListener(e -> {
            exportButton.setEnabled(trajectoryComboBox.getSelectedIndex() != -1);
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trajectory = (String) trajectoryComboBox.getSelectedItem();
                if (trajectory != null) {
                    exportDialogue.dispose(); // Close the first dialog
                    showFileChooser(trajectory); // Open the file chooser dialog
                }
            }
        });

        exportButton.setPreferredSize(new Dimension(100, 30)); // Fixed size for the export button
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 5, 0, 5); // Set padding between components
        contentPanel.add(exportButton, gbc);

        exportDialogue.add(contentPanel, BorderLayout.CENTER);

        exportDialogue.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                exportDialogue = null; // Reset the dialog reference when it is closed
            }
        });

        exportDialogue.setVisible(true);
    }

    private static void showFileChooser(String trajectory) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");
        fileChooser.setSelectedFile(new File(trajectory + Config.EXPORT_FILE_EXTENSION_TYPE));

        // Set up file filters based on the formats available in FormatManager
        for (String format : FormatManager.getAllFormats()) {
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(format, format));
        }
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String selectedFormat = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0]; // Format chosen by the user
            String filePath = fileToSave.getAbsolutePath();

            // Ensure the file has the correct extension
            if (!filePath.toLowerCase().endsWith(Config.EXPORT_FILE_EXTENSION_TYPE)) {
                filePath += Config.EXPORT_FILE_EXTENSION_TYPE;
            }

            export(filePath, selectedFormat, trajectory);
        }
    }

    private static void export(String path, String format, String trajectory) {
        File file = new File(path);

        // Create parent directories if they don't exist
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }

        try (FileWriter in = new FileWriter(file)) {
            Trajectory tr = TrajectoriesList.getTrajectoryByName(trajectory);
            String data = FormatManager.formatTrajectory(tr, format);
            in.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while writing the file.");
        }
    }

}
