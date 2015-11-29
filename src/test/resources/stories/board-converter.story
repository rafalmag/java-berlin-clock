Story: board converter for Game Of Life

Meta:
@scope interview

Narrative:
    String form of board should be loaded to object. First declared point is assumed to be at 0,0 position.

Given board
.XX
X..
..X
When it is converted
!-- 1st row
Then x=0,y=0 should be dead
Then x=1,y=0 should be alive
Then x=2,y=0 should be alive
!-- 2nd row
Then x=0,y=0 should be dead
Then x=1,y=0 should be alive
Then x=2,y=0 should be alive
!-- 3rd row
Then x=0,y=0 should be dead
Then x=1,y=0 should be alive
Then x=2,y=0 should be alive
!-- all others fields should be dead
Then x=3,y=4 should be dead
Then x=-2,y=-3 should be dead