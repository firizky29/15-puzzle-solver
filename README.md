# 15 Puzzle Solver
## About Program
This is a solver program of a puzzle called 15-puzzle. It is basically a game to achieve particular goal state from the given state. Further information about 15-puzzle can be found here https://en.wikipedia.org/wiki/15_puzzle

This solver uses an algorithm strategy called branch and bound to optimally find the solution path. It has feature to visualize the path as well as the heuristics strategy and analysis. The approximate cost in this B&B techniques is determined by the number of tiles that are not in the right position relative to the goal state.

## Requirement
This program was developed using Java SE 17 as the programming language and IntelliJ as IDE software.

It is recommended to use IntelliJ to compile the program. It is also recommended to use minimum 8 GB RAM as it compiled with the modification of maximum allocated memory of virtual memory JVM can use. You can as well recompile it to adjust it with your machine.

Refer to this link for further information: https://www.jetbrains.com/help/idea/tuning-the-ide.html#configure-jvm-options

## How to Use
Run the program using this command
```
.\bin\artifacts\15-puzzle-solver.jar
```
This is GUI of main windows.
![GUI Main Windows](https://user-images.githubusercontent.com/88244714/161386019-2bd93172-afbb-4c5f-97f3-c9574b39076a.PNG)
1. Import button is used for importing root state from file text. 
2. The big square text box is used for writing user input. 
3. Create button is used to make the user input to get processed. 
4. Randomize button is to randomize current board. 
5. Solve button is used to solve current board.
6. Reset button [Hidden] is used to reset processed board to its root state
7. See Analysis Button [Hidden] is used to show heuristics analysis of the board.

And for the analysis window are shown below.
![GUI Analysis Window](https://user-images.githubusercontent.com/88244714/161386014-1a5820bc-46a2-473c-93fa-363bc303fc0d.PNG)
Export button is used for writing the output to file text.


## Author
Firizky Ardiansyah (13520095) Kelas K02.
