package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.adapter.api.response.dto.SimpleMatch;

import java.util.Set;

public class SimpleMatchesResponse {

    private Set<SimpleMatch> simpleMatchList;
    private Integer matchesCount;

    public SimpleMatchesResponse(Set<SimpleMatch> simpleMatchList, Integer matchesCount) {
        this.simpleMatchList = simpleMatchList;
        this.matchesCount = matchesCount;
    }

    public Set<SimpleMatch> getSimpleMatchList() {
        return simpleMatchList;
    }

    public Integer getMatchesCount() {
        return matchesCount;
    }
}
