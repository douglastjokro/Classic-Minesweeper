# Minesweeper
Minesweeper is a classic single-player game in which the player has to clear a board containing hidden mines without detonating any of them. The player can reveal the contents of a cell by clicking on it, and if the cell contains a mine, the game is over. If the cell does not contain a mine, it will display the number of adjacent mines, and the player can continue to click on adjacent cells until all non-mine cells have been uncovered. The player wins the game by uncovering all non-mine cells.

The game board consists of a grid of buttons, and the contents of each cell are determined randomly at the beginning of each game. The player can left-click on a cell to reveal its contents and right-click to mark a cell as a mine. The game ends when the player clicks on a mine, and a dialog appears to inform the player of the result.

## Development
This simple implementation of Minesweeper is written in Java using **Swing** for the graphical user interface. The game logic is implemented in the **Minesweeper** class, which extends the **JFrame** class and implements the **ActionListener** and **MouseListener** interfaces to handle user input. The game board is represented as a two-dimensional array of integers, where each cell contains either a mine (-1) or the number of adjacent mines (0-8).

The game board is generated randomly at the beginning of each game using the **generateBoard** method, which first initializes the board with zeros, then randomly places mines on the board, and finally fills in the rest of the board with numbers indicating the number of adjacent mines. The **uncoverCell** method is called when the player left-clicks on a cell, and it recursively uncovers adjacent cells until all non-mine cells have been uncovered or a mine is uncovered. The **mousePressed** method is called when the player right-clicks on a cell, and it marks the cell as a mine if it is not already marked or unmarks it if it is already marked.

## Installation
- To play the gamne, you need to have Java installed on your system. 
- Once Java is installed, you can download the Minesweeper program from this GitHub repository.

## Execution
Open a terminal or command prompt, navigate to the directory where you downloaded the program, and run the "**java Minesweeper**" command.

## Credits
This program was developed by Douglas Tjokrosetio.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
