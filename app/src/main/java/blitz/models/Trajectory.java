package blitz.models;

import java.util.ArrayList;

public class Trajectory {

    // -=-=-=- METHODS -=-=-=-

    private String name;
    private ArrayList<ControlPoint> controlPoints;





    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Initializes Trajectory with specified name and no curves.
     * 
     * @param name                  name of this Trajectory
     */
    public Trajectory(String name){
        setName(name);
        controlPoints = new ArrayList<ControlPoint>();
    }

    /**
     * Initializes Trajectory as a copy of other.
     * 
     * @param other                 some other Trajectory
     */
    public Trajectory(Trajectory other){
        setName(other.getName());
        controlPoints = other.copyControlPoints();
    }





    // -=-=-=- METHODS -=-=-=-

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addControlPoint(ControlPoint cp){
        controlPoints.add(cp);
    }

    
    public void insertControlPoint(int index, ControlPoint cp){
        controlPoints.add(index, cp);
    }

    public boolean contains(ControlPoint cp) {
        for (ControlPoint controlPoint : controlPoints) {
            if(controlPoint == cp){
                return true;
            }
        }
        return false;
    }

    public ArrayList<ControlPoint> getAllControlPoints(){
        return controlPoints;
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

    public ArrayList<ControlPoint> copyControlPoints(){
        return new ArrayList<ControlPoint>(controlPoints);
    }
    
}
