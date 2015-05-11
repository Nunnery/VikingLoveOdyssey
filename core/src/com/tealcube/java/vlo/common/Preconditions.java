package com.tealcube.java.vlo.common;

public final class Preconditions {

    private Preconditions() {
        // do nothing
    }

    public static void checkNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException("o cannot be null");
        }
    }

    public static void checkNotNull(Object o, String msg) {
        if (o == null) {
            throw new NullPointerException(msg);
        }
    }

    public static void checkArgument(boolean arg) {
        if (!arg) {
            throw new IllegalArgumentException("arg is false");
        }
    }

    public static void checkArgument(boolean arg, String msg) {
        if (!arg) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void checkState(boolean state) {
        if (!state) {
            throw new IllegalStateException("state is false");
        }
    }

    public static void checkState(boolean state, String msg) {
        if (!state) {
            throw new IllegalStateException(msg);
        }
    }

}
