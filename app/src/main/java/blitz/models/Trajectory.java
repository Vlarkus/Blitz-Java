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
import blitz.models.Trajectory.INTERPOLATION_TYPE;
import blitz.services.CartesianCoordinate;

public class Trajectory {

    // -=-=-=- FIELDS -=-=-=-

    private String name;
    private ArrayList<ControlPoint> controlPoints;
    private boolean isVisible, isLocked;

    private Method splineMethod;

    private static Map<String, Method> splineMap = new HashMap<>();
    public static final String BEZIER_SPLINE_KEY = "Beziér";
    public static final String LINEAR_SPLINE_KEY = "Linear";
    public static final String[] ALL_SPLINE_TYPES = new String[]{BEZIER_SPLINE_KEY, LINEAR_SPLINE_KEY};
    

    private INTERPOLATION_TYPE interpolationType;
    private static Map<String, INTERPOLATION_TYPE> interpolationMap = new HashMap<>();
    public static final String EQUIDISTANT_INTERPOLATION_KEY = "Equidistant";
    public static final String UNIFORM_INTERPOLATION_KEY = "Uniform";
    public static final String[] ALL_INTERPOLATION_TYPES = new String[]{EQUIDISTANT_INTERPOLATION_KEY, UNIFORM_INTERPOLATION_KEY};
    public static enum INTERPOLATION_TYPE{
        EQUIDISTANT,
        UNIFORM
    }

    private double distance;

    private final int PRECISION_STEPS = 15;
    private final int LENGTH_PRECISION = 100;
    private final double PRECISION_ACCURACY = 0.001;
    

