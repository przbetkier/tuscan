package integration.common.response

import groovy.transform.CompileStatic

@CompileStatic
class PlayerPositionResponse {

    static def String simplePosition(int position) {
        def positionUp = position + 1

        """ {
            "position":  $position ,
            "items": [
                {
                    "player_id": "bb7a002c-599c-4da1-a853-71eb554f4398",
                    "nickname": "Player-Random-1",
                    "country": "ru",
                    "position": $positionUp,
                    "faceit_elo": 1404,
                    "game_skill_level": 6
                }
        ],
            "start": 197710,
            "end": 197710
        }"""
    }
}
