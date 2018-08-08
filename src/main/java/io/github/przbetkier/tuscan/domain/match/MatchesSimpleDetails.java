package io.github.przbetkier.tuscan.domain.match;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatchesSimpleDetails {

    private List<SimpleMatch> simpleMatchList;
    private Integer matchesCount;

    @JsonCreator
    public MatchesSimpleDetails(@JsonProperty("items") List<SimpleMatch> simpleMatchList) {
        this.simpleMatchList = simpleMatchList;
        this.matchesCount = this.simpleMatchList.size();
    }

    public List<SimpleMatch> getSimpleMatchList() {
        return simpleMatchList;
    }

    public void setSimpleMatchList(List<SimpleMatch> simpleMatchList) {
        this.simpleMatchList = simpleMatchList;
    }

    public Integer getMatchesCount() {
        return matchesCount;
    }

    public void setMatchesCount(Integer matchesCount) {
        this.matchesCount = matchesCount;
    }
}
