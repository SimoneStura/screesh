package com.screesh.solver;

import java.time.Duration;

public interface PlacedOverTime<E> extends Comparable<E> {

    boolean isInConflictWith(E element);

    Duration gap(E element);

    boolean sameDayAs(E element);
}
