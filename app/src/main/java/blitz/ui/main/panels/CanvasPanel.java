package blitz.ui.main.panels;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import blitz.configs.MainFrameConfig;
import blitz.models.ActiveListener;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;
import blitz.servises.CartesianCoordinate;
import blitz.ui.main.pointers.BezierPointer;
import blitz.ui.main.pointers.ControlPointer;
import blitz.ui.main.pointers.HelperPointer;
import blitz.ui.main.pointers.Pointer.State;

public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener, ActiveListener{

    private int mousePreviousX, mousePreviousY;

    private JScrollPane scrollPane;

    private ArrayList<Trajectory> visibleTrajectories;
    private ArrayList<ControlPointer> controlPointers;
    private ArrayList<HelperPointer> helperPointers;
    private ArrayList<BezierPointer> bezierPointers;

    public CanvasPanel(){
        
        setBackground(MainFrameConfig.CANVAS_PANEL_BACKGROUND_COLOR);
        setPreferredSize(MainFrameConfig.CANVAS_PANEL_PREFFERED_DIMENSION);
        setLayout(null);

        visibleTrajectories = new ArrayList<Trajectory>();
        controlPointers = new ArrayList<ControlPointer>();
        bezierPointers = new ArrayList<BezierPointer>();
        helperPointers = new ArrayList<HelperPointer>();

        addMouseListener(this);
        addMouseMotionListener(this);

    }

    private void clearControlPointers(){
        controlPointers = new ArrayList<ControlPointer>();
    }

    private void clearBezierPointers(){
        bezierPointers = new ArrayList<BezierPointer>();
    }

    private void clearHelperPointers(){
        helperPointers = new ArrayList<HelperPointer>();
    }

    private void populateControlPointers(){
        
        clearControlPointers();

        for (Trajectory tr : visibleTrajectories) {
            for (ControlPoint cp : tr.getAllControlPoints()) {

                CartesianCoordinate coordinate = convertFieldToScreenCoordinates(cp.getPosition());
                int x = (int) coordinate.getX();
                int y = (int) coordinate.getY();
                controlPointers.add( new ControlPointer(x, y, cp) );

            }
        }



    }

    private void populateBezierPointers(){

        clearBezierPointers();

        for (Trajectory tr : visibleTrajectories) {
            for (ControlPoint cp : tr.getAllControlPoints()) {
                
                ArrayList<CartesianCoordinate> bezierCoordinates = tr.calculateBezierCurveFrom(cp);
                if(bezierCoordinates == null){
                    continue;
                }

                for (CartesianCoordinate cartesianCoordinate : bezierCoordinates) {
                    CartesianCoordinate coordinate = convertFieldToScreenCoordinates(cartesianCoordinate);
                    int x = (int) coordinate.getX();
                    int y = (int) coordinate.getY();
                    bezierPointers.add(new BezierPointer(x, y, cp));
                }


            }
        }

    }

    private void populateHelperPointers(){

        clearHelperPointers();

        for (ControlPointer p : controlPointers) {
            
            if(p.getState() == State.SELECTED){
                ControlPoint cp = p.getRelatedControlPoint();

                CartesianCoordinate helperStart = convertFieldToScreenCoordinates(cp.getAbsStartHelperPos());
                helperPointers.add(new HelperPointer((int)helperStart.getX(), (int)helperStart.getY(), cp, true));

                CartesianCoordinate endStart = convertFieldToScreenCoordinates(cp.getAbsEndHelperPos());
                helperPointers.add(new HelperPointer((int)endStart.getX(), (int)endStart.getY(), cp, false));
            }

        }

    }

    private void addAllPointers(){

        removeAll();

        for (HelperPointer p : helperPointers) {
            add(p);
        }

        for (ControlPointer p : controlPointers) {
            add(p);
        }

        for (BezierPointer p : bezierPointers) {
            add(p);
        }

        

    }

    public CartesianCoordinate convertFieldToScreenCoordinates(CartesianCoordinate c) {
        double x = c.getX() + MainFrameConfig.CANVAS_PANEL_X_OFFSET;
        double y = -c.getY() + MainFrameConfig.CANVAS_PANEL_Y_OFFSET;
        return new CartesianCoordinate((int) x, (int) y);
    }
    
    public CartesianCoordinate convertScreenToFieldCoordinates(CartesianCoordinate c) {
        double x = c.getX() - MainFrameConfig.CANVAS_PANEL_X_OFFSET;
        double y = -(c.getY() - MainFrameConfig.CANVAS_PANEL_Y_OFFSET);
        return new CartesianCoordinate(x, y);
    }

    public ArrayList<Trajectory> getVisibleTrajectories() {
        return visibleTrajectories;
    }

    public void setVisibleTrajectories(ArrayList<Trajectory> visibleTrajectories) {
        this.visibleTrajectories = visibleTrajectories;
        renderVisibleTrajectories();
        //TODO: Rerender visibleTrajectories.
    }

    public void renderVisibleTrajectories(){
        populateControlPointers();
        populateBezierPointers();
        populateHelperPointers();
        addAllPointers();
        repaint();
    }

    private ArrayList<ControlPointer> getControlPointers() {
        return controlPointers;
    }

    private void setControlPointers(ArrayList<ControlPointer> controlPointers) {
        this.controlPointers = controlPointers;
    }

    private ArrayList<HelperPointer> getHelperPointers() {
        return helperPointers;
    }

    private void setHelperPointers(ArrayList<HelperPointer> helperPointers) {
        this.helperPointers = helperPointers;
    }

    private ArrayList<BezierPointer> getBezierPointers() {
        return bezierPointers;
    }

    private void setBezierPointers(ArrayList<BezierPointer> bezierPointers) {
        this.bezierPointers = bezierPointers;
    }

    public void setScrollPane(JScrollPane p){
        scrollPane = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePreviousX = e.getX();
        mousePreviousY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        calculatePanning(e);
    }

    private void calculatePanning(MouseEvent e){
        JViewport viewport = scrollPane.getViewport();
        if (viewport != null) {
            int dx = e.getX() - mousePreviousX;
            int dy = e.getY() - mousePreviousY;
            Point viewPos = viewport.getViewPosition();
            viewPos.translate(-dx, -dy);

            int maxX = Math.max(0, getWidth() - viewport.getWidth());
            int maxY = Math.max(0, getHeight() - viewport.getHeight());

            if (viewPos.x < 0) viewPos.x = 0;
            if (viewPos.x > maxX) viewPos.x = maxX;
            if (viewPos.y < 0) viewPos.y = 0;
            if (viewPos.y > maxY) viewPos.y = maxY;

            viewport.setViewPosition(viewPos);
        }
    }
    

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void activeTrajectoryChanged(Trajectory tr) {
        System.out.println("CanvasPanel: Active Trajectory Changed!");
    }

    public void activeControlPointChanged(ControlPoint cp) {
        renderVisibleTrajectories();
        System.out.println("CanvasPanel: Active ControlPoint Changed!");

    }
    
}
