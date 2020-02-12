package pro.tuscan.client.player;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class PlayerHistoryDto {

    private List<MatchHistoryDto> matchHistoryDtoList;

    @JsonCreator
    public PlayerHistoryDto(List<MatchHistoryDto> matchHistoryDtoList) {
        this.matchHistoryDtoList = matchHistoryDtoList;
    }

    public List<MatchHistoryDto> getMatchHistoryDtoList() {
        return matchHistoryDtoList;
    }
}
