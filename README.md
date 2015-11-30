# Conway's Game of Life 

The "game" is a zero-player game, meaning that its evolution is determined by its initial state, 
requiring no further input. 
One interacts with the Game of Life by creating an initial configuration and observing how it evolves.

## Rules
The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, 
each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, 
which are the cells that are horizontally, vertically, or diagonally adjacent. 

At each step in time, the following transitions occur:
 * Any live cell with fewer than two live neighbours dies, as if caused by under-population.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by over-population.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

The initial pattern constitutes the seed of the system. 
The first generation is created by applying the above rules simultaneously to every cell in the seed—births 
and deaths occur simultaneously, and the discrete moment at which this happens is sometimes called a tick 
(in other words, each generation is a pure function of the preceding one). 
The rules continue to be applied repeatedly to create further generations.

## The brief

There are provided acceptance tests and your challenge is to get them passing.

## Some hints
If you are new to Gradle, it may be worth spending 10 minutes reading a high level summary. 
Gradle Wrapper is used so `gradlew` from the command line should download everything you need. 
Most modern IDEs support Gradle projects.

The use of JBehave in this instance is to provide you with our definition of done for the task.

Please ensure that you are familiar with our values in the instructions project. They are important to us.

## More information

More info and description based on [this wikipedia article](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).
