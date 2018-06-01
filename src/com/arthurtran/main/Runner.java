package com.arthurtran.main;

/*
Mini Golf Final Project

Arthur Tran, Michael Crowe, Danny Padilla, Parth Mehta
Period 5, 6

Tasks:
Arthur - Basic functions of the game such as shooting, aiming, and the physics
       - Created the soundtrack
       - Worked on collisions
       - Did the key events
       - Implemented variable strength

Michael - Created the mini map and it's functionality
        - Helped with the collisions
        - Added mouse events
        - Helped with the variable strength

Danny - Worked on the maps and having them show up in the game as blocks
      - Helped with collisions and hit boxes
      - Did all of the textures

Parth - Worked on the menu and end screen
      - Helped with mouse events


We also used a little bit of my API for things like audio (was not aware javafx has audio)
and loading in images (again, was not aware of this feature in javafx)
    My API: https://github.com/atran06/Arch2D

 */

import com.arthurtran.game.Game;

public class Runner extends Game {

    public static void main(String[] args) {
        launch(args);
    }
}
