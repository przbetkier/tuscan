package io.github.przbetkier.tuscan.domain.stats;

import java.math.BigDecimal;

class DemoPosition {

    private final BigDecimal x;
    private final BigDecimal y;

    public DemoPosition(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }
}
