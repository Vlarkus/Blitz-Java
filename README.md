
<div align="center">
  <img src="app/src/main/java/blitz/resources/images/icons/app/Blitz App Icon (No Background).png" alt="Blitz Logo">
   <h1>Blitz</h1>
  <h2>Complex Made Simple</h2>
  <h3>Version 1.0.0</h3>
</div>

---

## Introduction

Blitz is advanced software for modeling autonomous routine trajectories for VEX Robotics Competitions. It simplifies the complex process of trajectory planning by providing intuitive tools for generating and editing various splines. With Blitz, teams can efficiently design and fine-tune their robot's autonomous routines.

Blitz's Philosophy: simplicity in usage & complexity in customization.

---

## How To Use

See the YouTube tutorial videos to get started with Blitz:

- [Blitz v1.0.0 Showcase](https://youtu.be/nr9F6VdIvRY?si=DwqkOhjh7goCK9w5)
---

## Installation Instructions

To install Blitz on your system, please follow these steps:

1. **Install Java**

   Make sure you have the Java Development Kit (JDK) installed (recommended version: 21+). You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html) or use an open-source alternative like [OpenJDK](https://openjdk.java.net/install/).

2. **Install Gradle**

   Blitz uses Gradle for building the project (recommended version: 8.8). Download and install Gradle from the [official website](https://gradle.org/install/).

3. **Clone the Project**

   Open your terminal and run the following commands:

   ```bash
   git clone https://github.com/Vlarkus/Blitz.git
   cd blitz
   ```

4. **Build the Project**

   Use Gradle to build Blitz:

   ```bash
   gradle build
   ```

5. **Run Blitz**

   After building, go to ```app/src/main/java/blitz/Main.java``` and run the file.
   Alternatively, you can use Gradle to run Blitz:
   ```bash
   gradle run
   ```

---

## Report / Suggest

We value your feedback! If you encounter any issues or have suggestions for improvements, please:

- **Open an Issue**: Submit a detailed report on our [GitHub Issues page](https://github.com/Vlarkus/Blitz/issues).
- **Contact Us**: Reach out via Discord at [BLITZ](https://discord.gg/v6zSjrpWfh).

---

## Known Issues

Currently, we are aware of the following issues:

- **Laggy Spline Behavior**: Certain spline types may cause noticeable lag upon editing.
- **Inconsistent Load of Custom Fields**: Custom fields may not always load properly through the View/Change Field menu.
- **Jittering on Max Zoom Out**: Panning on a max zoom out may cause jittering.

We are actively working to resolve these problems. Stay updated by checking our [Issues page](https://github.com/Vlarkus/Blitz/issues).

---

## Future Implementations & Improvements

Here is a list of features and improvements we plan to implement in the near future:

- **Path Following Visualization**: Visualize robot paths with a configurable box when hovering over a path segment.
- **Advanced Trajectory Planner**: Addition of a trajectory planner menu to allow the user modify selected trajectory by changing speed and time of the robot on different segments with keyframes.
- **View Modes**: Choose to visualize trajectories with the regard to their speed, time, curvature, or none.
- **Settings**: Addition of a settings window to configure visual, calculational, default, and other features within the program.
- **Action History**: Addition of *UNDO* and *REDO* functionality.
- **Units**: Choose other measuring systems when editing trajectories (currently the only choice is inches).
- **Configurable Coordinate Systems**: Choose the origin and the absolute orientation on the field.
- **Copy / Merge Tool**: Addition of a copy / merge window that creates a new trajectory from selected control points of multiple trajectories in the specified order.
- **Canvas Panel Optimization**: Optimize structural and rendering functionality of the Canvas Panel to eliminate lag.
- **Curve Calculations Optimization**: Optimize the calculations used for curve generation.
- **Multi-Select Position Editing within One Trajectory**: Edit position of multiple control points within the same trajectory.
- **More Export Options**: Support for more formats.
- **Support for Holonomic Drive Systems**: Adaptation of the functionality to model trajectories for holonomic drive systems.

---

## How to Contribute

We welcome contributions from the community! Here's how you can contribute:

1. **Fork the Repository**

   Click the "Fork" button at the top-right corner of the [repository page](https://github.com/your-username/blitz).

2. **Clone Your Fork**

   ```bash
   git clone https://github.com/your-username/blitz.git
   cd blitz
   ```

3. **Create a New Branch**

   ```bash
   git checkout -b feature/your-feature-name
   ```

4. **Make Your Changes**

   Implement your feature or bug fix.

5. **Commit Your Changes**

   ```bash
   git add .
   git commit -m "Add your feature"
   ```

6. **Push to Your Fork**

   ```bash
   git push origin feature/your-feature-name
   ```

7. **Submit a Pull Request**

   Go to your fork on GitHub and click the "New Pull Request" button.

---

## License

Blitz is distributed under the **Apache License, Version 2.0**. See the [LICENSE](LICENSE) file for more information.

---