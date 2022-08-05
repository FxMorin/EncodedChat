package ca.fxco.encodedchat.config;

public class ECConfig {

    @Weight(100)
    public boolean enabled = true;

    @Weight(50)
    public boolean verbose = false;

    public ECConfig validateOnLoad() { // Always runs when config is loaded from file
        return this;
    }
}
