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
Api documentation could be seen on swagger at http://localhost:8080/minesweeper

### Examples
#### Game Creation
```shell script
curl --location --request POST 'http://localhost:8080/minesweeper/game' \
--header 'Content-Type: application/json' \
--data-raw '{
  "rows": 10,
  "cols": 10,
  "mines": 10,
  "user": "Licher"
}'
```
Returns 
- [GameResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#gameresponse)
- [ErrorResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#errorresponse)

#### Flag/Reveal a Cell
```shell script
curl --location --request PUT 'http://localhost:8080/minesweeper/game/1' \
--header 'Content-Type: application/json' \
--data-raw '{
  "action": "FLAG",
  "col": 1,
  "row": 0
}'
```
Returns 
- [GameResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#gameresponse)
- [ErrorResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#errorresponse)

#### Pause Game
```shell script
curl --location --request PATCH 'http://localhost:8080/minesweeper/game/10'
```
Returns 
- [GameResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#gameresponse)
- [ErrorResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#errorresponse)

#### Retrieve Games by Id
```shell script
curl --location --request GET 'http://localhost:8080/minesweeper/game/1'
```
Returns 
- [GameResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#gameresponse)
- [ErrorResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#errorresponse)

#### Retrieve Games by User (If not user sent this will bring all games)
```shell script
curl --location --request GET 'http://localhost:8080/minesweeper/game?user=Licher'
```
Returns 
- [GamesResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#gamesresponse)
- [ErrorResponse](https://github.com/QuiqueScampini/minesweeper-API/blob/master/README.md#errorresponse)

### Responses Examples
#### GameResponse
```json
{
    "id": 1,
    "user": "Quique",
    "status": "CREATED",
    "board": [
        {
            "cols": [
                {
                    "content": "NONE"
                },
                {
                    "content": "FLAG"
                },
                {
                    "content": "NONE"
                }
            ]
        },
        {
            "cols": [
                {
                    "content": "QUESTION"
                },
                {
                    "value": 1,
                    "content": "REVEALED"
                },
                {
                    "content": "NONE"
                }
            ]
        } ]
        }
    ],
    "gameTime": 35,
    "leftFlags": 1
}
```

#### GamesResponse
```json
{
    "games": [
        {
            "id": 1,
            "user": "Quique",
            "status": "CREATED",
            "board": [
                {
                    "cols": [
                        {
                            "content": "NONE"
                        },
                        {
                            "content": "QUESTION"
                        },
                        {
                            "content": "NONE"
                        }
                    ]
                },
                {
                    "cols": [
                        {
                            "content": "FLAG"
                        },
                        {
                            "content": "NONE"
                        },
                        {
                            "value": 1,
                            "content": "REVEALED"
                        }
                    ]
                }
            ],
            "gameTime": 50,
            "leftFlags": 1
        }
    ]
}
```
#### ErrorResponse
```json
{
    "status": "NOT_FOUND",
    "timestamp": "2021-02-03T10:00:25.6644",
    "message": "Game with Id: 10 not found"
}
```


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

 
