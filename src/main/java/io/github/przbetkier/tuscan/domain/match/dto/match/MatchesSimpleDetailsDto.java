package io.github.przbetkier.tuscan.domain.match.dto.match;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MatchesSimpleDetailsDto {

    private List<SimpleMatchDto> simpleMatchList;
    private Integer matchesCount;

    @JsonCreator
    public MatchesSimpleDetailsDto(@JsonProperty("items") List<SimpleMatchDto> simpleMatchList) {
        this.simpleMatchList = simpleMatchList;
        this.matchesCount = this.simpleMatchList.size();
    }

    public List<SimpleMatchDto> getSimpleMatchList() {
        return simpleMatchList;
    }

    public void setSimpleMatchList(List<SimpleMatchDto> simpleMatchList) {
        this.simpleMatchList = simpleMatchList;
    }

    public Integer getMatchesCount() {
        return matchesCount;
    }

    public void setMatchesCount(Integer matchesCount) {
        this.matchesCount = matchesCount;
    }
}
