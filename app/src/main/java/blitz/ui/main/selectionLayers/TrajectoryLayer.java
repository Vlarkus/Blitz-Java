package blitz.ui.main.selectionLayers;

import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import blitz.models.Trajectory;

public class TrajectoryLayer extends JPanel{
    
    private Trajectory relatedTrajectory;
    private ArrayList<ControlPointLayer> controlPointLayers;
    private JButton activeButton; // Set/show active trajectory
    private JButton visibilityButton; // Set visible on/off
    private JButton lockButton; // Lock all ControlPoints
    private JButton bringUpButton; // Bring trajectory up
    private JButton bringDownButton; // Bring trajectory down
    private JButton collapseButton; // Hide/show all ControlPoints
    private JLabel indexLabel; // Display trajectory's index in the trajectoriesList
    private JLabel nameLabel; // Display name
    private TextField namTextField; // Edit name

    public TrajectoryLayer(Trajectory relatedTrajectory){
        this.relatedTrajectory = relatedTrajectory;
        // TODO: fill the rest
    }

}
