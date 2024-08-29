package blitz.models.calculations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blitz.models.FollowPoint;
import blitz.models.Trajectory;
import blitz.models.calculations.interpolations.EquidistantIntp;
import blitz.models.calculations.interpolations.UniformIntp;
import blitz.models.calculations.splines.BezierSpline;
import blitz.models.calculations.splines.LinearSpline;

public class Calculations {

    private static Map<String, AbstractSpline> SPLINE_MAP = new HashMap<>();
    public static final String BEZIER_SPLINE = "Beziér";
    public static final String LINEAR_SPLINE = "Linear";
    public static final String[] ALL_SPLINE_TYPES = new String[]{BEZIER_SPLINE, LINEAR_SPLINE};
    
    private static Map<String, AbstractInterpolation> INTERPOLATION_MAP = new HashMap<>();
    public static final String EQUIDISTANT_INTERPOLATION = "Equidistant";
    public static final String UNIFORM_INTERPOLATION = "Uniform";
    public static final String[] ALL_INTERPOLATION_TYPES = new String[]{EQUIDISTANT_INTERPOLATION, UNIFORM_INTERPOLATION};

    static {
        SPLINE_MAP.put(LINEAR_SPLINE, new LinearSpline());
        SPLINE_MAP.put(BEZIER_SPLINE, new BezierSpline());
    
        INTERPOLATION_MAP.put(EQUIDISTANT_INTERPOLATION, new EquidistantIntp());
        INTERPOLATION_MAP.put(UNIFORM_INTERPOLATION, new UniformIntp());
    }



    public static boolean isValidInterpolationType(String type) {
        for (String validType : ALL_INTERPOLATION_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidSplineType(String type) {
        for (String validType : ALL_SPLINE_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
    

    public static ArrayList<FollowPoint> calculate(Trajectory tr) {

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
