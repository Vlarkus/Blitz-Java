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

package blitz;

import javax.swing.SwingUtilities;

import blitz.configs.Config;
import blitz.services.Utils;
import blitz.ui.application.MainFrame;


/**
 * @author Valery Rabchanka
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println(BlitzTerminalLogo()); // Print terminal logo
        setupMacOS(); // Setup macOS-specific properties
        SwingUtilities.invokeLater(() -> new MainFrame()); // Launch UI on the EDT
    }

    private static void setupMacOS() {
        if (Utils.isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Blitz");
        }
    }

    private static String BlitzTerminalLogo(){
        String blitzLogo = new String();
        blitzLogo += "\n";
        blitzLogo += "\n";
        blitzLogo += "\n";
        blitzLogo += Config.BLITZ_TERMINAL_ICON;
        blitzLogo += "\n";
        blitzLogo += "\n";
        blitzLogo += "\n";
        return blitzLogo;
    }

}
