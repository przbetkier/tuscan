package pro.tuscan.domain.stats;

class DemoDeath {

    private final String killer;
    private final DemoPosition killerPosition;
    private final DemoPosition victimPosition;
    private final Boolean wallbang;
    private final Boolean headshot;
    private final Boolean entry;
    private final String weapon;

    public DemoDeath(String killer, DemoPosition killerPosition, DemoPosition victimPosition, Boolean wallbang,
                     Boolean headshot, Boolean entry, String weapon) {
        this.killer = killer;
        this.killerPosition = killerPosition;
        this.victimPosition = victimPosition;
        this.wallbang = wallbang;
        this.headshot = headshot;
        this.entry = entry;
        this.weapon = weapon;
    }

    public String getKiller() {
        return killer;
    }

    public DemoPosition getKillerPosition() {
        return killerPosition;
    }

    public DemoPosition getVictimPosition() {
        return victimPosition;
    }

    public Boolean getWallbang() {
        return wallbang;
    }

    public Boolean getHeadshot() {
        return headshot;
    }

    public Boolean getEntry() {
        return entry;
    }

    public String getWeapon() {
        return weapon;
    }
}
