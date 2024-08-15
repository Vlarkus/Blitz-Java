package blitz.ui.main.selectionLayers;

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

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ControlPoint;
import blitz.models.TrajectoriesList;
import blitz.models.Trajectory;
import blitz.models.VisibleTrajectories;
import blitz.servises.Utils;

public class TrajectoryLayer extends JPanel {

    private Trajectory relatedTrajectory;

    private JPanel controlPointsPanel; // Stores ControlPointLayers
    private ArrayList<ControlPointLayer> controlPointLayers;

    private JPanel trajectoryPanelElements;
    private JButton activeButton; // Set/show active trajectory
    private JButton visibilityButton; // Set visible on/off
    private JButton lockButton; // Lock all ControlPoints
    private JButton moveUpButton; // Bring trajectory up
    private JButton moveDownButton; // Bring trajectory down
    private JButton collapseButton; // Hide/show all ControlPoints
    private JLabel indexLabel; // Display trajectory's index in the trajectoriesList
    private JLabel nameLabel; // Display name
    private JTextField nameTextField; // Edit name
    private GridBagConstraints trLayerGBC;

    private boolean isCollapsed;

    private final int INSETS_DEFAULT = 4;

    public TrajectoryLayer(Trajectory tr) {
        setLayout(new BorderLayout());
    
        trLayerGBC = new GridBagConstraints();
        relatedTrajectory = tr;
    
        isCollapsed = false;
    
        constructTrajectoryPanelElements();
        constructControlPointsPanel();
    }
    

    private void constructTrajectoryPanelElements() {
        trajectoryPanelElements = new JPanel(new GridBagLayout());
        trajectoryPanelElements.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_PREFERRED_DIMENSION);
        trajectoryPanelElements.setMinimumSize(MainFrameConfig.TRAJECTORY_LAYER_PREFERRED_DIMENSION);
        trajectoryPanelElements.setMaximumSize(MainFrameConfig.TRAJECTORY_LAYER_PREFERRED_DIMENSION);
        trajectoryPanelElements.setBackground(MainFrameConfig.TRAJECTORY_LAYER_BACKGROUND_COLOR);
        add(trajectoryPanelElements, BorderLayout.NORTH);

        trLayerGBC.anchor = GridBagConstraints.WEST;
        trLayerGBC.weightx = 1.0;

        // Active Button
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        trLayerGBC.gridx = 0;
        trLayerGBC.gridy = 0;
        activeButton = new JButton();
        configureLayerButton(activeButton, true);
        setLayerButtonImage(activeButton, relatedTrajectory == Active.getActiveTrajectory(), MainFrameConfig.PATH_TO_SELECTED_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_UNSELECTED_LAYER_SELECTION_ICON);
        trajectoryPanelElements.add(activeButton, trLayerGBC);
        activeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Active.setActiveTrajectory(relatedTrajectory);
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
        nameLabel.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_NAME_ELEMENT_PREFERRED_DIMENSION);
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchToTextField();
            }
        });
        trajectoryPanelElements.add(nameLabel, trLayerGBC);
    
        // Name TextField
        nameTextField = new JTextField(relatedTrajectory.getName());
        nameTextField.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_NAME_ELEMENT_PREFERRED_DIMENSION);
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
        setLayerButtonImage(visibilityButton, relatedTrajectory.isVisible(), MainFrameConfig.PATH_TO_SHOWN_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_HIDDEN_LAYER_SELECTION_ICON);
        trajectoryPanelElements.add(visibilityButton, trLayerGBC);
        visibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatedTrajectory.setIsVisible(!relatedTrajectory.isVisible());
                setLayerButtonImage(visibilityButton, relatedTrajectory.isVisible(), MainFrameConfig.PATH_TO_SHOWN_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_HIDDEN_LAYER_SELECTION_ICON);
                VisibleTrajectories.notifyVisibleTrajectoriesChanged();
                Utils.requestFocusInWindowFor(visibilityButton);
            }
        });
    
        // Lock Button
        trLayerGBC.gridx++;
        trLayerGBC.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        lockButton = new JButton();
        configureLayerButton(lockButton, true);
        setLayerButtonImage(lockButton, relatedTrajectory.isLocked(), MainFrameConfig.PATH_TO_LOCKED_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_UNLOCKED_LAYER_SELECTION_ICON);
        trajectoryPanelElements.add(lockButton, trLayerGBC);
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatedTrajectory.setIsLocked(!relatedTrajectory.isLocked());
                if(Active.getActiveTrajectory().equals(relatedTrajectory)){
                    Active.setActiveTrajectory(null);
                }
                setLayerButtonImage(lockButton, relatedTrajectory.isLocked(), MainFrameConfig.PATH_TO_LOCKED_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_UNLOCKED_LAYER_SELECTION_ICON);
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
        moveUpButton.setIcon(new ImageIcon(MainFrameConfig.PATH_TO_MOVE_UP_LAYER_SELECTION_ICON));
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
        moveDownButton.setIcon(new ImageIcon(MainFrameConfig.PATH_TO_MOVE_DOWN_LAYER_SELECTION_ICON));
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
        setLayerButtonImage(collapseButton, true, MainFrameConfig.PATH_TO_COLLAPSE_LAYERS_SELECTION_ICON, null);
        trajectoryPanelElements.add(collapseButton, trLayerGBC);
        collapseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCollapsed = !isCollapsed;
                setLayerButtonImage(collapseButton, true, MainFrameConfig.PATH_TO_COLLAPSE_LAYERS_SELECTION_ICON, null);
                Utils.requestFocusInWindowFor(collapseButton);
            }
        });
    
    }

    private void configureLayerButton(JButton b, boolean isRegular){
        if(isRegular){
            b.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_REGULAR_BUTTON_PREFERRED_DIMENSION);
        } else {
            b.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_HALF_SIZE_BUTTON_PREFERRED_DIMENSION);
        }
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setMaximumSize(b.getPreferredSize());
    }

    private void setLayerButtonImage(JButton b, Boolean condition, String path1, String path2){
        ImageIcon image;
        if(condition){
            image = new ImageIcon(path1);
        } else {
            image = new ImageIcon(path2);
        }
        b.setIcon(image);
    }
    
    private void constructControlPointsPanel() {
        // Filler Panel
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        fillerPanel.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION);
        fillerPanel.setMinimumSize(MainFrameConfig.TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION);
        fillerPanel.setMaximumSize(MainFrameConfig.TRAJECTORY_LAYER_FILLER_PANEL_PREFERRED_DIMENSION);
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
      

    private void switchToTextField() {
        nameLabel.setVisible(false);
        nameTextField.setVisible(true);
        nameTextField.requestFocusInWindow();
        nameTextField.selectAll();
    }

    private void switchToLabel() {
        relatedTrajectory.setName(nameTextField.getText());
        nameLabel.setText(relatedTrajectory.getName());
        nameLabel.setVisible(true);
        nameTextField.setVisible(false);
        Utils.requestFocusInWindowFor(nameTextField);
    }

    public void setIndexLabel(int index) {
        indexLabel.setText("" + index);
    }
}
