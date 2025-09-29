# Colliding Balls Simulation

A JavaFX-based physics simulation of elastic collisions between multiple balls in a 2D space. 
The system uses a priority queue–based event-driven algorithm to compute ball-ball and ball-wall collisions, ensuring realistic motion and conservation of momentum.
This project was inspired by Robert Sedgewick's lecture on Event-Driven Simulation from the course Algorithms, Part I. 

## Screen Recording
<i>Framerate lower due to gif format.</i>
![Simulation Demo](example.gif)
## Installation and Setup Instructions

### Requirements
- Java 24
- JavaFX SDK 25

### Clone this repository
```powershell
git clone https://github.com/alexwhoover/2D_ball_collision_simulation.git
cd 2D_ball_collision_simulation
```


### To Run
1. Set an environmental variable pointing to JavaFX SDK
```powershell
$env:JAVAFX_HOME "...\openjfx-25_windows-x64_bin-sdk\javafx-sdk-25"
```
2. Compile the project
```powershell
javac --module-path $env:JAVAFX_HOME\lib --add-modules javafx.controls,javafx.graphics src\main\java\com\example\*.java
```

3. Run the simulation
```powershell
java --enable-native-access=javafx.graphics --module-path $env:JAVAFX_HOME\lib --add-modules javafx.controls,javafx.graphics src\main\java\com\example\CollisionSimulation.java
```

Or, use an IDE
1. Add the JavaFX 25 SDK as a library in your project structure
2. Configure the run configuration to include the module path and add modules javafx.controls and javafx.graphics
3. Run `CollisionSimulation.java` as a JavaFX application

## Optimization
With the priority queue (event-driven) solution, only the next imminent event is processed at each step.
Each event insertion or removal from the priority queue takes O(log M) time, where M is the number of scheduled events (typically O(N), where N is the number of balls). Thus, insertion has an average time complexity of O(logN).
After each collision event, all future events involving the two colliding balls must be recomputed, requiring up to \(2N\) new event insertions into the priority queue (O(N)).
Therefore, we can expect an average time complexity per frame of O(NlogN), which is much faster than the naive O(N^2) approach which involves checking every pair of balls at each frame.

## Reflection

- **Context**: Personal project.
- **Goal**: To gain experience implementing different data structures and to begin exploring computer graphics. 
- **Challenges**: Managing multiple collisions that occur between animation frames required careful handling of event ordering and time advancement. I solved this by using a while loop to process all events that occur between frames. Therefore, the ball positions are updated at each collision as well as each frame, removing any erroneous overlapping.
- **Skills Learned**:
    - **JavaFX** for rendering and animation.
    - **Java PriorityQueue** for managing upcoming collision events.
    - How to implement physical phenomena in code.