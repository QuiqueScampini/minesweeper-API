# minesweeper-API

Miesweeper-api challenge project.

For information you can visit our [Wiki](https://github.com/QuiqueScampini/minesweeper-API/wiki) or see the links below.

- #### [Challenge Statement](https://github.com/QuiqueScampini/minesweeper-API/wiki/Challenge-Statement)
- #### [Api Definition](https://github.com/QuiqueScampini/minesweeper-API/wiki/Api-Definition)

## Considerations

- New games can have a max of 30 rows, 30 cols and 99 mines.
- Time Tracking has the following count:
    - Game starts with 0 seconds.
    - When Status goes to IN_PROGRESS start counting.
    - When Status goes to PAUSED store that amount of seconds and stop counting.
    - When Status goes to IN_PROGRESS again it counting adding last saved amount of seconds.
    - When Game finishes it stop counting.
- An finished game can not be replayed.
- Games search by user is case sensitive Quique and quique are diferent users.
- When you FLAG a cell it takes 1 of your leftFlags.
- You can put more flags than mines so that counter would be negative.
- When you QUESTION mark a cell it recover that leftFlag.
- If you send an invalid ActionRequest Action it will return a 400 error but not an ErrorResponse.

## Design considerations

- As this will be a mobile app I used an H2 embedded database that saves data in the filesystem where the app is running.
- Once created the database it can't be erased, you will have to delete it from filesystem and start the app again.
- As we only have to distinguish the user that started the game, I only save it's name in the game data. If more user data is needed the model may not be enough.
- As this wil be a standalone mobile app concurrent request where not considered.

 
