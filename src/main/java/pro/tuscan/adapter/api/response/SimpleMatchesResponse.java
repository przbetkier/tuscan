package pro.tuscan.adapter.api.response;

import pro.tuscan.adapter.api.response.dto.SimpleMatch;

import java.util.List;

public class SimpleMatchesResponse {

    private List<SimpleMatch> simpleMatchList;
    private Integer matchesCount;

    public SimpleMatchesResponse(List<SimpleMatch> simpleMatchList, Integer matchesCount) {
        this.simpleMatchList = simpleMatchList;
        this.matchesCount = matchesCount;
    }

    public List<SimpleMatch> getSimpleMatchList() {
        return simpleMatchList;
    }

    public Integer getMatchesCount() {
        return matchesCount;
    }
}
