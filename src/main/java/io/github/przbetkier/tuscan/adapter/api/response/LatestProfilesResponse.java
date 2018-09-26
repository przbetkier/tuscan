package io.github.przbetkier.tuscan.adapter.api.response;

import io.github.przbetkier.tuscan.domain.latestProfiles.LatestProfile;

import java.util.List;

public class LatestProfilesResponse {

    private List<LatestProfile> latestProfiles;

    public LatestProfilesResponse(List<LatestProfile> latestProfiles) {
        this.latestProfiles = latestProfiles;
    }

    public List<LatestProfile> getLatestProfiles() {
        return latestProfiles;
    }
}
