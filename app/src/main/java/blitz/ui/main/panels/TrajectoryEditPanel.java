package blitz.ui.main.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;

public class TrajectoryEditPanel extends JPanel implements ActiveListener {

    private JComboBox<String> curveTypeDropdown;
    private JCheckBox fixedDistanceCheckbox;
    private JLabel curveTypeLabel;
    private JLabel fixedDistanceLabel;

    public TrajectoryEditPanel() {
        setBackground(MainFrameConfig.TR_EDIT_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.TR_EDIT_PANEL_PREFERRED_DIMENSIONS);
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(MainFrameConfig.PANEL_BORDER_COLOR));

        fillWithContent();

        Active.addActiveListener(this);
    }

    private void fillWithContent() {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Row 1: Label "Curve type:" and Dropdown Menu
        curveTypeLabel = new JLabel("Curve type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(curveTypeLabel, gbc);

        curveTypeDropdown = new JComboBox<>(Trajectory.getAllSplineNames());
        if(Active.getActiveTrajectory() != null){
            curveTypeDropdown.setSelectedItem(Active.getActiveTrajectory().getSplineType());
        }
        gbc.gridx = 1;
        add(curveTypeDropdown, gbc);

        // Add action listener to dropdown
        curveTypeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCurve = (String) curveTypeDropdown.getSelectedItem();
                Active.getActiveTrajectory().setSplineType(selectedCurve);
                Active.notifyActiveTrajectoryStateEdited();
            }
        });

        // Row 2: Checkbox and Label "Generate points continuously"
        fixedDistanceCheckbox = new JCheckBox();
        fixedDistanceCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Active.getActiveTrajectory().setIsContinuous(fixedDistanceCheckbox.isSelected());
                Active.notifyActiveTrajectoryStateEdited();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(fixedDistanceCheckbox, gbc);

        fixedDistanceLabel = new JLabel("Fixed Distance:");
        gbc.gridx = 0;
        add(fixedDistanceLabel, gbc);
    }

    private void updateElementsInfo(){
        Trajectory tr = Active.getActiveTrajectory();
        if(tr != null){
            curveTypeDropdown.setSelectedItem(tr.getSplineType());
            fixedDistanceCheckbox.setSelected(tr.isContinuous());
        }
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
        updateElementsInfo();
    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
    }

    @Override
    public void activeTrajectoryStateEdited(ControlPoint cp) {
        updateElementsInfo();
    }
}
