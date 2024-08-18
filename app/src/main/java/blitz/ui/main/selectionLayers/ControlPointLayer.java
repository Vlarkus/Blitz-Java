package blitz.ui.main.selectionLayers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ControlPoint;
import blitz.models.TrajectoriesList;
import blitz.services.Utils;

public class ControlPointLayer extends JPanel{

    private ControlPoint relatedControlPoint;
    private JButton activeButton; // Set ControlPoint active
    private JButton lockButton; // Lock ControlPoint
    private JLabel indexLabel; // Display trajectory's index in the trajectoriesList
    private JLabel nameLabel; // Display name
    private JTextField nameTextField; // Edit name
    private GridBagConstraints gbc;

    private final int INSETS_DEFAULT = 4;

    public ControlPointLayer(ControlPoint cp) {
        setPreferredSize(MainFrameConfig.CONTROL_POINT_LAYER_PREFERRED_DIMENSION);
        setMaximumSize(MainFrameConfig.CONTROL_POINT_LAYER_PREFERRED_DIMENSION);
        setMinimumSize(MainFrameConfig.CONTROL_POINT_LAYER_PREFERRED_DIMENSION);
        setBackground(MainFrameConfig.CONTROL_POINT_LAYER_BACKGROUND_COLOR);
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        relatedControlPoint = cp;

        constructControlPointLayerPanel();
    }

    private void constructControlPointLayerPanel(){

        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;

        // Active Button
        gbc.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        activeButton = new JButton();
        configureLayerButton(activeButton, true);
        setLayerButtonImage(activeButton, relatedControlPoint == Active.getActiveControlPoint(), MainFrameConfig.PATH_TO_SELECTED_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_UNSELECTED_LAYER_SELECTION_ICON);
        add(activeButton, gbc);
        activeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Active.setActiveControlPoint(relatedControlPoint);
                Utils.requestFocusInWindowFor(activeButton);
            }
        });

        // Index
        gbc.gridx++;
        gbc.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        gbc.insets = new Insets(5, 5, 5, 5);
        indexLabel = new JLabel("" + (TrajectoriesList.getTrajectoryByControlPoint(relatedControlPoint).indexOf(relatedControlPoint) + 1));
        add(indexLabel, gbc);
    
        // Name Label
        gbc.gridx++;
        gbc.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        nameLabel = new JLabel(relatedControlPoint.getName());
        nameLabel.setPreferredSize(MainFrameConfig.TRAJECTORY_LAYER_NAME_ELEMENT_PREFERRED_DIMENSION);
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchToTextField();
            }
        });
        add(nameLabel, gbc);
    
        // Name TextField
        nameTextField = new JTextField(relatedControlPoint.getName());
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
        add(nameTextField, gbc);

        // Lock Button
        gbc.gridx++;
        gbc.insets = new Insets(INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT, INSETS_DEFAULT);
        lockButton = new JButton();
        configureLayerButton(lockButton, true);
        setLayerButtonImage(lockButton, relatedControlPoint.isLocked(), MainFrameConfig.PATH_TO_LOCKED_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_UNLOCKED_LAYER_SELECTION_ICON);
        add(lockButton, gbc);
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatedControlPoint.setIsLocked(!relatedControlPoint.isLocked());
                if(Active.getActiveControlPoint().equals(relatedControlPoint)){
                    Active.setActiveControlPoint(null);
                }
                setLayerButtonImage(lockButton, relatedControlPoint.isLocked(), MainFrameConfig.PATH_TO_LOCKED_LAYER_SELECTION_ICON, MainFrameConfig.PATH_TO_UNLOCKED_LAYER_SELECTION_ICON);
                Utils.requestFocusInWindowFor(lockButton);
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

    private void switchToTextField() {
        nameLabel.setVisible(false);
        nameTextField.setVisible(true);
        nameTextField.requestFocusInWindow();
        nameTextField.selectAll();
    }

    private void switchToLabel() {
        relatedControlPoint.setName(nameTextField.getText());
        nameLabel.setText(relatedControlPoint.getName());
        nameLabel.setVisible(true);
        nameTextField.setVisible(false);
        Utils.requestFocusInWindowFor(nameTextField);
    }
    
}
