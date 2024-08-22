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
    private JCheckBox continuetyCheckbox;

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Row 1: Label "Curve type:" and Dropdown Menu
        JLabel curveTypeLabel = new JLabel("Curve type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(curveTypeLabel, gbc);

        curveTypeDropdown = new JComboBox<>(Trajectory.getAllSplineNames());
        gbc.gridx = 1;
        add(curveTypeDropdown, gbc);

        // Add action listener to dropdown
        curveTypeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCurve = (String) curveTypeDropdown.getSelectedItem();
                handleCurveTypeSelection(selectedCurve);
            }
        });

        // Row 2: Checkbox and Label "Generate points continuously"
        continuetyCheckbox = new JCheckBox();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(continuetyCheckbox, gbc);

        JLabel generatePointsLabel = new JLabel("Continuity:");
        gbc.gridx = 0;
        add(generatePointsLabel, gbc);
    }

    private void handleCurveTypeSelection(String splineType) {
        
    }

    private void handleBezierSelection() {
        // Implement Bezier curve handling here
        System.out.println("Bezier curve selected.");
    }

    private void handleHermiteSelection() {
        // Implement Hermite curve handling here
        System.out.println("Hermite curve selected.");
    }

    private void handleCatmullRomSelection() {
        // Implement Catmull-Rom curve handling here
        System.out.println("Catmull-Rom curve selected.");
    }

    private void handleBSplineSelection() {
        // Implement B-spline curve handling here
        System.out.println("B-spline curve selected.");
    }

    private void handleLinearSelection() {
        // Implement Linear curve handling here
        System.out.println("Linear curve selected.");
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
    }
}
