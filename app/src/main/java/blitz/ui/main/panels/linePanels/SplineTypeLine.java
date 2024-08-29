package blitz.ui.main.panels.linePanels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import blitz.configs.MainFrameConfig;
import blitz.models.Active;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;
import blitz.models.calculations.Calculations;

public class SplineTypeLine extends LinePanel implements ActiveListener {

    private JComboBox<String> splineTypeComboBox;
    private final String[] CURVE_TYPES = Calculations.ALL_SPLINE_TYPES;

    public SplineTypeLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel splineTypeLabel = new JLabel("Spline:");

        // Initialize the drop-down menu
        splineTypeComboBox = new JComboBox<>(CURVE_TYPES);
        Dimension dim = MainFrameConfig.LINE_PANEL_COMBO_BOX_DIMENSITONS;
        splineTypeComboBox.setPreferredSize(dim);
        splineTypeComboBox.setMaximumSize(dim);
        splineTypeComboBox.setMinimumSize(dim);
        splineTypeComboBox.addActionListener(e -> {
            if (isInteractable()) {
                String selectedSplineType = (String) splineTypeComboBox.getSelectedItem();
                if (selectedSplineType != null) {
                    Trajectory tr = Active.getActiveTrajectory();
                    tr.setSplineType(selectedSplineType);
                    Active.notifyActiveControlPointStateEdited();
                }
                Active.notifyActiveTrajectoryStateEdited();
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        Component horizontalStrut = Box.createHorizontalStrut(82);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(splineTypeLabel, gbc);

        gbc.gridx++;
        add(splineTypeComboBox, gbc);

        displayInteractability();

        Active.addActiveListener(this);
    }

    private void updateComboBox() {
        Trajectory tr = Active.getActiveTrajectory();
        if (tr != null) {
            splineTypeComboBox.setSelectedItem(tr.getSplineType());
        }

    }

    @Override
    public boolean isInteractable() {
        return Active.getActiveTrajectory() != null;
    }

    @Override
    protected void displayInteractability() {
        super.displayInteractability();
        boolean isInteractable = isInteractable();
        splineTypeComboBox.setEnabled(isInteractable);
    }

    @Override
    public void activeTrajectoryChanged(Trajectory tr) {
        // Implement if necessary
    }

    @Override
    public void activeControlPointChanged(ControlPoint cp) {
        displayInteractability();
        updateComboBox();
    }

    @Override
    public void activeControlPointStateEdited(ControlPoint cp) {
        updateComboBox();
    }

    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
        // Implement if necessary
    }
}
