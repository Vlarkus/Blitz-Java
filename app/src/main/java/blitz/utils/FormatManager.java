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

package blitz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import blitz.configs.Config;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;

/**
 * Manages different formatting strategies for trajectories.
 * 
 * This class maintains a mapping between format names and their corresponding 
 * formatting functions. It allows formatting a {@link Trajectory} into various 
 * string representations based on the selected format.
 * 
 * Supported Formats:
 * <ul>
 *     <li>LemLib v0.4.0</li>
 * </ul>
 * 
 * @author Valery Rabchanka
 */
public class FormatManager {

    // -=-=-=- FIELDS -=-=-=-

    /**
     * A map that associates format names with their corresponding formatting functions.
     * The key is the format name, and the value is a {@link Function} that takes a 
     * {@link Trajectory} and returns its formatted {@link String} representation.
     */
    private static final Map<String, Function<Trajectory, String>> formatMap = new HashMap<>();

    // Static block to initialize the format mappings
    static {
        formatMap.put("LemLib v0.4.0", FormatManager::LemLib_v_0_4_0);
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Formats the given trajectory using the specified format.
     * 
     * @param tr     the {@link Trajectory} to format
     * @param format the name of the format to apply
     * @return the formatted string representation of the trajectory, or {@code null} 
     *         if the trajectory is {@code null} or the format is not supported
     */
    public static String formatTrajectory(Trajectory tr, String format) {
        if (tr == null) return null;

        Function<Trajectory, String> formatFunction = formatMap.get(format);
        if (formatFunction == null) return null;

        return formatFunction.apply(tr);
    }

    /**
     * Retrieves all available format names.
     * 
     * @return a {@link Set} of all supported format names
     */
    public static Set<String> getAllFormats() {
        return formatMap.keySet();
    }

    // -=-=-=- FORMAT FUNCTIONS -=-=-=-

    /**
     * Formats the trajectory according to the LemLib v0.4.0 specifications.
     * 
     * The format includes:
     * <ul>
     *     <li>Each follow point's x, y coordinates and speed, separated by commas.</li>
     *     <li>An "endData" marker.</li>
     *     <li>Application info from {@link Config#APP_INFO}.</li>
     * </ul>
     * 
     * @param tr the {@link Trajectory} to format
     * @return the formatted string, or {@code null} if the trajectory is invalid
     */
    public static String LemLib_v_0_4_0(Trajectory tr) {

        // Declarations

        Function<Trajectory, Boolean> isValid = (Trajectory t) -> t != null && t.size() >= 2;

        Function<FollowPoint, String> point = (FollowPoint fp) -> {
            return String.format("%.4f, %.4f, %.4f", fp.getX(), fp.getY(), fp.getSpeed());
        };

        // Validation
        if (!isValid.apply(tr)) return null;

        // Formatting
        StringBuilder result = new StringBuilder();

        // Append each follow point
        ArrayList<FollowPoint> followPoints = tr.calculateFollowPoints();
        for (int i = 0; i < followPoints.size(); i++) {
            FollowPoint fp = followPoints.get(i);
            result.append(point.apply(fp));
            if (i < followPoints.size() - 1) {
                result.append(",");
            }
            result.append("\n");
        }

        // Append endData
        result.append("endData\n");

        // Append BLITZ InfoPanel
        result.append(Config.APP_INFO);

        return result.toString();
    }
}
