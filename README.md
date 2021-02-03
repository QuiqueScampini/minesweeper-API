# minesweeper-API

Miesweeper-api challenge project.

- #### [Challenge Statement](https://github.com/QuiqueScampini/minesweeper-API/wiki/Challenge-Statement)

## Requirements
- Java 11
- Maven 

## Run the App
- Clone the repository with git or download from [this link](https://github.com/QuiqueScampini/minesweeper-API/archive/master.zip)
- Start spring-boot app.
    ```shell script
    ./mvnw spring-boot:run
    ```
    
- This will run the app on http://localhost:8080

## Api Definition 
Api documentation could be seen on swagger http://localhost:8080/minesweeper

You can see the [Api Definition](https://github.com/QuiqueScampini/minesweeper-API/wiki/Api-Definition) on Wiki.

## Considerations

- New games can have a max of 30 rows, 30 cols and 99 mines.
- Time Tracking has the following count:
    - Game starts with 0 seconds.
    - When Status goes to IN_PROGRESS start counting.
    - When Status goes to PAUSED store that amount of seconds and stop counting.
    - When Status goes to IN_PROGRESS again it counting adding last saved amount of seconds.
    - When Game finishes it stop counting.
- A finished game can not be replayed.
- Games search by user is case sensitive Quique and quique are diferent users.
- When you FLAG a cell it takes 1 of your leftFlags.
- You can put more flags than mines so leftFlags counter would be negative.
- When you QUESTION mark a cell it recover that leftFlag.
- If you send an invalid ActionRequest Action it will return a 400 error but not an ErrorResponse.
- If game is finished any action will have no efect.
- If a cell has a FLAG or is REVEALED a REVEAL action will have no efect.
- If a cell is REVEAL any action will have no efect.


## Design considerations

- As this will be a mobile app I used an H2 embedded database that saves data in the filesystem where the app is running.
- Once created the database it can't be erased, you will have to delete it from filesystem and start the app again.
- As we only have to distinguish the user that started the game, I only save it's name in the game data. If more user data is needed the model may not be enough.
- As this wil be a standalone mobile app concurrent request where not considered.
- Log file and database will be written in the application path. 
- Repository and Domain Game model is shared, this is not a recomended practice. 
- ApiResponses shares domain enums for GameStatus and CellContent, this is not a recomended practice too. 
- The last 2 items were designed this way because of the size of the app, if the app grows it would be recomended to have 2 diferent models with a transformer from repository to domain for repository and domain entities and an api specific GameStatus and CellContent.


 
