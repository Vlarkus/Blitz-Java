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

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Represents a table that maps horizontal values (h) to vertical values (v) within specified bounds.
 * 
 * This class allows adding, removing, and approximating values based on the defined table points.
 * It ensures that all points added to the table are within the specified horizontal and vertical bounds.
 * 
 * <p>
 * Example usage:
 * <pre>
 *     Table table = new Table(0.0, 10.0, 0.0, 100.0);
 *     table.add(2.0, 20.0);
 *     table.add(5.0, 50.0);
 *     table.add(8.0, 80.0);
 *     Double value = table.approximate(6.0); // Returns 60.0
 * </pre>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public class Table {

    // -=-=-=- FIELDS -=-=-=-

    /**
     * The minimum horizontal bound.
     */
    private double minH;

    /**
     * The maximum horizontal bound.
     */
    private double maxH;

    /**
     * The minimum vertical bound.
     */
    private double minV;

    /**
     * The maximum vertical bound.
     */
    private double maxV;

    /**
     * A navigable map that stores horizontal values (h) as keys and vertical values (v) as values.
     * The keys are sorted in natural order to facilitate efficient lookup and interpolation.
     */
    private NavigableMap<Double, Double> table = new TreeMap<>();

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Constructs a {@code Table} with specified horizontal and vertical bounds.
     * 
     * @param minH the minimum horizontal value (must be less than {@code maxH})
     * @param maxH the maximum horizontal value (must be greater than {@code minH})
     * @param minV the minimum vertical value (must be less than {@code maxV})
     * @param maxV the maximum vertical value (must be greater than {@code minV})
     * @throws IllegalArgumentException if {@code minH} is not less than {@code maxH} or
     *                                  if {@code minV} is not less than {@code maxV}
     */
    public Table(double minH, double maxH, double minV, double maxV) {
        if (minH >= maxH) {
            throw new IllegalArgumentException("minH must be less than maxH");
        }
        if (minV >= maxV) {
            throw new IllegalArgumentException("minV must be less than maxV");
        }
        this.minH = minH;
        this.maxH = maxH;
        this.minV = minV;
        this.maxV = maxV;
    }

    // -=-=-=- METHODS -=-=-=-

    // -=-=-=- ADDITIONAL METHODS -=-=-=-

    /**
     * Adds a point to the table at the specified horizontal (h) and vertical (v) values.
     * The point must be within the defined horizontal and vertical bounds.
     * 
     * @param h the horizontal value to add (must be between {@code minH} and {@code maxH} inclusive)
     * @param v the vertical value to add (must be between {@code minV} and {@code maxV} inclusive)
     * @throws IllegalArgumentException if the point is out of the defined bounds
     */
    public void add(Double h, Double v) {
        if (h < minH || h > maxH || v < minV || v > maxV) {
            throw new IllegalArgumentException("Point is out of bounds");
        }
        table.put(h, v);
    }

    /**
     * Removes the point with the specified horizontal (h) value from the table.
     * 
     * @param h the horizontal value of the point to remove
     * @throws IllegalArgumentException if the point does not exist in the table
     */
    public void remove(Double h) {
        if (!table.containsKey(h)) {
            throw new IllegalArgumentException("Point does not exist in the table.");
        }
        table.remove(h);
    }

    /**
     * Approximates the vertical value (v) for a given horizontal value (h) using linear interpolation.
     * 
     * If the exact horizontal value exists in the table, its corresponding vertical value is returned.
     * Otherwise, the method finds the closest lower and higher points and interpolates between them.
     * 
     * @param h the horizontal value to approximate (must be between {@code minH} and {@code maxH} inclusive)
     * @return the approximated vertical value
     * @throws IllegalArgumentException if {@code h} is out of bounds or the table is empty
     */
    public Double approximate(Double h) {
        if (h < minH || h > maxH) {
            throw new IllegalArgumentException("Value must be between minH and maxH.");
        }

        // If the table has a value at h, return it.
        if (table.containsKey(h)) {
            return table.get(h);
        }

        // Find the closest lower and higher keys
        Map.Entry<Double, Double> lowerEntry = table.lowerEntry(h);
        Map.Entry<Double, Double> higherEntry = table.higherEntry(h);

        // If both entries are null, it means the table is empty, or h is out of bounds.
        if (lowerEntry == null && higherEntry == null) {
            throw new IllegalArgumentException("The table is empty; cannot approximate.");
        }

        // If only the lower entry is null, return the value from the higher entry.
        if (lowerEntry == null) {
            return higherEntry.getValue();
        }

        // If only the higher entry is null, return the value from the lower entry.
        if (higherEntry == null) {
            return lowerEntry.getValue();
        }

        // Perform linear interpolation between the two closest points
        double h1 = lowerEntry.getKey();
        double v1 = lowerEntry.getValue();
        double h2 = higherEntry.getKey();
        double v2 = higherEntry.getValue();

        // Avoid division by zero
        if (h2 == h1) {
            return v1;
        }

        // Linear interpolation formula
        double ratio = (h - h1) / (h2 - h1);
        return v1 + ratio * (v2 - v1);
    }

    // -=-=-=- GETTERS AND SETTERS -=-=-=-

    /**
     * Returns the minimum horizontal bound.
     * 
     * @return the minimum horizontal value {@code minH}
     */
    public double getMinH() {
        return minH;
    }

    /**
     * Returns the maximum horizontal bound.
     * 
     * @return the maximum horizontal value {@code maxH}
     */
    public double getMaxH() {
        return maxH;
    }

    /**
     * Returns the minimum vertical bound.
     * 
     * @return the minimum vertical value {@code minV}
     */
    public double getMinV() {
        return minV;
    }

    /**
     * Returns the maximum vertical bound.
     * 
     * @return the maximum vertical value {@code maxV}
     */
    public double getMaxV() {
        return maxV;
    }

    /**
     * Returns a copy of all points in the table.
     * 
     * @return a {@link NavigableMap} containing all horizontal and vertical points
     */
    public NavigableMap<Double, Double> getTable() {
        return new TreeMap<>(table);
    }

    /**
     * Clears all points from the table.
     */
    public void clear() {
        table.clear();
    }

    /**
     * Returns the number of points currently in the table.
     * 
     * @return the size of the table
     */
    public int size() {
        return table.size();
    }

    /**
     * Checks if the table is empty.
     * 
     * @return {@code true} if the table contains no points, {@code false} otherwise
     */
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Returns a string representation of the table, listing all points in order.
     * 
     * @return a string listing all horizontal and vertical points
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Table Points:\n");
        for (Map.Entry<Double, Double> entry : table.entrySet()) {
            sb.append(String.format("h: %.4f, v: %.4f\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
