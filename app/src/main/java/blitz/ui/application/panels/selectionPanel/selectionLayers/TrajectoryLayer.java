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

package blitz.ui.application.panels.selectionPanel.selectionLayers;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blitz.configs.Config;
import blitz.models.active.ActiveEntities;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoriesList.TrajectoriesList;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.visibleTrajectories.VisibleTrajectories;
import blitz.services.Utils;

/**
 * Represents a layer for displaying and managing a trajectory within the selection panel.
 * 
 * This panel provides user interface components for interacting with a {@link Trajectory},
 * including buttons to activate, lock, move, and collapse the trajectory, labels to display
 * its index and name, and a text field for editing the trajectory's name. It also manages
 * a list of {@link ControlPointLayer} instances associated with the trajectory, allowing
 * users to interact with individual control points.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     Trajectory tr = new Trajectory(...);
 *     TrajectoryLayer trLayer = new TrajectoryLayer(tr);
 *     selectionPanel.add(trLayer);
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class TrajectoryLayer extends JPanel {

    // -=-=-=- FIELDS -=-=-=-=-
    
    /**
     * The trajectory associated with this layer.
     */
    private Trajectory relatedTrajectory;

    /**
     * Panel that stores and displays the control point layers.
     */
    private JPanel controlPointsPanel; // Stores ControlPointLayers
    
    /**
     * List of {@link ControlPointLayer} instances associated with this trajectory.
     */
    private ArrayList<ControlPointLayer> controlPointLayers;

    /**
     * Panel containing the trajectory's interactive elements (buttons and labels).
     */
    private JPanel trajectoryPanelElements;
    
    /**
     * Button to set/show the trajectory as active.
     */
    private JButton activeButton; // Set/show active trajectory
    
    /**
     * Button to toggle the trajectory's visibility on or off.
     */
    private JButton visibilityButton; // Set visible on/off
    
    /**
     * Button to lock or unlock all control points within the trajectory.
     */
    private JButton lockButton; // Lock all ControlPoints
    
    /**
     * Button to move the trajectory up in the list.
     */
    private JButton moveUpButton; // Bring trajectory up
    
    /**
     * Button to move the trajectory down in the list.
     */
    private JButton moveDownButton; // Bring trajectory down
    
    /**
     * Button to collapse or expand the display of all control points.
     */
    private JButton collapseButton; // Hide/show all ControlPoints
    
    /**
     * Label to display the trajectory's index in the trajectories list.
     */
    private JLabel indexLabel; // Display trajectory's index in the trajectoriesList
    
    /**
     * Label to display the trajectory's name.
     */
    private JLabel nameLabel; // Display name
    
    /**
     * Text field to edit the trajectory's name.
     */
    private JTextField nameTextField; // Edit name
    
    /**
     * Constraints used for layout management of the trajectory panel elements.
     */
    private GridBagConstraints trLayerGBC;

    /**
     * Flag indicating whether the control points are collapsed (hidden) or expanded (visible).
     */
    private boolean isCollapsed;


    /**
     * Default insets for component spacing.
     */
    private final int INSETS_DEFAULT = 4;

    // -=-=-=- CONSTRUCTORS -=-=-=-=-
    
    /**
     * Constructs a {@code TrajectoryLayer} panel for the specified trajectory.
     * 
     * Initializes the panel's layout, background color, and size. It sets up the trajectory's
     * interactive elements and initializes the control points panel.
     * 
     * @param tr the {@link Trajectory} to associate with this layer
     */
    public TrajectoryLayer(Trajectory tr) {
        setLayout(new BorderLayout());
    
        trLayerGBC = new GridBagConstraints();
        relatedTrajectory = tr;
    
        isCollapsed = false;
    
        constructTrajectoryPanelElements();
        constructControlPointsPanel();
    }
    

    // -=-=-=- METHODS -=-=-=-=-
    
    /**
     * Constructs and adds the trajectory panel elements (buttons and labels) to the panel.
     * 
     * This method sets up the active button, index label, name label and text field,
     * visibility button, lock button, move up and move down buttons, and collapse button.
     * It also configures their behaviors and appearances.
     */
    private void constructTrajectoryPanelElements() {
        trajectoryPanelElements = new JPanel(new GridBagLayout());
        trajectoryPanelElements.setPreferredSize(Config.TRAJECTORY_LAYER_PREFERRED_DIMENSION);
        trajectoryPanelElements.setMinimumSize(Config.TRAJECTORY_LAYER_PREFERRED_DIMENSION);
        trajectoryPanelElements.setMaximumSize(Config.TRAJECTORY_LAYER_PREFERRED_DIMENSION);
        trajectoryPanelElements.setBackground(Config.TRAJECTORY_LAYER_BACKGROUND_COLOR);
        add(trajectoryPanelElements, BorderLayout.NORTH);

        trLayerGBC.anchor = GridBagConstraints.WEST;
        trLayerGBC.weightx = 1.0;

        // Active Button
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        trLayerGBC.gridx = 0;
        trLayerGBC.gridy = 0;
        activeButton = new JButton();
        configureLayerButton(activeButton, true);
        setLayerButtonImage(activeButton, relatedTrajectory == ActiveEntities.getActiveTrajectory(), Config.PATH_TO_SELECTED_LAYER_SELECTION_ICON, Config.PATH_TO_UNSELECTED_LAYER_SELECTION_ICON);
        trajectoryPanelElements.add(activeButton, trLayerGBC);
        activeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActiveEntities.setActiveTrajectory(relatedTrajectory);
                Utils.requestFocusInWindowFor(activeButton);
            }
        });

        // Index
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        trLayerGBC.insets = new Insets(5, 5, 5, 5);
        indexLabel = new JLabel("" + (TrajectoriesList.getTrajectoryIndex(relatedTrajectory) + 1));
        trajectoryPanelElements.add(indexLabel, trLayerGBC);
    
        // Name Label
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        nameLabel = new JLabel(relatedTrajectory.getName());
        nameLabel.setPreferredSize(Config.TRAJECTORY_LAYER_NAME_ELEMENT_PREFERRED_DIMENSION);
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchToTextField();
            }
        });
        trajectoryPanelElements.add(nameLabel, trLayerGBC);
    
        // Name TextField
        nameTextField = new JTextField(relatedTrajectory.getName());
        nameTextField.setPreferredSize(Config.TRAJECTORY_LAYER_NAME_ELEMENT_PREFERRED_DIMENSION);
        nameTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToLabel();
                Utils.requestFocusInWindowFor(nameTextField);
            }
        });

        // FocusAdapter for focus lost
        nameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                switchToLabel();
                Utils.requestFocusInWindowFor(nameTextField);
            }
        });
        nameTextField.setVisible(false);
        trajectoryPanelElements.add(nameTextField, trLayerGBC);
    
        // Visibility Button
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        visibilityButton = new JButton();
        configureLayerButton(visibilityButton, true);
        setLayerButtonImage(visibilityButton, relatedTrajectory.isVisible(), Config.PATH_TO_SHOWN_LAYER_SELECTION_ICON, Config.PATH_TO_HIDDEN_LAYER_SELECTION_ICON);
        trajectoryPanelElements.add(visibilityButton, trLayerGBC);
        visibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                relatedTrajectory.setIsVisible(!relatedTrajectory.isVisible());
                
                if (ActiveEntities.getActiveTrajectory() == getRelatedTrajectory()) {
                    ActiveEntities.setActiveTrajectory(null);
                }
                    
                setLayerButtonImage(visibilityButton, relatedTrajectory.isVisible(), Config.PATH_TO_SHOWN_LAYER_SELECTION_ICON, Config.PATH_TO_HIDDEN_LAYER_SELECTION_ICON);
                VisibleTrajectories.notifyVisibleTrajectoriesChanged();
                Utils.requestFocusInWindowFor(visibilityButton);
            }
        });
    
        // Lock Button
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        lockButton = new JButton();
        configureLayerButton(lockButton, true);
        setLayerButtonImage(lockButton, relatedTrajectory.isLocked(), Config.PATH_TO_LOCKED_LAYER_SELECTION_ICON, Config.PATH_TO_UNLOCKED_LAYER_SELECTION_ICON);
        trajectoryPanelElements.add(lockButton, trLayerGBC);
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatedTrajectory.setIsLocked(!relatedTrajectory.isLocked());
                setLayerButtonImage(lockButton, relatedTrajectory.isLocked(), Config.PATH_TO_LOCKED_LAYER_SELECTION_ICON, Config.PATH_TO_UNLOCKED_LAYER_SELECTION_ICON);
                if (ActiveEntities.getActiveTrajectory() == relatedTrajectory) {
                    ActiveEntities.setActiveTrajectory(null);
                }
                Utils.requestFocusInWindowFor(lockButton);
            }
        });


        // Move Up & Down
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        JPanel movePanel = new JPanel();
        movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.Y_AXIS));
        movePanel.setOpaque(false);
        trajectoryPanelElements.add(movePanel);        

        // Move Up
        moveUpButton = new JButton();
        configureLayerButton(moveUpButton, false);
        moveUpButton.setIcon(new ImageIcon(Config.PATH_TO_MOVE_UP_LAYER_SELECTION_ICON));
        movePanel.add(moveUpButton);
        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrajectoriesList.moveTrajectoryUp(relatedTrajectory);
                Utils.requestFocusInWindowFor(moveUpButton);
            }
        });
    
        // Move Down
        trLayerGBC.gridx++;
        moveDownButton = new JButton();
        configureLayerButton(moveDownButton, false);
        moveDownButton.setIcon(new ImageIcon(Config.PATH_TO_MOVE_DOWN_LAYER_SELECTION_ICON));
        movePanel.add(moveDownButton);
        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrajectoriesList.moveTrajectoryDown(relatedTrajectory);
                Utils.requestFocusInWindowFor(moveDownButton);
            }
        });
    
        // Collapse Button
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        collapseButton = new JButton();
        configureLayerButton(collapseButton, true);
        setLayerButtonImage(collapseButton, true, Config.PATH_TO_COLLAPSE_LAYERS_SELECTION_ICON, null);
        trajectoryPanelElements.add(collapseButton, trLayerGBC);
        collapseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCollapsed = !isCollapsed;
                
                // Toggle visibility of controlPointsPanel
                controlPointsPanel.setVisible(!isCollapsed);
                
                // Optionally change the icon to indicate expanded/collapsed state
                String iconPath = isCollapsed ? Config.PATH_TO_EXPAND_LAYERS_SELECTION_ICON : Config.PATH_TO_COLLAPSE_LAYERS_SELECTION_ICON;
                setLayerButtonImage(collapseButton, true, iconPath, null);
        
                revalidate();
                repaint();
                Utils.requestFocusInWindowFor(collapseButton);
            }
        });
    
    }

    /**
     * Returns whether the control points are currently collapsed.
     * 
     * @return {@code true} if the control points are collapsed, {@code false} otherwise
     */
    public boolean isCollapsed() {
        return isCollapsed;
    }

    /**
     * Configures a {@link JButton} with specified dimensions and appearance settings.
     * 
     * @param b         the {@link JButton} to configure
     * @param isRegular {@code true} if the button is a regular size button, {@code false} otherwise
     */
    private void configureLayerButton(JButton b, boolean isRegular){
        if(isRegular){
            b.setPreferredSize(Config.TRAJECTORY_LAYER_REGULAR_BUTTON_PREFERRED_DIMENSION);
        } else {
            b.setPreferredSize(Config.TRAJECTORY_LAYER_HALF_SIZE_BUTTON_PREFERRED_DIMENSION);
        }
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setMaximumSize(b.getPreferredSize());
    }

    /**
     * Sets the image icon of a layer button based on a condition.
     * 
     * @param b         the {@link JButton} to set the icon for
     * @param condition {@code true} to set the first image, {@code false} to set the second image
     * @param path1     the file path to the first image icon
     * @param path2     the file path to the second image icon
     */
    private void setLayerButtonImage(JButton b, Boolean condition, String path1, String path2){
        ImageIcon image;
        if(condition){
            image = new ImageIcon(path1);
        } else {
            image = new ImageIcon(path2);
        }
        b.setIcon(image);
    }
    
    /**
     * Constructs and adds the control points panel to the trajectory layer.
     * 
     * This method initializes the controlPointsPanel with a vertical box layout and populates it
     * with {@link ControlPointLayer} instances corresponding to each control point in the trajectory.
     * The panel's visibility is managed based on the collapsed state.
     */
    private void constructControlPointsPanel() {
        // Filler Panel
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        fillerPanel.setPreferredSize(Config.TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION);
        fillerPanel.setMinimumSize(Config.TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION);
        fillerPanel.setMaximumSize(Config.TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION);
        add(fillerPanel, BorderLayout.WEST);
    
        // Control Points Panel
        controlPointsPanel = new JPanel();
        controlPointsPanel.setLayout(new BoxLayout(controlPointsPanel, BoxLayout.Y_AXIS));
        add(controlPointsPanel, BorderLayout.CENTER);
    
        controlPointLayers = new ArrayList<>();
    
        if (!isCollapsed) {
            for (ControlPoint cp : relatedTrajectory.getAllControlPoints()) {
                ControlPointLayer cpLayer = new ControlPointLayer(cp);
                controlPointLayers.add(cpLayer);
                controlPointsPanel.add(cpLayer);
            }
        }
    }
      

    /**
     * Switches the display from the name label to the name text field for editing.
     * 
     * Makes the name label invisible and the text field visible, then requests focus on the text field.
     */
    private void switchToTextField() {
        nameLabel.setVisible(false);
        nameTextField.setVisible(true);
        nameTextField.requestFocusInWindow();
        nameTextField.selectAll();
    }

    /**
     * Switches the display from the name text field back to the name label after editing.
     * 
     * Updates the trajectory's name with the text field's content, makes the text field invisible,
     * and the name label visible. It also requests focus back to the text field.
     */
    private void switchToLabel() {
        relatedTrajectory.setName(nameTextField.getText());
        nameLabel.setText(relatedTrajectory.getName());
        nameLabel.setVisible(true);
        nameTextField.setVisible(false);
        Utils.requestFocusInWindowFor(nameTextField);
    }

    /**
     * Sets the index label to the specified index.
     * 
     * @param index the new index to display
     */
    public void setIndexLabel(int index) {
        indexLabel.setText("" + index);
    }

    /**
     * Returns the trajectory associated with this layer.
     * 
     * @return the {@link Trajectory} associated with this layer
     */
    public Trajectory getRelatedTrajectory(){
        return relatedTrajectory;
    }

    /**
     * Checks if the trajectory layer has no associated control points.
     * 
     * @return {@code true} if there are no control points, {@code false} otherwise
     */
    public boolean isEmpty() {
        return controlPointLayers.isEmpty();
    }

    /**
     * Collapses the control points panel, hiding all control points.
     * 
     * Sets the isCollapsed flag to {@code true}, hides the controlPointsPanel, updates the
     * collapse button icon, and refreshes the panel's layout.
     */
    public void collapse() {
        isCollapsed = true;
        controlPointsPanel.setVisible(false);
        String iconPath = Config.PATH_TO_EXPAND_LAYERS_SELECTION_ICON;
        setLayerButtonImage(collapseButton, true, iconPath, null);
        revalidate();
        repaint();
    }
    
    /**
     * Expands the control points panel, showing all control points.
     * 
     * Sets the isCollapsed flag to {@code false}, shows the controlPointsPanel, updates the
     * collapse button icon, and refreshes the panel's layout.
     */
    public void expand() {
        isCollapsed = false;
        controlPointsPanel.setVisible(true);
        String iconPath = Config.PATH_TO_COLLAPSE_LAYERS_SELECTION_ICON;
        setLayerButtonImage(collapseButton, true, iconPath, null);
        revalidate();
        repaint();
    }
    
}
