package blitz.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blitz.services.CartesianCoordinate;

public class Trajectory {

    // -=-=-=- METHODS -=-=-=-

    private String name;
    private ArrayList<ControlPoint> controlPoints;
    private boolean isVisible, isLocked;

    private static Map<String, Method> splineCalculationMap = new HashMap<>();
    private Method splineCalculationMethod;
    private boolean isContinuous;
    private double distance;
    

    static {
        try {
    //         splineCalculationMap.put("Linear", MethodMapExample.class.getMethod("method1", String.class));
            splineCalculationMap.put("Beziér", Trajectory.class.getMethod("bezierInterpolation", ArrayList.class, int.class));
    //         splineCalculationMap.put("Catmul-Rom", MethodMapExample.class.getMethod("method3", int.class));
    //         splineCalculationMap.put("NURB", MethodMapExample.class.getMethod("method3", int.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }



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
        setSplineType("Beziér");
        setIsContinuous(false);
        setDistance(0.75);
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

    public static String[] getAllSplineNames() {
        return splineCalculationMap.keySet().toArray(new String[0]);
    }
    
    public void setSplineType(String type){
        splineCalculationMethod = splineCalculationMap.get(type);
    }

    public void setIsContinuous(boolean b){
        isContinuous = b;
    }

    public boolean getIsContinuous(){
        return isContinuous;
    }

    public void setDistance(double d){
        if(d < 0.5 || 10 < d){
            distance = 0.75;
        } else {
            distance = d;
        }
    }

    public double getDistance(){
        return distance;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
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



    public ArrayList<CartesianCoordinate> calculateFollowPoints() {
        try {
            return (ArrayList<CartesianCoordinate>) splineCalculationMethod.invoke(null, controlPoints, 20);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    

    public static ArrayList<CartesianCoordinate> bezierInterpolation(ArrayList<ControlPoint> controlPoints, int numInterpolatedPoints) {
        ArrayList<CartesianCoordinate> interpolatedPoints = new ArrayList<>();
    
        for (int i = 0; i < controlPoints.size() - 1; i++) {
            ControlPoint p0 = controlPoints.get(i);
            ControlPoint p1 = controlPoints.get(i + 1);
    
            // Calculate the Bézier curve between p0 and p1
            for (int j = 0; j <= numInterpolatedPoints; j++) {
                double t = (double) j / numInterpolatedPoints;
                interpolatedPoints.add(calculateBezierPoint(t, p0, p1));
            }
        }
    
        return interpolatedPoints;
    }
    
    private static CartesianCoordinate calculateBezierPoint(double t, ControlPoint p0, ControlPoint p1) {
        // Control points and helper points
        CartesianCoordinate startPoint = p0.getPosition();
        CartesianCoordinate controlPoint1 = p0.getAbsStartHelperPos(); // End helper point of p0
        CartesianCoordinate controlPoint2 = p1.getAbsEndHelperPos(); // Start helper point of p1
        CartesianCoordinate endPoint = p1.getPosition();
    
        // Bézier formula using the four points
        double x = Math.pow(1 - t, 3) * startPoint.getX() +
                   3 * Math.pow(1 - t, 2) * t * controlPoint1.getX() +
                   3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getX() +
                   Math.pow(t, 3) * endPoint.getX();
    
        double y = Math.pow(1 - t, 3) * startPoint.getY() +
                   3 * Math.pow(1 - t, 2) * t * controlPoint1.getY() +
                   3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getY() +
                   Math.pow(t, 3) * endPoint.getY();
    
        return new CartesianCoordinate(x, y);
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
