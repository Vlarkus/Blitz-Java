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

package blitz.ui.speedControl;

import javax.swing.JFrame;

/*
 * Only one instance of this frame can exist.
 * The frame stays on top of all other frames,
 * but allows for interactions with other frames.
 */
// Singleton pattern implementation for speedControlFrame
public class speedControlFrame extends JFrame {
    private static speedControlFrame instance;

    private speedControlFrame() {
        // Initialize the frame here
    }

    public static speedControlFrame getInstance() {
        if (instance == null) {
            instance = new speedControlFrame();
        }
        return instance;
    }
}
