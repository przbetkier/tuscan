package pro.tuscan.adapter.api.response;

import pro.tuscan.adapter.api.response.dto.MatchHistory;

import java.util.List;

public class PlayerHistoryResponse {

    private final List<MatchHistory> matchHistory;

    public PlayerHistoryResponse(List<MatchHistory> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public List<MatchHistory> getMatchHistory() {
        return matchHistory;
    }
}
