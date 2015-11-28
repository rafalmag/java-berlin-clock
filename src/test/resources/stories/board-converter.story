Story: board converter for Game Of Life

Meta:
@scope interview

Narrative:
    String form of board should be loaded to object

Given board
.XX
X..
..X
When it is converted
Then x=0,y=0 should be dead
Then x=1,y=0 should be alive
Then x=2,y=0 should be alive

Then x=0,y=0 should be dead
Then x=1,y=0 should be alive
Then x=2,y=0 should be alive

Then x=0,y=0 should be dead
Then x=1,y=0 should be alive
Then x=2,y=0 should be alive
