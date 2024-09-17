package com.shpp.p2p.cs.anemeritskyy.assignment10;

public enum Trigonometry {
    sin("sin"),
    cos("cos"),
    tan("tan"),
    atan("atan"),
    log10("log10"),
    log2("log2"),
    sqrt("sqrt");

    public final String value;

    Trigonometry(String func) {
        this.value = func;
    }
}
