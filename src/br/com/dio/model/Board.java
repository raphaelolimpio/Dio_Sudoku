package br.com.dio.model;

import java.util.Collection;
import java.util.List;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream()
                .flatMap(Collection::stream)
                .noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return GameStatusEnum.NON_STARTED;
        }
        return spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> isNull(s.getActual())) ? GameStatusEnum.INCOMPLET : GameStatusEnum.COMPLETE;
    }

    public boolean hasError() {
        if (getStatus() == GameStatusEnum.NON_STARTED) {
            return false;
        }
        return spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && s.getActual().equals(s.getExpected()));
    }

    public boolean changeValues(final int col, final int row, final int values) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }
        space.setActual(values);
        return true;

    }

    public boolean clearValue(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }
        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasError() && getStatus().equals(GameStatusEnum.COMPLETE);
    }

}
