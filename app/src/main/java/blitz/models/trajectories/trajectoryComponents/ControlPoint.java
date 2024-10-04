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

package blitz.models.trajectories.trajectoryComponents;

import java.util.HashMap;
import java.util.Map;

import blitz.configs.Config;
import blitz.services.CartesianCoordinate;
import blitz.services.PolarCoordinate;
import blitz.services.Utils;

public final class ControlPoint {
    
    // -=-=-=- FIELDS -=-=-=-

    private String name;

    // Control Point position on the field
    private double x, y;

    // Helpers relative position
    private double rStart, thetaStart, rEnd, thetaEnd;

    // Number of segments of this curve, minimum look ahead distance
    private int numSegments;

    // Time the robot reaches this segment at
    private double time;

    private boolean isLocked;

    private static Map<String, SYMMETRY> symmetryMap = new HashMap<>();
    public static final String BROKEN_SYMMETRY_KEY = "Broken";
    public static final String ALIGNED_SYMMETRY_KEY = "Aligned";
    public static final String MIRRORED_SYMMETRY_KEY = "Mirrored";
    public static final String[] ALL_INTERPOLATION_TYPES = new String[]{BROKEN_SYMMETRY_KEY, ALIGNED_SYMMETRY_KEY, MIRRORED_SYMMETRY_KEY};
    private SYMMETRY symmetryType;
    public enum SYMMETRY{
        BROKEN,
        ALIGNED,
        MIRRORED
    }

    static{
        symmetryMap.put(BROKEN_SYMMETRY_KEY, SYMMETRY.BROKEN);
        symmetryMap.put(ALIGNED_SYMMETRY_KEY, SYMMETRY.ALIGNED);
        symmetryMap.put(MIRRORED_SYMMETRY_KEY, SYMMETRY.MIRRORED);
    }

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Instantiates Control Point with specified parameters.
     * 
     * @param name  name of Control Point
     * @param x     x position of Control Point
     * @param y     y position of Control Point
     * @param rS    r of Start Helper Point
     * @param tS    theta of Start Helper Point
     * @param rE    r of End Helper Point
     * @param tE    theta of End Helper Point
     */
    public ControlPoint(String name, double x, double y, double rS, double tS, double rE, double tE, int numSegments, double time) {
        setName(isValidName(name)? name : "ControlPoint");
        setPosition(x, y);
        setRStart(rS);
        setThetaStart(tS);
        setREnd(rE);
        setThetaEnd(tE);
        setNumSegments(isValidNumSegments(numSegments)? numSegments : Config.CONTROL_POINT_MIN_NUM_SEGMENTS);
        setTime(isValidTime(time)? time : Config.CONTROL_POINT_MIN_TIME);
        setIsLocked(false);
        setSymmetryType(ControlPoint.MIRRORED_SYMMETRY_KEY);
    }

    public ControlPoint(String name, double x, double y, double rS, double tS, double rE, double tE) {
        this(name, x, y, rS, tS, rE, tE, Config.CONTROL_POINT_DEFAULT_NUM_SEGMENTS, Config.CONTROL_POINT_DEFAULT_TIME);
    }

    public ControlPoint(String name, double x, double y, double tS, double tE) {
        this(name, x, y, Config.CONTROL_POINT_DEFAULT_R_START, tS, Config.CONTROL_POINT_DEFAULT_R_END, tE, Config.CONTROL_POINT_DEFAULT_NUM_SEGMENTS, Config.CONTROL_POINT_DEFAULT_TIME);
    }

    public ControlPoint(String name, double x, double y, int numSegments, double time) {
        this(name, x, y, Config.CONTROL_POINT_DEFAULT_R_START, Config.CONTROL_POINT_DEFAULT_THETA_START, Config.CONTROL_POINT_DEFAULT_R_END, Config.CONTROL_POINT_DEFAULT_THETA_END, numSegments, time);
    }

    public ControlPoint(String name, double x, double y) {
        this(name, x, y, Config.CONTROL_POINT_DEFAULT_R_START, Config.CONTROL_POINT_DEFAULT_THETA_START, Config.CONTROL_POINT_DEFAULT_R_END, Config.CONTROL_POINT_DEFAULT_THETA_END, Config.CONTROL_POINT_DEFAULT_NUM_SEGMENTS, Config.CONTROL_POINT_DEFAULT_TIME);
    }

    public ControlPoint(String name) {
        this(name, Config.CONTROL_POINT_DEFAULT_X, Config.CONTROL_POINT_DEFAULT_Y, Config.CONTROL_POINT_DEFAULT_R_START, Config.CONTROL_POINT_DEFAULT_THETA_START, Config.CONTROL_POINT_DEFAULT_R_END, Config.CONTROL_POINT_DEFAULT_THETA_END, Config.CONTROL_POINT_DEFAULT_NUM_SEGMENTS, Config.CONTROL_POINT_DEFAULT_TIME);
    }

    public ControlPoint(ControlPoint cp) {
        this(cp.getName(), cp.getX(), cp.getY(), cp.getRStart(), cp.getThetaStart(), cp.getREnd(), cp.getThetaEnd(), cp.getNumSegments(), cp.getTime());
    }

    // -=-=-=- METHODS -=-=-=-

    // -=- Control Point -=-

