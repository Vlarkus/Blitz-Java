/*
 * Copyright 2024 Valery Rabchanka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blitz.models.trajectories;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blitz.models.active.ActiveEntities;
import blitz.models.calculations.Calculations;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;
import blitz.services.CartesianCoordinate;

public class Trajectory {

    // -=-=-=- FIELDS -=-=-=-

    private String name;
    private ArrayList<ControlPoint> controlPoints;
    private boolean isVisible, isLocked;

    private double spacing;
    private double minSpeed;
    private double maxSpeed;
    private double minBentRate;
    private double maxBentRate;

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
        setInterpolationType(Calculations.EQUIDISTANT_INTERPOLATION);
        setSpacing(0.5);
        setMaxSpeed(127);
        setMinSpeed(0);
        setMinBentRate(0);
        setMaxBentRate(1);
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
        setSplineType(other.getSplineType());
        setInterpolationType(other.getInterpolationType());
        setSpacing(other.getSpacing());
        setMaxSpeed(other.getMaxSpeed());
        setMinSpeed(other.getMinSpeed());
        setMinBentRate(other.getMinBentRate());
        setMaxBentRate(other.getMaxBentRate());
    }





    // -=-=-=- METHODS -=-=-=-

    public void setMaxSpeed(double speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("Max speed must be non-negative.");
        }
        if (minSpeed > 0 && speed < minSpeed) {
            throw new IllegalArgumentException("Max speed cannot be less than the minimum speed.");
        }
        maxSpeed = speed;
    }
    
    public double getMaxSpeed() {
        return maxSpeed;
    }
    
    public void setMinSpeed(double speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("Min speed must be non-negative.");
        }
        if (maxSpeed > 0 && speed > maxSpeed) {
            throw new IllegalArgumentException("Min speed cannot be greater than the maximum speed.");
        }
        minSpeed = speed;
    }
    
    public double getMinSpeed() {
        return minSpeed;
    }
    
    public void setMinBentRate(double rate) {
        if (rate < 0 || rate > 1) {
            throw new IllegalArgumentException("Min bent rate must be between 0 and 1.");
        }
        if (maxBentRate > 0 && rate > maxBentRate) {
            throw new IllegalArgumentException("Min bent rate cannot be greater than the maximum bent rate.");
        }
        minBentRate = rate;
    }
    
    public double getMinBentRate() {
        return minBentRate;
    }
    
    public void setMaxBentRate(double rate) {
        if (rate < 0 || rate > 1) {
            throw new IllegalArgumentException("Max bent rate must be between 0 and 1.");
        }
        if (minBentRate > 0 && rate < minBentRate) {
            throw new IllegalArgumentException("Max bent rate cannot be less than the minimum bent rate.");
        }
        maxBentRate = rate;
    }
    
    public double getMaxBentRate() {
        return maxBentRate;
    }
    

    public void setInterpolationType(String type){
        if(Calculations.isValidInterpolationType(type)){
            interpolationType = type;
        }
        if(ActiveEntities.getActiveTrajectory() == this){
            ActiveEntities.notifyActiveTrajectoryStateEdited();
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
        if(ActiveEntities.getActiveTrajectory() == this){
            ActiveEntities.notifyActiveTrajectoryStateEdited();
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


    public ArrayList<FollowPoint> calculateFollowPoints() {
        return Calculations.calculateFollowPoints(this);
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
