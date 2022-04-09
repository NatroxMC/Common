package de.natrox.common.runnable;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Runnable that prints exceptions thrown
 */
public record CatchingRunnable(Runnable delegate) implements Runnable {

    public CatchingRunnable(@NotNull Runnable delegate) {
        Preconditions.checkNotNull(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            delegate.run();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
