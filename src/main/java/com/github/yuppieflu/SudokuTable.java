package com.github.yuppieflu;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Data
class SudokuTable {
    public static final int DIMENSION = 9;

    private final List<List<Integer>> table;

    Stream<Integer> getAllCellsAsStream() {
        return table.stream().flatMap(Collection::stream);
    }

    List<List<Integer>> getRows() {
        return table;
    }

    List<List<Integer>> getColumns() {
        List<List<Integer>> columns = new ArrayList<>(DIMENSION);
        for (int i = 0; i < DIMENSION; i++) {
            List<Integer> singleColumn = new ArrayList<>(DIMENSION);
            for (int j = 0; j < DIMENSION; j++) {
                singleColumn.add(table.get(j).get(i));
            }
            columns.add(singleColumn);
        }
        return columns;
    }

    List<List<Integer>> getSquares() {
        List<List<Integer>> squares = new ArrayList<>(DIMENSION);
        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {
                List<Integer> square = new ArrayList<>(DIMENSION);
                for (int k = 0; k < 3; k++) {
                    for (int n = 0; n < 3; n++) {
                        square.add(table.get(i + k).get(j + n));
                    }
                }
                squares.add(square);
            }
        }
        return squares;
    }
}
