package blitz.services;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Table {

    private double minH;
    private double maxH;
    private double minV;
    private double maxV;
    
    // Use NavigableMap (TreeMap) to store the points
    private NavigableMap<Double, Double> table = new TreeMap<>();
    
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

    public void add(Double h, Double v) {
        if (h >= minH && h <= maxH && v >= minV && v <= maxV) {
            table.put(h, v);
        } else {
            throw new IllegalArgumentException("Point is out of bounds");
        }
    }

    

    public Double approximate(Double h) {
        // If the table has a value at h, return it.
        if (table.containsKey(h)) {
            return table.get(h);
        }
    
        // Find the closest lower and higher keys
        Map.Entry<Double, Double> lowerEntry = table.lowerEntry(h);
        Map.Entry<Double, Double> higherEntry = table.higherEntry(h);
    
        // If both entries are null, it means the table is empty, or h is out of bounds.
        if (lowerEntry == null && higherEntry == null) {
            throw new IllegalArgumentException("The value is out of bounds and cannot be approximated.");
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
    
        // Linear interpolation formula
        double t = (h - h1) / (h2 - h1);
        return v1 + t * (v2 - v1);
    }

    
    
    public void remove(Double h) {
        if (table.containsKey(h)) {
            table.remove(h);
        } else {
            throw new IllegalArgumentException("Point does not exist in the table.");
        }
    }
}
