package com.screesh.solver;

import java.time.Duration;
import java.time.LocalDateTime;

public interface PlacedOverTime<E> extends Comparable<E> {

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    boolean isInConflictWith(E element);

    Duration gap(E element);

    boolean sameDayAs(E element);
}
