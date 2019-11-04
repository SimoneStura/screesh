package com.screesh.solver;

import java.time.LocalDateTime;
import java.time.Period;

public interface PlacedOverTime<E> extends Comparable<E> {

    boolean isInConflictWith(E element);

    Period gap(E element);

    boolean sameDayAs(E element);
}
