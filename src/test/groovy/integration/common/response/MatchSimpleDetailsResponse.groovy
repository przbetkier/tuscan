package integration.common.response

import groovy.transform.CompileStatic

import java.time.LocalDateTime

import static java.util.TimeZone.getTimeZone

@CompileStatic
class MatchSimpleDetailsResponse {

    def static String successfulResponse(LocalDateTime startedAt, LocalDateTime finishedAt, String matchId) {
        def startedAtTimestamp = convertToTimestamp(startedAt)
        def finishedAtTimestamp = convertToTimestamp(finishedAt)
        return """
            {
               "items":[
                {  
                     "match_id":"$matchId",
                     "game_id":"csgo",
                     "match_type":"5v5 Classic",
                     "game_mode":"5v5 Classic",
                     "game_type":"matchmaking",
                     "best_of":1,
                     "played":1,
                     "max_players":10,
                     "teams_size":5,
                     "teams":{  
                        "faction1":{  
                           "team_id":"1d94cafc-3df8-4e94-be20-a6c8c9c0bdae",
                           "nickname":"team_Cukijass",
                           "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/1d94cafc-3df8-4e94-be20-a6c8c9c0bdae_1488496253235",
                           "type":"",
                           "players":[  
                              {  
                                 "player_id":"a092d676-0835-4375-bd8d-54ffb5950185",
                                 "nickname":"Vigs",
                                 "avatar":"",
                                 "skill_level":9,
                                 "game_player_id":"76561198007021449",
                                 "game_player_name":"Æv",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/Vigs"
                              },
                              {  
                                 "player_id":"c39e55df-9bed-47fe-b877-e41705f6a652",
                                 "nickname":"DV3OF",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/c39e55df-9bed-47fe-b877-e41705f6a652_1509082796386",
                                 "skill_level":8,
                                 "game_player_id":"76561198403361156",
                                 "game_player_name":"DVE",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/DV3OF"
                              },
                              {  
                                 "player_id":"277259be-95bf-4d47-becb-2675bc713f5e",
                                 "nickname":"dreamchasers",
                                 "avatar":"",
                                 "skill_level":10,
                                 "game_player_id":"76561198158438122",
                                 "game_player_name":"dreamchaser",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/dreamchasers"
                              },
                              {  
                                 "player_id":"3e71499c-3dd0-41b4-8af2-5b91820f231e",
                                 "nickname":"tommyP23",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/3e71499c-3dd0-41b4-8af2-5b91820f231e_1479780893523",
                                 "skill_level":7,
                                 "game_player_id":"76561198098538631",
                                 "game_player_name":"TommYEsc",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/tommyP23"
                              },
                              {  
                                 "player_id":"1d94cafc-3df8-4e94-be20-a6c8c9c0bdae",
                                 "nickname":"Cukijass",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/1d94cafc-3df8-4e94-be20-a6c8c9c0bdae_1488496253235",
                                 "skill_level":8,
                                 "game_player_id":"76561197961412352",
                                 "game_player_name":"galaxy",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/Cukijass"
                              }
                           ]
                        },
                        "faction2":{  
                           "team_id":"046b7260-1f93-4993-8c69-14ad5624b440",
                           "nickname":"team_PaNicLL",
                           "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/046b7260-1f93-4993-8c69-14ad5624b440_1523233395929",
                           "type":"",
                           "players":[  
                              {  
                                 "player_id":"07683152-e406-455c-9442-ccfc8ec3dc7e",
                                 "nickname":"TrueSquard",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/07683152-e406-455c-9442-ccfc8ec3dc7e_1497201240441",
                                 "skill_level":10,
                                 "game_player_id":"76561198127064594",
                                 "game_player_name":"76561198127064594",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/TrueSquard"
                              },
                              {  
                                 "player_id":"046b7260-1f93-4993-8c69-14ad5624b440",
                                 "nickname":"PaNicLL",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/046b7260-1f93-4993-8c69-14ad5624b440_1523233395929",
                                 "skill_level":5,
                                 "game_player_id":"76561198201094023",
                                 "game_player_name":"",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/PaNicLL"
                              },
                              {  
                                 "player_id":"7acf2895-b5b7-4b03-80a6-057abdaafac6",
                                 "nickname":"Zroch",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/7acf2895-b5b7-4b03-80a6-057abdaafac6_1507484863936",
                                 "skill_level":10,
                                 "game_player_id":"76561198017789610",
                                 "game_player_name":"76561198017789610",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/Zroch"
                              },
                              {  
                                 "player_id":"bba6b405-64a9-4e90-a317-19c2fbb62f39",
                                 "nickname":"Vint4gesesh",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/bba6b405-64a9-4e90-a317-19c2fbb62f39_1530309824492",
                                 "skill_level":5,
                                 "game_player_id":"76561198206992117",
                                 "game_player_name":"Vintage /v2",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/Vint4gesesh"
                              },
                              {  
                                 "player_id":"c72461ee-9c65-4000-956b-09b006529a58",
                                 "nickname":"BartiFK",
                                 "avatar":"https://d50m6q67g4bn3.cloudfront.net/avatars/c72461ee-9c65-4000-956b-09b006529a58_1451660607523",
                                 "skill_level":7,
                                 "game_player_id":"76561198185736131",
                                 "game_player_name":"Barti ✈",
                                 "faceit_url":"https://www.faceit.com/{lang}/players/BartiFK"
                              }
                           ]
                        }
                     },
                     "playing_players":[  
                        "a092d676-0835-4375-bd8d-54ffb5950185",
                        "c39e55df-9bed-47fe-b877-e41705f6a652"
                     ],
                     "competition_id":"42e160fc-2651-4fa5-9a9b-829199e27adb",
                     "competition_name":"CS:GO 5v5",
                     "competition_type":"matchmaking",
                     "organizer_id":"faceit",
                     "status":"finished",
                     "started_at":$startedAtTimestamp,
                     "finished_at":$finishedAtTimestamp,
                     "faceit_url":"https://www.faceit.com/{lang}/csgo/room/1-398c21fe-3c77-48bb-9712-b037c3e2c136"
                  }
               ]
            }
            """
    }

    def static convertToTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(getTimeZone("UTC").toZoneId()).toEpochSecond()
    }
}
