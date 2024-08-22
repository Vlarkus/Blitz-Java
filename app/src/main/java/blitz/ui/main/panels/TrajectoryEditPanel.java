package blitz.ui.main.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;

public class TrajectoryEditPanel extends JPanel implements ActiveListener {

    private JComboBox<String> curveTypeDropdown;
    private JCheckBox generatePointsContinuouslyCheckbox;

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

        curveTypeDropdown = new JComboBox<>(new String[]{"Bezier", "Hermite", "Catmull-Rom", "B-spline", "Linear"});
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
        generatePointsContinuouslyCheckbox = new JCheckBox();
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(generatePointsContinuouslyCheckbox, gbc);

        JLabel generatePointsLabel = new JLabel("Generate points continuously:");
        gbc.gridx = 1;
        add(generatePointsLabel, gbc);
    }

    private void handleCurveTypeSelection(String curveType) {
        // Call different methods based on the selected curve type
        switch (curveType) {
            case "Bezier":
                handleBezierSelection();
                break;
            case "Hermite":
                handleHermiteSelection();
                break;
            case "Catmull-Rom":
                handleCatmullRomSelection();
                break;
            case "B-spline":
                handleBSplineSelection();
                break;
            case "Linear":
                handleLinearSelection();
                break;
        }
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
