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

import java.util.ArrayList;

import blitz.models.active.ActiveEntities;
import blitz.models.trajectories.Trajectory;
import blitz.models.trajectories.trajectoryComponents.ControlPoint;

/**
 * Manages a list of trajectories and provides functionality to modify, 
 * add, remove, and notify listeners about changes to the trajectory list.
 * 
 * This class maintains the list of {@link Trajectory} objects and handles 
 * operations such as cutting a trajectory, moving trajectories up or down, 
 * and notifying listeners when the trajectory list changes.
 * 
 * @see Trajectory
 * @see ControlPoint
 * @see TrajectoriesListListener
 * @see ActiveEntities
 * 
 * @author Valery Rabchanka
 */
public class TrajectoriesList {

    // -=-=-=- FIELDS -=-=-=-

    /**
     * The list of all trajectories.
     */
    private static ArrayList<Trajectory> trajectoriesList = new ArrayList<>();

    /**
     * The list of listeners for changes in the trajectory list.
     */
    private static ArrayList<TrajectoriesListListener> listeners = new ArrayList<>();


    // -=-=-=- METHODS -=-=-=-

    /**
     * Cuts the trajectory at the specified control point, splitting it into two separate trajectories.
     * 
     * @param cp the control point at which to cut the trajectory
     */
    public static void cutTrajectoryAtControlPoint(ControlPoint cp) {

        if (cp == null || cp.isLocked()) {
            return;
        }
    
        Trajectory tr1 = getTrajectoryByControlPoint(cp);
    
        if (tr1 == null || tr1.isLocked()) {
            return;
        }
    
        if (tr1.getAllControlPoints().getLast() == cp || tr1.getAllControlPoints().getFirst() == cp) {
            return; // Cannot cut if cp is the first or last point
        }
    
        int indexTR1 = trajectoriesList.indexOf(tr1);
        Trajectory tr2 = new Trajectory(getNextAvaliableName());
    
        int indexOfCP = tr1.indexOf(cp);
    
        // Add all control points after cp to tr2
        for (int i = indexOfCP; i < tr1.getAllControlPoints().size(); i++) {
            ControlPoint insertCP = tr1.getControlPoint(i);
            tr2.addControlPoint(new ControlPoint(insertCP));
        }
    
        // Remove all control points after cp from tr1
        for (int i = tr1.getAllControlPoints().size() - 1; i > indexOfCP; i--) {
            tr1.removeControlPoint(tr1.getControlPoint(i));
        }
    
        // Insert the new trajectory after tr1 in the list
        trajectoriesList.add(indexTR1 + 1, tr2);
    
        notifyTrajectoriesListListeners();
    }

    /**
     * Moves the specified trajectory down in the list, swapping it with the next trajectory.
     * 
     * @param tr the trajectory to move down
     */
    public static void moveTrajectoryDown(Trajectory tr) {
        if (!trajectoriesList.contains(tr)) {
            return;
        }
    
        int indexTR = trajectoriesList.indexOf(tr);
        if (indexTR == trajectoriesList.size() - 1) {
            return; // Cannot move down if it is the last trajectory
        }
    
        int indexTR2 = indexTR + 1;
        Trajectory temp = trajectoriesList.get(indexTR2);
        trajectoriesList.set(indexTR2, tr);
        trajectoriesList.set(indexTR, temp);
        notifyTrajectoriesListListeners();
    }

    /**
     * Moves the specified trajectory up in the list, swapping it with the previous trajectory.
     * 
     * @param tr the trajectory to move up
     */
    public static void moveTrajectoryUp(Trajectory tr) {
        if (!trajectoriesList.contains(tr)) {
            return;
        }
    
        int indexTR = trajectoriesList.indexOf(tr);
        if (indexTR == 0) {
            return; // Cannot move up if it is the first trajectory
        }
    
        int indexTR2 = indexTR - 1;
        Trajectory temp = trajectoriesList.get(indexTR2);
        trajectoriesList.set(indexTR2, tr);
        trajectoriesList.set(indexTR, temp);
        notifyTrajectoriesListListeners();
    }

