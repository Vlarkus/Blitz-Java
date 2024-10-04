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

package blitz.models.trajectories.trajectoriesList;

/**
 * Listener interface for receiving notifications when the trajectory list changes.
 * Classes that implement this interface can be registered to be notified whenever 
 * the list of trajectories is modified.
 * 
 * Implementing classes must define how they respond to changes in the trajectory list.
 * 
 * @author Valery Rabchanka
 */
public interface TrajectoriesListListener {

    /**
     * Invoked when the trajectory list has changed.
     * Implementing classes should define the behavior to handle this event.
     */
    public void TrajectoryListChanged();
}
