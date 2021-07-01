package com.github.avalon.concurrent.pipeline;

import java.util.function.Consumer;

public abstract class Pipeline<B, A> {

    private Consumer<B> before;
    private Consumer<A> after;

    public abstract void run();

    public Consumer<B> getBefore() {
        return before;
    }

    public void runBefore(B parameter) {
        if(before != null)
            before.accept(parameter);
    }

    public void setBefore(Consumer<B> before) {
        this.before = before;
    }

    public Consumer<A> getAfter() {
        return after;
    }

    public void runAfter(A argument) {
        if(after != null)
            after.accept(argument);
    }

    public void setAfter(Consumer<A> after) {
        this.after = after;
    }
}
