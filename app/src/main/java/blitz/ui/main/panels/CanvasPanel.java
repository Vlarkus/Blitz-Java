package blitz.ui.main.panels;

import java.util.ArrayList;

import javax.swing.JPanel;

import blitz.configs.MainFrameConfig;
import blitz.models.ControlPoint;
import blitz.models.Trajectory;
import blitz.servises.CartesianCoordinate;
import blitz.ui.main.pointers.BezierPointer;
import blitz.ui.main.pointers.ControlPointer;
import blitz.ui.main.pointers.HelperPointer;
import blitz.ui.main.pointers.Pointer.State;

public class CanvasPanel extends JPanel{

    private int xOffset, yOffset;
    private ArrayList<Trajectory> visibleTrajectories;
    private ArrayList<ControlPointer> controlPointers;
    private ArrayList<HelperPointer> helperPointers;
    private ArrayList<BezierPointer> bezierPointers;

    public CanvasPanel(){
        
        setBackground(MainFrameConfig.CANVAS_PANEL_BACKGROUND_COLOR);
        setLayout(null);

        visibleTrajectories = new ArrayList<Trajectory>();
        controlPointers = new ArrayList<ControlPointer>();
        bezierPointers = new ArrayList<BezierPointer>();
        helperPointers = new ArrayList<HelperPointer>();

        setXOffset((int) MainFrameConfig.DEFAULT_OFFSET.getX());
        setYOffset((int) MainFrameConfig.DEFAULT_OFFSET.getY());

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

    public CartesianCoordinate convertFieldToScreenCoordinates(CartesianCoordinate c){
        double x = c.getX();
        x += getXOffset();

        double y = c.getY();
        y += getYOffset();

        return new CartesianCoordinate((int)x, (int)y);
        
    }

    public int getXOffset() {
        return xOffset;
    }
    
    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public ArrayList<Trajectory> getVisibleTrajectories() {
        return visibleTrajectories;
    }

    public void setVisibleTrajectories(ArrayList<Trajectory> visibleTrajectories) {
        this.visibleTrajectories = visibleTrajectories;
        populateControlPointers();//TODO: Delete this line.
        populateBezierPointers();//TODO: Delete this line.
        controlPointers.get(2).setState(State.SELECTED);
        populateHelperPointers();//TODO: Delete this line.
        addAllPointers();//TODO: Delete this line.
        //TODO: Rerender visibleTrajectories.
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


    
}
