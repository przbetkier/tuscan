package pro.tuscan.domain.stats;

class DemoKill {

    private final String victim;
    private final DemoPosition killerPosition;
    private final DemoPosition victimPosition;
    private final Boolean wallbang;
    private final Boolean headshot;
    private final Boolean entry;
    private final String weapon;

    public DemoKill(String victim, DemoPosition killerPosition, DemoPosition victimPosition, Boolean wallbang,
                    Boolean headshot, Boolean entry, String weapon) {
        this.victim = victim;
        this.killerPosition = killerPosition;
        this.victimPosition = victimPosition;
        this.wallbang = wallbang;
        this.headshot = headshot;
        this.entry = entry;
        this.weapon = weapon;
    }

    public String getVictim() {
        return victim;
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
