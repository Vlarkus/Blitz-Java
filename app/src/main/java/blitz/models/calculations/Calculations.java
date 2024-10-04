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

package blitz.models.calculations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blitz.models.calculations.interpolations.EquidistantIntp;
import blitz.models.calculations.interpolations.FixedAmountIntp;
import blitz.models.calculations.interpolations.UniformIntp;
import blitz.models.calculations.splines.BezierSpline;
import blitz.models.calculations.splines.LinearSpline;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;

/**
 * The {@code Calculations} class provides methods to calculate follow points for a given trajectory 
 * based on various spline and interpolation types. It manages the mapping of different spline and 
 * interpolation types and ensures that the trajectory uses valid types before performing calculations.
 * 
 * The class supports:
 * <ul>
 * <li>Bezier and Linear splines.</li>
 * <li>Equidistant, Uniform, and Fixed Amount interpolation types.</li>
 * </ul>
 * 
 * @see AbstractSpline
 * @see AbstractInterpolation
 * @see Trajectory
 * @see FollowPoint
 * 
 * @author Valery Rabchanka
 */
public class Calculations {

    // Map for storing available spline types
    private static Map<String, AbstractSpline> SPLINE_MAP = new HashMap<>();
    public static final String BEZIER_SPLINE = "Beziér";
    public static final String LINEAR_SPLINE = "Linear";
    public static final String[] ALL_SPLINE_TYPES = new String[]{BEZIER_SPLINE, LINEAR_SPLINE};
    
    // Map for storing available interpolation types
    private static Map<String, AbstractInterpolation> INTERPOLATION_MAP = new HashMap<>();
    public static final String EQUIDISTANT_INTERPOLATION = "Equidistant";
    public static final String UNIFORM_INTERPOLATION = "Uniform";
    public static final String FIXED_SPACING_INTERPOLATION = "Fixed Amount";
    public static final String[] ALL_INTERPOLATION_TYPES = new String[]{EQUIDISTANT_INTERPOLATION, UNIFORM_INTERPOLATION, FIXED_SPACING_INTERPOLATION};

    // Static block to initialize spline and interpolation mappings
    static {
        SPLINE_MAP.put(LINEAR_SPLINE, new LinearSpline());
        SPLINE_MAP.put(BEZIER_SPLINE, new BezierSpline());
    
        INTERPOLATION_MAP.put(EQUIDISTANT_INTERPOLATION, new EquidistantIntp());
        INTERPOLATION_MAP.put(UNIFORM_INTERPOLATION, new UniformIntp());
        INTERPOLATION_MAP.put(FIXED_SPACING_INTERPOLATION, new FixedAmountIntp());
    }

    /**
     * Checks if the provided interpolation type is valid by comparing it with the available interpolation types.
     * 
     * @param type the interpolation type to check
     * @return {@code true} if the interpolation type is valid, otherwise {@code false}
     */
    public static boolean isValidInterpolationType(String type) {
        for (String validType : ALL_INTERPOLATION_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the provided spline type is valid by comparing it with the available spline types.
     * 
     * @param type the spline type to check
     * @return {@code true} if the spline type is valid, otherwise {@code false}
     */
    public static boolean isValidSplineType(String type) {
        for (String validType : ALL_SPLINE_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calculates the list of follow points for a given trajectory based on the interpolation 
     * and spline type set in the trajectory. If the trajectory has invalid types or lacks sufficient 
     * control points, the method returns {@code null}.
     * 
     * @param tr the trajectory for which to calculate follow points
     * @return an {@link ArrayList} of {@link FollowPoint} objects representing the calculated follow points, or {@code null} if calculation fails
     */
    public static ArrayList<FollowPoint> calculateFollowPoints(Trajectory tr) {

        if (tr == null || tr.size() < 2) return null;

        String interpolationType = tr.getInterpolationType();
        if (!isValidInterpolationType(interpolationType)) return null;

        String splineType = tr.getSplineType();
        if (!isValidSplineType(splineType)) return null;

        AbstractSpline splineObj = SPLINE_MAP.get(splineType);
        AbstractInterpolation intpObj = INTERPOLATION_MAP.get(interpolationType);
        
        try {
            return intpObj.calculate(tr, splineObj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
    
}
