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

package blitz.models.active;

import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Listener interface for receiving notifications about changes to the active trajectory and control point.
 * Classes that implement this interface can be registered to be notified when the active trajectory 
 * or control point changes, or when their state is edited.
 * 
 * <p>Listeners can handle the following events:
 * <ul>
 *   <li>When the active trajectory is changed.</li>
 *   <li>When the active control point is changed.</li>
 *   <li>When the state of the active control point is edited.</li>
 *   <li>When the state of the active trajectory is edited.</li>
 * </ul>
 * </p>
 * 
 * @author Valery Rabchanka
 */
public interface ActiveEntitiesListener {
    
    /**
     * Invoked when the active trajectory has changed.
     *
     * @param tr the new active trajectory, or {@code null} if there is no active trajectory
     */
    public void activeTrajectoryChanged(Trajectory tr);

    /**
     * Invoked when the active control point has changed.
     *
     * @param cp the new active control point, or {@code null} if there is no active control point
     */
    public void activeControlPointChanged(ControlPoint cp);

    /**
     * Invoked when the state of the active control point has been edited.
     * 
     * @param cp the active control point that has been edited
     */
    public void activeControlPointStateEdited(ControlPoint cp);

    /**
     * Invoked when the state of the active trajectory has been edited.
     * 
     * @param tr the active trajectory that has been edited
     */
    public void activeTrajectoryStateEdited(Trajectory tr);
}
