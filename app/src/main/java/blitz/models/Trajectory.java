package blitz.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blitz.models.ControlPoint;
import blitz.models.FollowPoint;
import blitz.models.calculations.Calculations;
import blitz.services.CartesianCoordinate;

public class Trajectory {

    // -=-=-=- FIELDS -=-=-=-

    private String name;
    private ArrayList<ControlPoint> controlPoints;
    private boolean isVisible, isLocked;

    private double spacing;

    private String interpolationType;
    private String splineType;
    



    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Initializes Trajectory with specified name and no curves.
     * 
     * @param name                  name of this Trajectory
     */
    public Trajectory(String name){
        setName(name);
        controlPoints = new ArrayList<ControlPoint>();
        setIsVisible(true);
        setIsLocked(false);
        setSplineType(Calculations.BEZIER_SPLINE);
        setInterpolationType(Calculations.UNIFORM_INTERPOLATION);
        setSpacing(0.5);
    }

    /**
     * Initializes Trajectory as a copy of other.
     * 
     * @param other                 some other Trajectory
     */
    public Trajectory(Trajectory other){
        setName(other.getName());
        controlPoints = other.copyAllControlPoints();
        setIsVisible(true);
        setIsLocked(false);
    }





    // -=-=-=- METHODS -=-=-=-

    public void setInterpolationType(String type){
        if(Calculations.isValidInterpolationType(type)){
            interpolationType = type;
        }
        if(Active.getActiveTrajectory() == this){
            Active.notifyActiveTrajectoryStateEdited();
        }
    }

    public String getInterpolationType(){
        return interpolationType;
    }

    public String getSplineType(){
        return splineType;
    }
    
    public void setSplineType(String type){
        if(Calculations.isValidSplineType(type)){
            splineType = type;
        }
        if(Active.getActiveTrajectory() == this){
            Active.notifyActiveTrajectoryStateEdited();
        }
    }

    public void setSpacing(double d){
        if(0.1 <= d && d < 12){
            spacing = d;
        }
    }

    public double getSpacing(){
        return spacing;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public ControlPoint getFirst(){
        if(controlPoints.isEmpty()) return null;
        return controlPoints.getFirst();
    }

    public ControlPoint getLast(){
        if(controlPoints.isEmpty()) return null;
        return controlPoints.getLast();
    }

    public boolean isEmpty(){
        return controlPoints.isEmpty();
    }

    public void addControlPoint(ControlPoint cp){
        controlPoints.add(cp);
    }

    public void addControlPoint(double x, double y){
        controlPoints.add(new ControlPoint(getNextAvaliableName(), x, y));
    }

    public void addControlPoint(CartesianCoordinate c){
        controlPoints.add(new ControlPoint(getNextAvaliableName(), c.getX(), c.getY()));
    }

    public void insertControlPoint(int index, ControlPoint cp){
        controlPoints.add(index, cp);
    }

    public void removeControlPoint(ControlPoint cp){
        controlPoints.remove(cp);
    }

    public void removeControlPoint(int index){
        controlPoints.remove(index);
    }

    public boolean contains(ControlPoint cp) {
        for (ControlPoint controlPoint : controlPoints) {
            if(controlPoint == cp){
                return true;
            }
        }
        return false;
    }

    public int indexOf(ControlPoint cp){
        return controlPoints.indexOf(cp);
    }

    public ArrayList<ControlPoint> getAllControlPoints(){
        return controlPoints;
    }

    public ArrayList<ControlPoint> copyAllControlPoints(){
        return new ArrayList<ControlPoint>(controlPoints);
    }

    public ControlPoint getControlPoint(int index){
        return controlPoints.get(index);
    }

    public ControlPoint getControlPoint(String name){
        for (ControlPoint controlPoint : controlPoints) {
            if(controlPoint.getName().equals(name)){
                return controlPoint;
            }
        }
        return null;
    }


    
    @SuppressWarnings("unchecked")
    public ArrayList<FollowPoint> calculateFollowPoints() {
        return Calculations.calculate(this);
    }
    
    

    public String getNextAvaliableName(){
        
        String name = "Control Point 1";
        int i = 1;
        boolean nameIsTaken = true;
        while(nameIsTaken) { 
            name = "Control Point " + ++i;
            nameIsTaken = false;
            for (ControlPoint cp : controlPoints)
                if(cp.getName().equals(name))
                    nameIsTaken = true;
        }
        return name;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public int size(){
        return controlPoints.size();
    }
    
}
