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

package blitz.models.trajectories.visibleTrajectories;

/**
 * Listener interface for receiving notifications when the visible trajectories change.
 * Implementing classes will be notified whenever there is a change in the visibility 
 * of trajectories.
 * 
 * @author Valery Rabchanka
 */
public interface VisibleTrajectoriesListener {

    /**
     * Invoked when the visible trajectories have changed.
     * Implementing classes should define how they handle changes in visible trajectories.
     */
    public void visibleTrajectoriesChanged();
}
