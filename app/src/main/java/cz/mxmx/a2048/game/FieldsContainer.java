package cz.mxmx.a2048.game;

import java.util.Arrays;

/**
 * Created by mxmx on 18.3.17.
 */

public class FieldsContainer {
    private final int dimension;
    private final Integer[][] fields;

    public FieldsContainer(int dimension) {
        this.dimension = dimension;
        this.fields = new Integer[dimension][dimension];
    }

    public FieldsContainer(FieldsContainer copy){
        this(copy.getDimension());
        this.copyFields(copy.getFields());
    }

    private void copyFields(Integer[][] fields) {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.setField(i, j, fields[i][j]);
            }
        }
    }

    public void setField(int top, int left, Integer value) {
        this.fields[top][left] = value;
    }

    public Integer getField(int top, int left) {
        return this.fields[top][left];
    }

    public int getDimension(){
        return this.dimension;
    }

    public Integer[][] getFields(){
        return this.fields;
    }
}
