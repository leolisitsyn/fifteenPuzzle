package com.foxliss.fifteen;


import java.util.ArrayList;
import java.util.Random;

class Field {
    final int size;
    final int iterations;
    final int empty;

    int[] field;
    final int[] winning;

    Field(int sizeId, int iterations, int nEmpty){
        this.size = sizeId + 2;
        this.iterations = iterations;
        this.empty = nEmpty;
        winning = winningField();
        field = new int[size*size];
        field = createField();
    }

    void setField(String[] field) {
        for (int i = 0; i < size * size; i++) {
            this.field[i] = Integer.parseInt(field[i]);
        }
    }

    int[] createField(){

        int[] gameField = winningField();
        for (int i = 0; i < iterations * 5; i++) {
            switchRandom();
        }
        return gameField;
    }

    int[] winningField() {
        int square = size * size;
        int[] winningField = new int[square];
        for (int i = 0; i < square-empty; i++) {
            winningField[i] = i + 1;
        }
        return winningField;
    }

    private void switchRandom() {
        Random random = new Random();

        ArrayList<Integer> emptyPositions = findEmpty();
        int emptyIdx = emptyPositions.get(random.nextInt(emptyPositions.size()));

        ArrayList<Integer> neighbours = findNeighbours(emptyIdx);

        int randomNeighbour = neighbours.get(random.nextInt(neighbours.size()));

        int tmp = field[randomNeighbour];
        field[emptyIdx] = tmp;
        field[randomNeighbour] = 0;
    }

    private ArrayList<Integer> findEmpty() {
        ArrayList<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            if (field[i] == 0) emptyPositions.add(i);
        }
        return emptyPositions;
    }

    public ArrayList<Integer> findNeighbours(int pos){
        ArrayList<Integer> neighbours = new ArrayList<>();
        int row = pos / size;
        int column = pos % size;
        if (row - 1 >= 0) neighbours.add(pos - size);
        if (row + 1 < size) neighbours.add(pos + size);
        if (column - 1 >= 0) neighbours.add(pos - 1);
        if (column + 1 < size) neighbours.add(pos + 1);
        return neighbours;
    }

    public int[] getField() {
        return field;
    }

    public int getSize() {
        return size;
    }

    public void changeField(int pos, int idx){
        int tmp = field[pos];
        field[pos] = field[idx];
        field[idx] = tmp;
    }
}
