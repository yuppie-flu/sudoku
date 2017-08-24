package com.github.yuppieflu;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableValidator {

    private static final Type LIST_OF_LIST_OF_INT = new TypeToken<List<List<Integer>>>() {}.getType();

    private final Gson gson = new Gson();

    public boolean validate(String tableAsJsonString) {
        SudokuTable table;
        try {
            table = new SudokuTable(gson.fromJson(tableAsJsonString, LIST_OF_LIST_OF_INT));
        } catch (JsonSyntaxException e) {
            System.err.println("Unexpected format of input table:");
            e.printStackTrace();
            return false;
        }

        return validateAllCells(table) &&
                areAllRowsUnique(table) &&
                areAllColumnsUnique(table) &&
                areAllSquaresUnique(table);
    }

    private boolean validateAllCells(SudokuTable table) {
        return table.getAllCellsAsStream()
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .allMatch(i -> i >= 1 && i <= 9);
    }

    private boolean areAllRowsUnique(SudokuTable table) {
        return table.getRows().stream().allMatch(this::are9CellsUnique);
    }

    private boolean areAllColumnsUnique(SudokuTable table) {
        return table.getColumns().stream().allMatch(this::are9CellsUnique);
    }

    private boolean areAllSquaresUnique(SudokuTable table) {
        return table.getSquares().stream().allMatch(this::are9CellsUnique);
    }

    private boolean are9CellsUnique(List<Integer> cells) {
        Preconditions.checkArgument(cells.size() == SudokuTable.DIMENSION,
                "Unexpected number of cells " + cells.size() + ", must be " + SudokuTable.DIMENSION);
        List<Integer> cellsWithDigits = cells.stream()
                                         .filter(Objects::nonNull)
                                         .collect(Collectors.toList());
        return cellsWithDigits.size() == cellsWithDigits.stream().distinct().count();
    }
}