    /**
     * Adds a listener to the list of trajectory list listeners.
     * 
     * @param listener the listener to add
     */
    public static void addTrajecoriesListListener(TrajectoriesListListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the list of trajectory list listeners.
     * 
     * @param listener the listener to remove
     */
    public static void removeTrajecoriesListListener(TrajectoriesListListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners that the trajectory list has changed.
     */
    private static void notifyTrajectoriesListListeners() {
        for (TrajectoriesListListener listener : listeners) {
            listener.TrajectoryListChanged();
        }
    }

    /**
     * Gets the list of all trajectories.
     * 
     * @return an {@link ArrayList} of all trajectories
     */
    public static ArrayList<Trajectory> getTrajectoriesList() {
        return trajectoriesList;
    }

    /**
     * Gets a trajectory by its index in the list.
     * 
     * @param index the index of the trajectory
     * @return the trajectory at the specified index
     */
    public static Trajectory getTrajectoryByIndex(int index) {
        return trajectoriesList.get(index);
    }

    /**
     * Gets a trajectory by its name.
     * 
     * @param name the name of the trajectory
     * @return the trajectory with the specified name, or {@code null} if not found
     */
    public static Trajectory getTrajectoryByName(String name) {
        for (Trajectory tr : trajectoriesList) {
            if (tr.getName().equals(name)) return tr;
        }
        return null;
    }

    /**
     * Gets an array of all trajectory names.
     * 
     * @return an array of all trajectory names
     */
    public static String[] getAllTrajectoriesNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Trajectory tr : trajectoriesList) {
            names.add(tr.getName());
        }
        return names.toArray(new String[0]);
    }

    /**
     * Creates a copy of the current list of trajectories.
     * 
     * @return a copy of the current list of trajectories
     */
    public static ArrayList<Trajectory> copyTrajectoriesList() {
        return new ArrayList<>(trajectoriesList);
    }

    /**
     * Checks if the list contains the specified trajectory.
     * 
     * @param tr the trajectory to check
     * @return {@code true} if the list contains the trajectory, otherwise {@code false}
     */
    public static boolean contains(Trajectory tr) {
        return trajectoriesList.contains(tr);
    }

    /**
     * Gets the trajectory that contains the specified control point.
     * 
     * @param cp the control point to search for
     * @return the trajectory that contains the control point, or {@code null} if not found
     */
    public static Trajectory getTrajectoryByControlPoint(ControlPoint cp) {
        for (Trajectory trajectory : trajectoriesList) {
            if (trajectory.contains(cp)) {
                return trajectory;
            }
        }
        return null;
    }

    /**
     * Gets the index of the specified trajectory in the list.
     * 
     * @param tr the trajectory to find
     * @return the index of the trajectory, or {@code -1} if not found
     */
    public static int getTrajectoryIndex(Trajectory tr) {
        return trajectoriesList.indexOf(tr);
    }

    /**
     * Sets the list of trajectories and notifies listeners of the change.
     * 
     * @param trajectories the new list of trajectories
     */
    public static void setTrajectoriesList(ArrayList<Trajectory> trajectories) {
        trajectoriesList = trajectories;
        notifyTrajectoriesListListeners();
        ActiveEntities.setActiveTrajectory(null);
    }

    /**
     * Adds a trajectory to the list and notifies listeners.
     * 
     * @param tr the trajectory to add
     */
    public static void addTrajectory(Trajectory tr) {
        if (tr == null) {
            throw new NullPointerException("Trajectory cannot be null!");
        }
        trajectoriesList.add(tr);
        notifyTrajectoriesListListeners();
    }

    /**
     * Adds a new trajectory with the next available name and notifies listeners.
     */
    public static void addTrajectory() {
        trajectoriesList.add(new Trajectory(getNextAvaliableName()));
        notifyTrajectoriesListListeners();
    }

    /**
     * Removes a trajectory from the list and notifies listeners.
     * 
     * @param tr the trajectory to remove
     */
    public static void removeTrajectory(Trajectory tr) {
        if (tr == null) {
            throw new NullPointerException("Trajectory cannot be null!");
        }

        trajectoriesList.remove(tr);
        notifyTrajectoriesListListeners();
    }

    /**
     * Gets the next available unique name for a trajectory.
     * 
     * @return the next available trajectory name
     */
    public static String getNextAvaliableName() {
        
        String name = null;
        int i = 1;
        boolean nameIsTaken = true;
        while (nameIsTaken) { 
            name = "Trajectory " + i++;
            nameIsTaken = false;
            for (Trajectory tr : trajectoriesList) {
                if (tr.getName().equals(name)) {
                    nameIsTaken = true;
                }
            }
        }
        return name;
    }
    
}
