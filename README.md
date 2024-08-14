# NoSmoking - Lung Breaker Game

**NoSmoking** is a Java-based brick breaker game that advocates against smoking. The game is built using Java Swing and is designed to simulate the damage caused by smoking on lungs. The player controls a paddle, represented as a cigarette, to break bricks, which represent lungs. The goal is to destroy all the lung bricks, signifying the damage smoking does, and ultimately win the game which is actually a loss for the health.


# Features

- **Health Bar:** Displays the damage done to the lungs based on the number of bricks destroyed.
- **Score Tracking:** Keeps track of the player's score, representing the extent of lung damage.
- **Pause/Resume:** The game can be paused and resumed using the 'P' key.
- **Brick Map Generation:** The layout of bricks is dynamically generated from a CSV file, allowing for customizable game levels.


## How to Run the Game
   
1. **Import the Project:**
   - Import the project into your preferred IDE (e.g., Eclipse, IntelliJ).
   
2. **Run the Game:**
   - Run the `MainClass.java` file in the `game` package.

3. **Controls:**
   - Left Arrow: Move paddle left.
   - Right Arrow: Move paddle right.
   - Enter: Restart the game after losing or winning.
   - P: Pause/Resume the game.


## Customizing the Brick Map

- The brick layout can be customized by editing the `lungs.csv` file in the `src/res` directory. Each value in the CSV represents a brick:
  - `0`: No brick.
  - `1`: Brown lung brick.
  - `2`: Pink lung brick.


## Dependencies

- The game is built using Java Swing, so no external dependencies are required.


## Contributing

Feel free to fork this repository and submit pull requests if you want to enhance the game.


**Note:** This game is a conceptual project and should not be used as a medical representation of smoking effects. The goal is to create awareness about the harmful effects of smoking through a fun and interactive game.
