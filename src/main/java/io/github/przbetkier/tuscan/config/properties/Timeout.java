package io.github.przbetkier.tuscan.config.properties;

import javax.validation.constraints.Min;

public class Timeout {

    @Min(1)
    private int read;

    @Min(1)
    private int connect;

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }
}
