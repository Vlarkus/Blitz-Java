package blitz.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import blitz.configs.Config;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.FollowPoint;

public class FormatManager {

    private static Map<String, Function<Trajectory, String>> formatMap = new HashMap<>();

    static {
        formatMap.put("LemLib v0.4.0", FormatManager::LemLib_v_0_4_0);
    }

    public static String formatTrajectory(Trajectory tr, String format) {

        if (tr == null) return null;

        Function<Trajectory, String> formatFunction = formatMap.get(format);
        if (formatFunction == null) return null;

        return formatFunction.apply(tr);
    }

    public static Set<String> getAllFormats() {
        return formatMap.keySet();
    }

    // LemLib v0.4.0
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

        // Point+
        ArrayList<FollowPoint> followPoints = tr.calculateFollowPoints();
        for (FollowPoint fp : followPoints) {
            result.append(point.apply(fp));
            if(followPoints.getLast() != fp){
                result.append(",");
            }
            result.append("\n");
        }

        // EndData
        result.append("endData\n");

        // BLITZ InfoPanel

        result.append(Config.APP_INFO);

        return result.toString();
    }
}
