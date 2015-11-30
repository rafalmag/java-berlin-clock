Story: Game Of Life on Guava Board

Meta:
@scope interview

Narrative:
    Unit test of internal implementation methods using JBehave.

@Scenario: count neighbours

Given board
...
...
...
When count x=1, y=1 neighbours
Then result should be 0

Given board
XXX
XXX
XXX
When count x=1, y=1 neighbours
Then result should be 8

Given board
...
.X.
X.X
When count x=1, y=1 neighbours
Then result should be 2

@Scenario: toString

Given board
X.X
XX.
X..
When printed
Then should look like this
First point position (0,0)
#.#
##.
#..

Given board
....
....
..X.
..X.
.X.X
When printed
Then should look like this
First point position (1,2)
.#.
.#.
#.#