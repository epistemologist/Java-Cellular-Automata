# Java Cellular Automata

In this repository are Java implementations of various cellular automata. Most of the classes have a method to generate an image sequence, which can then be stitched into a video with something like ffmpeg (you can do this automatically by running makevid.sh). Examples of videos produced like this can be found in the "vids" folder.
## Utility Classes
* Array - provides methods to work with 2D arrays
* Colour - provides methods for implementing various color spectrums
* Image - provides methods for saving an array as an image
* ImportLifePattern - imports a Game of Life pattern from the [Game of Life Wiki](http://www.conwaylife.com/wiki/Main_Page)
## Implementations
* [ElementaryCellularAutomata](https://mathworld.wolfram.com/ElementaryCellularAutomaton.html)
* TwoDimensional (generalized 2d cellular automata using the Moore neighborhood)
* [GameOfLife](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
* [FastLife](https://github.com/norvig/pytudes/blob/master/ipynb/Life.ipynb) (faster version of Game of Life with the use of HashSets)
* [FredkinReplicator](https://www.youtube.com/watch?v=_UtCli1SgjI)
* PDE (cellular automata that models Laplace's equation)
* [ReactionDiffusion](https://thecodingtrain.com/CodingChallenges/013-reactiondiffusion-p5.html)
* [CyclicCellularAutomata](https://softologyblog.wordpress.com/2013/08/29/cyclic-cellular-automata/)
## Example Output
https://user-images.githubusercontent.com/12467423/132405966-5fe3ee24-75e0-41bf-9b61-f4f289868ed9.mp4
https://user-images.githubusercontent.com/12467423/132405971-781297b4-e901-4459-8278-160a12a3f61f.mp4
https://user-images.githubusercontent.com/12467423/132405980-8560515d-54cd-45e4-ba98-6813b36b5944.mp4
