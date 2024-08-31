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

public class InterpolationTypeLine extends AbstractLinePanel implements ActiveListener {

    private JComboBox<String> interpolationTypeComboBox;
    private final String[] CURVE_TYPES = Calculations.ALL_INTERPOLATION_TYPES;

    public InterpolationTypeLine() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel splineTypeLabel = new JLabel("Interpolation:");

        // Initialize the drop-down menu
        interpolationTypeComboBox = new JComboBox<>(CURVE_TYPES);
        Dimension dim = MainFrameConfig.LINE_PANEL_COMBO_BOX_DIMENSITONS;
        interpolationTypeComboBox.setPreferredSize(dim);
        interpolationTypeComboBox.setMaximumSize(dim);
        interpolationTypeComboBox.setMinimumSize(dim);
        interpolationTypeComboBox.addActionListener(e -> {
            if (isInteractable()) {
                String selectedSplineType = (String) interpolationTypeComboBox.getSelectedItem();
                if (selectedSplineType != null) {
                    Trajectory tr = Active.getActiveTrajectory();
                    tr.setInterpolationType(selectedSplineType);
                    Active.notifyActiveControlPointStateEdited();
                }
                Active.notifyActiveTrajectoryStateEdited();
            }
        });

        // Add components using GridBagLayout

        gbc.gridx = 0;
        gbc.gridy = 0;
        Component horizontalStrut = Box.createHorizontalStrut(36);
        add(horizontalStrut, gbc);

        gbc.gridx++;
        add(splineTypeLabel, gbc);

        gbc.gridx++;
        add(interpolationTypeComboBox, gbc);

        displayInteractability();

        Active.addActiveListener(this);
    }

    private void updateComboBox() {
        Trajectory tr = Active.getActiveTrajectory();
        if (tr != null) {
            interpolationTypeComboBox.setSelectedItem(tr.getInterpolationType());
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
        interpolationTypeComboBox.setEnabled(isInteractable);
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
        displayInteractability();
        updateComboBox();
    }

    @Override
    public void activeTrajectoryStateEdited(Trajectory tr) {
    }
}