    static {
        // Spline Method
        try {
            splineMap.put(LINEAR_SPLINE_KEY, Trajectory.class.getMethod("linearInterpolation"));
            splineMap.put(BEZIER_SPLINE_KEY, Trajectory.class.getMethod("bezierInterpolation"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // Interpolation Type
        interpolationMap.put(EQUIDISTANT_INTERPOLATION_KEY, INTERPOLATION_TYPE.EQUIDISTANT);
        interpolationMap.put(UNIFORM_INTERPOLATION_KEY, INTERPOLATION_TYPE.UNIFORM);
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
        setInterpolationType(EQUIDISTANT_INTERPOLATION_KEY);
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

    public void setInterpolationType(String type){
        if(interpolationMap.containsKey(type)){
            interpolationType = interpolationMap.get(type);
        }
    }

    public boolean isInterpolationEquidistant(){
        return interpolationType == INTERPOLATION_TYPE.EQUIDISTANT;
    }

    public boolean isInterpolationUniform(){
        return interpolationType == INTERPOLATION_TYPE.UNIFORM;
    }

    public INTERPOLATION_TYPE getIntrInterpolationType(){
        return interpolationType;
    }
    
    public void setSplineType(String type){
        splineMethod = splineMap.get(type);
        if(Active.getActiveTrajectory() == this){
            Active.notifyActiveTrajectoryStateEdited();
        }
    }

    public void setDistance(double d){
        if(0.1 <= d && d < 12){
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

    public boolean isTypeBezier(){
        return splineMethod == splineMap.get(BEZIER_SPLINE_KEY);
    }

    public boolean isTypeLinear(){
        return splineMethod == splineMap.get(LINEAR_SPLINE_KEY);
    }




    
    @SuppressWarnings("unchecked")
    public ArrayList<FollowPoint> calculateFollowPoints() {
        try {
            return (ArrayList<FollowPoint>) splineMethod.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
        return new ArrayList<>();
    }
    
    


    
    // Beziér Spline
    
    public ArrayList<FollowPoint> bezierInterpolation() {
        ArrayList<FollowPoint> interpolatedPoints = new ArrayList<>();
    
        if(controlPoints.size() <= 1) return interpolatedPoints; 
    
        switch (interpolationType) {
            case EQUIDISTANT:



                ControlPoint first = controlPoints.get(0);
                interpolatedPoints.add(new FollowPoint(first.getPosition(), first));
        
                double offset = 0.0; // Initialize offset
                double accumulatedDistance = 0.0; // Initialize accumulatedDistance
        
                for (int i = 0; i < controlPoints.size() - 1; i++) {
                    ControlPoint p0 = controlPoints.get(i);
                    ControlPoint p1 = controlPoints.get(i + 1);
                    double curveLength = calculateBezierLength(p0, p1, 1.0);
                    double prevT = 0;
        
                    for (double d = offset; d <= curveLength; d += distance) {
                        double currT = bezierTFromDistance(p0, p1, curveLength, d, prevT, 1);
                        CartesianCoordinate currentPoint = interpolateBezierPoint(currT, p0, p1);
                        interpolatedPoints.add(new FollowPoint(currentPoint, p0));
                        accumulatedDistance += distance;
                        prevT = currT;
                    }
        
                    // Calculate the correct offset for the next iteration
                    offset = accumulatedDistance - curveLength;
                    accumulatedDistance = offset;
                }
        
                ControlPoint lastEquidistant = controlPoints.get(controlPoints.size() - 1);
                interpolatedPoints.add(new FollowPoint(lastEquidistant.getPosition(), lastEquidistant));

                break;



            case UNIFORM:



                for (int i = 0; i < controlPoints.size() - 1; i++) {
                    ControlPoint p0 = controlPoints.get(i);
                    ControlPoint p1 = controlPoints.get(i + 1);
                    int numSegments = p0.getNumSegments();
        
                    for (int j = 0; j < numSegments; j++) {
                        double t = (double) j / numSegments;
                        interpolatedPoints.add(new FollowPoint(interpolateBezierPoint(t, p0, p1), p0));
                    }
                }
        
                // Manually add the last control point
                if(controlPoints.size() >= 2){
                    ControlPoint lastUniform = controlPoints.get(controlPoints.size() - 1);
                    interpolatedPoints.add(new FollowPoint(lastUniform.getPosition(), lastUniform));
                }

                break;
        }
    
        return interpolatedPoints;
    }
    

    public double bezierTFromDistance(ControlPoint p0, ControlPoint p1, double curveLength, double distFromStart,  double startT, double endT) {

        if(distFromStart == 0) return 0;

        int counter = PRECISION_STEPS;
        double currT = (startT + endT) / 2.0;
        double length = currT * curveLength;
        while (0.001 < Math.abs(distFromStart-length) && 0 < counter--) { 
            length = currT * curveLength;
            if (length < distFromStart)
                startT = currT;
            else if (length > distFromStart)
                endT = currT;
            else
                break;
            currT = (startT + endT) / 2.0;
        }
    
        return currT;
    }
    
    private static CartesianCoordinate interpolateBezierPoint(double t, ControlPoint p0, ControlPoint p1) {
        CartesianCoordinate startPoint = p0.getPosition();
        CartesianCoordinate controlPoint1 = p0.getAbsStartHelperPos(); // End helper point of p0
        CartesianCoordinate controlPoint2 = p1.getAbsEndHelperPos(); // Start helper point of p1
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
        int steps = LENGTH_PRECISION; // Number of subdivisions to approximate the curve length
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

    public ArrayList<FollowPoint> linearInterpolation(){
        ArrayList<FollowPoint> interpolatedPoints = new ArrayList<>();
        
        if(controlPoints.size() <= 1) return interpolatedPoints; 
    
        switch (interpolationType) {
            case EQUIDISTANT:



                ControlPoint first = controlPoints.get(0);
                interpolatedPoints.add(new FollowPoint(first.getPosition(), first));

                    double offset = 0.0;
                    double accumulatedDistance = 0.0;

                    for (int i = 0; i < controlPoints.size() - 1; i++) {
                        ControlPoint p0 = controlPoints.get(i);
                        ControlPoint p1 = controlPoints.get(i + 1);
                        double curveLength = p0.getPosition().distanceTo(p1.getPosition());

                        for (double d = offset; d <= curveLength; d += distance) {
                            double t = linearTFromDistance(p0, p1, d);
                            CartesianCoordinate currentPoint = interpolateLinearPoint(t, p0, p1);
                            interpolatedPoints.add(new FollowPoint(currentPoint, p0));
                            accumulatedDistance += distance;
                        }

                        offset = Math.max((accumulatedDistance - curveLength), 0);
                        accumulatedDistance = offset;

                    }

                    ControlPoint lastEquidistant = controlPoints.get(controlPoints.size() - 1);
                    interpolatedPoints.add(new FollowPoint(lastEquidistant.getPosition(), lastEquidistant));

                    break;




            case UNIFORM:



                for (int i = 0; i < controlPoints.size() - 1; i++) {
                    ControlPoint p0 = controlPoints.get(i);
                    ControlPoint p1 = controlPoints.get(i + 1);
                    int numSegments = p0.getNumSegments();
        
                    for (int j = 0; j < numSegments; j++) {
                        double t = (double) j / numSegments;
                        interpolatedPoints.add(new FollowPoint(interpolateLinearPoint(t, p0, p1), p0));
                    }
                }
        
                if(controlPoints.size() >= 2){
                    ControlPoint lastUniform = controlPoints.get(controlPoints.size() - 1);
                    interpolatedPoints.add(new FollowPoint(lastUniform.getPosition(), lastUniform));
                }

                break;

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
    
        for (int i = 0; i < PRECISION_STEPS; i++) {
            double length = calculateLinearLength(p0, p1, t);

            if( Math.abs(distFromStart-length) < PRECISION_ACCURACY)
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
