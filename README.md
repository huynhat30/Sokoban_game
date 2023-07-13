# Sokoban_game

•Features were added to the game and work well: 
    + Start window: the first window pop-up when executing the game, with 3 buttons (Play, How to play and Quit).
    + Name input window: a window asking user name.
    + Wall color choosing window: a window asking user to choose the wall color they like.
    + Instruction window: show player how to play the game.
    + Timer: count up the the time player uses to finish 1 level.
    + Move count: count up number of moves after each movement of player.
    + Toggle music window: 2 options, player can choose to play game default music or can add their music to game.
    + Music player: a simple music player for user add their music, with 3 buttons(Add, Play, Stop).
    + Level score window: Containing: player name, number of move, finished  time, current date.

•Features add to game but not work well:
    + Undo: the idea: create a ArrayList, receive code from keyboard, add to ArrayList then press undo, get the last code of list and give object oppsite positon with the code.

•Features are not add to game:
    + Save game: still figure out the way to make save game function.
    + Reset game: stuck with the undo, so this hasn’t implemented yet.

•Added classes:
    + StartScreen.java: create the start window.
    + NameAskScreen.java: create a window asking player name.
    + WallColorChoice.java: create a window asking for choosing the wall.
    + Wall.java: receive input color then sends to GameObject.java.
    + ButtonConfig.java: buttons style setting.
    + GameMessage.java: shows messages of game.
    + Timer.java: shows a timer in seconds on the game screen.
    + MoveCount.java: reads move’s amonut in file and updates on screen.
    + PlaySound.java: game sound controller.
    + GameBoard.java: create a window contains menu bar, timer, move’s amount, main game board.
    + MenuItemAction.java: receive action and sends back events when menu items clicked.
    + Highscore.java: receives player name, move’s amount, finished time, current date; writes into files. Create window to show all values above of each level.

•Modified classes:
    + Main.java: broke down into 3 classes: Main.java, GameBoard.java, MenuItemAction.java; 
    + GameFlow.java: origin name is StartMeUp.java; add undo function(not finish); get/set number of moves
    + GraphicObject.java: add more color option for object wall.

•Devided classes into 3 packages:
    + Control: all classes working with file and classes help game run in flow
    + View: all classes that relative to the game UI
    + Model: contains text files such as a raw level file, raw score list file of each level.

•Applied Singleton pattern to some classes because these classes instances be called once in all other classes.
•The reason I broke down the Main.class is I want to apply single responsibility principle.
•The reason of changed StartMeUp.java class name is I see this class make the application going in flows and the name is suited
