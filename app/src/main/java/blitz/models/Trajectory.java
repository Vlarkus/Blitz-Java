package blitz.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blitz.services.CartesianCoordinate;

public class Trajectory {

    // -=-=-=- FIELDS -=-=-=-

    private String name;
    private ArrayList<ControlPoint> controlPoints;
    private boolean isVisible, isLocked;

    private static Map<String, Method> splineCalculationMap = new HashMap<>();
    private static final String BEZIER_KEY = "Beziér";
    private static final String LINEAR_KEY = "Linear";
    
    private Method splineCalculationMethod;
    
    private boolean isContinuous;
    private double distance;

    private final int PRECISION = 15;
    

    static {
        try {
            splineCalculationMap.put(LINEAR_KEY, Trajectory.class.getMethod("linearInterpolation"));
            splineCalculationMap.put(BEZIER_KEY, Trajectory.class.getMethod("bezierInterpolation"));
            // Example for method with parameters:
            // splineCalculationMap.put("Catmul-Rom", Trajectory.class.getMethod("catmullRomInterpolation", ParameterType.class));
            // splineCalculationMap.put("NURB", Trajectory.class.getMethod("nurbInterpolation", ParameterType.class));
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
        setIsContinuous(true);
        setDistance(0.5);
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

    public String getSplineType(){
        for (String key : getAllSplineNames()) {
            if(splineCalculationMap.get(key).equals(splineCalculationMethod)){
                return key;
            }
        }
        return null;
    }

    public void setIsContinuous(boolean b){
        isContinuous = b;
    }

    public boolean isContinuous(){
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

    public boolean isTypeBezier(){
        return splineCalculationMethod == splineCalculationMap.get(BEZIER_KEY);
    }

    public boolean isTypeLinear(){
        return splineCalculationMethod == splineCalculationMap.get(LINEAR_KEY);
    }




    
    @SuppressWarnings("unchecked")
    public ArrayList<CartesianCoordinate> calculateFollowPoints() {
        try {
            return (ArrayList<CartesianCoordinate>) splineCalculationMethod.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
        return new ArrayList<>();
    }
    
    


    
    // Beziér Spline
    
    public ArrayList<CartesianCoordinate> bezierInterpolation() {
        ArrayList<CartesianCoordinate> interpolatedPoints = new ArrayList<>();
        
        if(controlPoints.size() <= 1) return interpolatedPoints; 
    
        if(isContinuous) {
        
            if (isContinuous) {
                interpolatedPoints.add(controlPoints.get(0).getPosition());

                double offset = 0.0;
                double accumulatedDistance = 0.0;

                for (int i = 0; i < controlPoints.size() - 1; i++) {
                    ControlPoint p0 = controlPoints.get(i);
                    ControlPoint p1 = controlPoints.get(i + 1);
                    double curveLength = calculateBezierLength(p0, p1, 1.0);

                    for (double d = offset; d <= curveLength; d += distance) {
                        double t = bezierTFromDistance(p0, p1, d);
                        CartesianCoordinate currentPoint = interpolateBezierPoint(t, p0, p1);
                        interpolatedPoints.add(currentPoint);
                        accumulatedDistance += distance;
                    }

                    offset = Math.max((accumulatedDistance - curveLength), 0);
                }

                interpolatedPoints.add(controlPoints.get(controlPoints.size() - 1).getPosition());
            }

    
        } else {
            for (int i = 0; i < controlPoints.size() - 1; i++) {
                ControlPoint p0 = controlPoints.get(i);
                ControlPoint p1 = controlPoints.get(i + 1);
                int numSegments = p0.getNumSegments();
    
                for (int j = 0; j < numSegments; j++) {
                    double t = (double) j / numSegments;
                    interpolatedPoints.add(interpolateBezierPoint(t, p0, p1));
                }
            }
    
            // Manually add the last control point
            if(controlPoints.size() >= 2){
                interpolatedPoints.add(controlPoints.get(controlPoints.size() - 1).getPosition());
            }

        }
    
        return interpolatedPoints;
    }

    public double bezierTFromDistance(ControlPoint p0, ControlPoint p1, double distFromStart) {
        double t = 0.0;
        double delta = 0.5;
        CartesianCoordinate prevPoint = interpolateBezierPoint(0, p0, p1);
    
        for (int i = 0; i < PRECISION; i++) {
            double length = calculateBezierLength(p0, p1, t);

            if( Math.abs(distFromStart-length) < 0.001)
                return t;
    
            if (length < distFromStart)
                t += delta;
            else if (distFromStart < length)
                t -= delta;
            else
                return t;
            delta *= 0.5;
        }
    
        return t;
    }
    
    private static CartesianCoordinate interpolateBezierPoint(double t, ControlPoint p0, ControlPoint p1) {
        CartesianCoordinate startPoint = p0.getPosition();
        CartesianCoordinate controlPoint1 = p0.getAbsEndHelperPos(); // End helper point of p0
        CartesianCoordinate controlPoint2 = p1.getAbsStartHelperPos(); // Start helper point of p1
        CartesianCoordinate endPoint = p1.getPosition();
    
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
        
    public double calculateBezierLength(ControlPoint p0, ControlPoint p1, double t) {
        int steps = 100; // Number of subdivisions to approximate the curve length
        double length = 0.0;
        CartesianCoordinate prevPoint = interpolateBezierPoint(0.0, p0, p1);
    
        for (int i = 1; i <= steps; i++) {
            double currentT = t * ((double) i / steps);
            CartesianCoordinate currentPoint = interpolateBezierPoint(currentT, p0, p1);
            length += prevPoint.distanceTo(currentPoint);
            prevPoint = currentPoint;
        }
    
        return length;
    }


    
    // Linear Spline

    public ArrayList<CartesianCoordinate> linearInterpolation(){
        ArrayList<CartesianCoordinate> interpolatedPoints = new ArrayList<>();
        
        if(controlPoints.size() <= 1) return interpolatedPoints; 
    
        if(isContinuous) {
            interpolatedPoints.add(controlPoints.get(0).getPosition());

                double offset = 0.0;
                double accumulatedDistance = 0.0;

                for (int i = 0; i < controlPoints.size() - 1; i++) {
                    ControlPoint p0 = controlPoints.get(i);
                    ControlPoint p1 = controlPoints.get(i + 1);
                    double curveLength = p0.getPosition().distanceTo(p1.getPosition());

                    for (double d = offset; d <= curveLength; d += distance) {
                        double t = linearTFromDistance(p0, p1, d);
                        CartesianCoordinate currentPoint = interpolateLinearPoint(t, p0, p1);
                        interpolatedPoints.add(currentPoint);
                        accumulatedDistance += distance;
                    }

                    offset = Math.max((accumulatedDistance - curveLength), 0);
                }

                interpolatedPoints.add(controlPoints.get(controlPoints.size() - 1).getPosition());

        } else {
            for (int i = 0; i < controlPoints.size() - 1; i++) {
                ControlPoint p0 = controlPoints.get(i);
                ControlPoint p1 = controlPoints.get(i + 1);
                int numSegments = p0.getNumSegments();
    
                for (int j = 0; j < numSegments; j++) {
                    double t = (double) j / numSegments;
                    interpolatedPoints.add(interpolateLinearPoint(t, p0, p1));
                }
            }
    
            // Manually add the last control point
            if(controlPoints.size() >= 2){
                interpolatedPoints.add(controlPoints.get(controlPoints.size() - 1).getPosition());
            }
        }

        return interpolatedPoints;
    }

    public CartesianCoordinate interpolateLinearPoint(double t, ControlPoint p0, ControlPoint p1) {
        // Ensure t is within the range [0, 1]
        t = Math.max(0, Math.min(1, t));
    
        // Calculate the interpolated x and y values
        double x = p0.getPosition().getX() + t * (p1.getPosition().getX() - p0.getPosition().getX());
        double y = p0.getPosition().getY() + t * (p1.getPosition().getY() - p0.getPosition().getY());
    
        // Return the interpolated point as a new CartesianCoordinate
        return new CartesianCoordinate(x, y);
    }
    
    public double linearTFromDistance(ControlPoint p0, ControlPoint p1, double distFromStart) {
        double t = 0.0;
        double delta = 0.5;  // Initial delta for binary search
        CartesianCoordinate prevPoint = interpolateLinearPoint(0, p0, p1);
    
        for (int i = 0; i < PRECISION; i++) {
            double length = calculateLinearLength(p0, p1, t);

            if( Math.abs(distFromStart-length) < 0.001)
                return t;
    
            if (length < distFromStart)
                t += delta;
            else if (distFromStart < length)
                t -= delta;
            else
                return t;
            
            delta *= 0.5;  // Halve the delta for the next iteration
        }
    
        return t;
    }

    public double calculateLinearLength(ControlPoint p0, ControlPoint p1, double t) {
        CartesianCoordinate startPoint = interpolateLinearPoint(0.0, p0, p1);
        CartesianCoordinate currentPoint = interpolateLinearPoint(t, p0, p1);
    
        return startPoint.distanceTo(currentPoint);
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
