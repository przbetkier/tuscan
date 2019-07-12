package io.github.przbetkier.tuscan.adapter.api.response.dto;

public class BanInfo {

    private final boolean active;

    public BanInfo(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

}
