package cz.mxmx.a2048.game;

import java.util.Arrays;

/**
 * Container representing a set of fields on the playground.
 */
public class FieldsContainer {
    /** Dimension (d x d) */
    private final int dimension;

    /** Fields itself - value or NULL */
    private final Integer[][] fields;

    /**
     * Creates a new fields container.
     * @param dimension Dimension of the fields (d x d).
     */
    public FieldsContainer(int dimension) {
        this.dimension = dimension;
        this.fields = new Integer[dimension][dimension];
    }

    /**
     * Creates a copy of the passed container.
     * @param copy Container to make a copy of. Is not altered at all.
     */
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
