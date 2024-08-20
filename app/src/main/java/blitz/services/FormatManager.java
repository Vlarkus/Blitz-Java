package blitz.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import blitz.models.ControlPoint;
import blitz.models.Trajectory;

public class FormatManager {
    
    private static Map<String, Function<Trajectory, String>> formatMap = new HashMap<>();

    static {
        formatMap.put("LemLib v0.4.0", FormatManager::LemLib_v_0_4_0);
    }



    public static String formatTrajectory(Trajectory tr, String format){
        if(tr == null) return null;

        Function<Trajectory, String> formatFunction = formatMap.get(format);
        if(formatFunction == null) return null;

        return formatFunction.apply(tr);

    }

    public static Set<String> getAllFormats(){
        return formatMap.keySet();
    }

    // LemLib v0.4.0
    public static String LemLib_v_0_4_0(Trajectory tr){

        // Declarations

        Function<Trajectory, Boolean> isValid = (Trajectory t) -> !(t == null || t.size() < 2);

        Function<CartesianCoordinate, String> point = (CartesianCoordinate p) -> {
            return p.getX() + ", " + p.getY() /* + ", " p.getVoltage() */;
        };
        
        // Validation

        if(isValid.apply(tr)) return null;

        // Formatting

        // result ::= Point+ EndData Deceleration MaxSpeed Multiplier Spline+ Data?
        String result = "";
        

        // Point+
        for (int i = 0; i < tr.size(); i++) {
            ControlPoint cp = tr.getControlPoint(i);
            ArrayList<CartesianCoordinate> points = tr.calculateBezierCurveFrom(cp);
            // TODO: it is likely that the limit is points.size() - 1;
            for (int j = 0; j < points.size(); j++) {
                CartesianCoordinate p = points.get(j);
                result += point.apply(p);
                result += "\n";
            }
        }

        // EndData

        result += "endData\n";

        // Deceleration
        
        
        
        // MaxSpeed
        
        
        
        // Multiplies
        
        
        
        // Spline+
        
        
        
        // Data?

        return result;
    }

}
