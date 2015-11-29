Story: The Game Of Life

Meta:
@scope interview

Narrative:
    The Game of Life, also known simply as Life, is a cellular automaton
    devised by the British mathematician John Horton Conway in 1970.
    The "game" is a zero-player game, meaning that its evolution
    is determined by its initial state, requiring no further input.
    One interacts with the Game of Life by creating an initial configuration
    and observing how it evolves.
    More info: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life

Scenario: Blinker
Given board
.X.
.X.
.X.
When it evolves
Then the board should look like
...
XXX
...
When it evolves
Then the board should look like
.X.
.X.
.X.

Scenario: Evolution of Line to Beehive
Given board
.X.
.X.
.X.
.X.
When it evolves
Then the board should look like
...
XXX
XXX
...
When it evolves
Then the board should look like
.X.
X.X
X.X
.X.
When it evolves
Then it is still

Scenario: Blinker at a side of the declared board
Given board
.X
.X
.X
When it evolves
Then the board should look like
...
XXX
