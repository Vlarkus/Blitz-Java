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

import java.util.ArrayList;

/**
 * Manages the list of listeners that are notified when visible trajectories change.
 * This class allows listeners to register and be notified of any changes in the visibility
 * of trajectories.
 * 
 * @see VisibleTrajectoriesListener
 * 
 * @author Valery Rabchanka
 */
public class VisibleTrajectories {
    
    // -=-=-=- FIELDS -=-=-=-
    
    private static ArrayList<VisibleTrajectoriesListener> listeners = new ArrayList<>();

    // -=-=-=- METHODS -=-=-=-

    /**
     * Adds a listener to the list of visible trajectories listeners.
     * 
     * @param listener the listener to add
     */
    public static void addVisibleTrajectoriesListener(VisibleTrajectoriesListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of visible trajectories listeners.
     * 
     * @param listener the listener to remove
     */
    public static void removeVisibleTrajectoriesListener(VisibleTrajectoriesListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners that the visible trajectories have changed.
     */
    public static void notifyVisibleTrajectoriesChanged() {
        for (VisibleTrajectoriesListener listener : listeners) {
            listener.visibleTrajectoriesChanged();
        }
    }

}