    /**
     * Sets the name of the Control Point.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        if(isValidName(name))
            this.name = name;
    }

    /**
     * Returns the name of the Control Point.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    public boolean isValidName(String name){
        if(name != null && !name.isEmpty()){
            return true;
        }
        return false;
    }

    public SYMMETRY getSymmetryType(){
        return symmetryType;
    }

    public void setSymmetryType(String symmetryType){
        this.symmetryType = symmetryMap.get(symmetryType);
        setRStart(getRStart());
        setThetaStart(getThetaStart());
    }

    public int getNumSegments() {
        return numSegments;
    }

    public boolean isValidNumSegments(int numSegments){
        if(Config.CONTROL_POINT_MIN_NUM_SEGMENTS <= numSegments && numSegments <= Config.CONTROL_POINT_MAX_NUM_SEGMENTS){
            return true;
        }
        return false;
    }

    public void setNumSegments(int numSegments) {
        if(isValidNumSegments(numSegments))
            this.numSegments = numSegments;
    }

    public double getTime() {
        return time;
    }

    public boolean isValidTime(double time){
        if(Config.CONTROL_POINT_MIN_TIME <= time){
            return true;
        }
        return false;
    }

    public void setTime(double time) {
        if(isValidTime(time))
            this.time = time;
    }

    /**
     * Sets the x and y position of the Control Point.
     * 
     * @param x x position
     * @param y y position
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x position of the Control Point.
     * 
     * @param x x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y position of the Control Point.
     * 
     * @param y y position
     */
    public void setY(double y) {
        this.y = y;
    }

    public CartesianCoordinate getPosition(){
        return new CartesianCoordinate(x, y);
    }

    /**
     * Returns the x position of the Control Point.
     * 
     * @return x position
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y position of the Control Point.
     * 
     * @return y position
     */
    public double getY() {
        return y;
    }

    // -=- Start Helper Point -=-

    /**
     * Sets the radial distance of the Start Helper Point from the Control Point.
     * 
     * @param r radial distance
     */
    public void setRStart(double r) {
        this.rStart = r;
        if(symmetryType == SYMMETRY.MIRRORED){
            rEnd = r;
        }
    }

    /**
     * Sets the angular position of the Start Helper Point from the Control Point.
     * 
     * @param theta angular position
     */
    public void setThetaStart(double theta) {
        this.thetaStart = Utils.normalizeAngle(theta);
        if(symmetryType == SYMMETRY.ALIGNED || symmetryType == SYMMETRY.MIRRORED){
            thetaEnd = Utils.normalizeAngle(theta+180);
        }
    }

    /**
     * Sets the Start Helper Point position relative to the Control Point.
     * 
     * @param x x position relative to Control Point
     * @param y y position relative to Control Point
     */
    public void setRelStartHelperPos(double x, double y) {
        PolarCoordinate p = Utils.cartesianToPolar(x, y);
        setRStart(p.getR());
        setThetaStart(p.getTheta());
    }

    /**
     * Sets the Start Helper Point position relative to the field.
     * 
     * @param x absolute x position on the field
     * @param y absolute y position on the field
     */
    public void setAbsStartHelperPos(double x, double y) {
        setRelStartHelperPos((x - this.x), (y - this.y));
    }

    /**
     * Returns the relative position of the Start Helper Point in Cartesian coordinates.
     * 
     * @return relative Cartesian coordinates of the Start Helper Point
     */
    public CartesianCoordinate getRelStartHelperPos() {
        return Utils.polarToCartesian(rStart, thetaStart);
    }

    /**
     * Returns the absolute position of the Start Helper Point in Cartesian coordinates.
     * 
     * @return absolute Cartesian coordinates of the Start Helper Point
     */
    public CartesianCoordinate getAbsStartHelperPos() {
        CartesianCoordinate relStart = getRelStartHelperPos();
        double absX = this.x + relStart.getX();
        double absY = this.y + relStart.getY();
        return new CartesianCoordinate(absX, absY);
    }

    // -=- End Helper Point -=-

    /**
     * Sets the radial distance of the End Helper Point from the Control Point.
     * 
     * @param r radial distance
     */
    public void setREnd(double r) {
        this.rEnd = r;
        if(symmetryType == SYMMETRY.MIRRORED){
            rStart = r;
        }
    }

    /**
     * Sets the angular position of the End Helper Point from the Control Point.
     * 
     * @param theta angular position
     */
    public void setThetaEnd(double theta) {
        this.thetaEnd = Utils.normalizeAngle(theta);
        if(symmetryType == SYMMETRY.ALIGNED || symmetryType == SYMMETRY.MIRRORED){
            thetaStart = Utils.normalizeAngle(theta+180);
        }
    }

    /**
     * Sets the End Helper Point position relative to the Control Point.
     * 
     * @param x x position relative to Control Point
     * @param y y position relative to Control Point
     */
    public void setRelEndHelperPos(double x, double y) {
        PolarCoordinate p = Utils.cartesianToPolar(x, y);
        setREnd(p.getR());
        setThetaEnd(p.getTheta());
    }

    /**
     * Sets the End Helper Point position relative to the field.
     * 
     * @param x absolute x position on the field
     * @param y absolute y position on the field
     */
    public void setAbsEndHelperPos(double x, double y) {
        setRelEndHelperPos((x - this.x), (y - this.y));
    }

    /**
     * Returns the relative position of the End Helper Point in Cartesian coordinates.
     * 
     * @return relative Cartesian coordinates of the End Helper Point
     */
    public CartesianCoordinate getRelEndHelperPos() {
        return Utils.polarToCartesian(rEnd, thetaEnd);
    }

    /**
     * Returns the absolute position of the End Helper Point in Cartesian coordinates.
     * 
     * @return absolute Cartesian coordinates of the End Helper Point
     */
    public CartesianCoordinate getAbsEndHelperPos() {
        CartesianCoordinate relEnd = getRelEndHelperPos();
        double absX = this.x + relEnd.getX();
        double absY = this.y + relEnd.getY();
        return new CartesianCoordinate(absX, absY);
    }

    public double getRStart() {
        return rStart;
    }

    public double getThetaStart() {
        return thetaStart;
    }

    public double getREnd() {
        return rEnd;
    }

    public double getThetaEnd() {
        return thetaEnd;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
