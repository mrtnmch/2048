package cz.mxmx.a2048.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import cz.mxmx.a2048.R;

/**
 * TODO: document your custom view class.
 */
public class GameBoard extends View {
    private int boardBackgroundColor = R.color.colorPrimary;
    private int fieldBackgroundColor = R.color.colorAccent;
    private int boardDimension;
    private int padding = 10;
    private Integer paddingLeft;
    private Integer paddingTop;
    private Integer paddingRight;
    private Paint backgroundPaint;
    private Paint fieldBackgroundPaint;
    private Rect rect;

    public GameBoard(Context context) {
        super(context);
        init(null, 0);
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GameBoard, defStyle, 0);

        try {
            this.boardDimension = a.getInt(R.styleable.GameBoard_boardDimension, this.boardDimension);
            this.boardBackgroundColor = a.getResourceId(R.styleable.GameBoard_boardBackground, ResourcesCompat.getColor(this.getResources(), this.boardBackgroundColor, null));
            this.fieldBackgroundColor = a.getResourceId(R.styleable.GameBoard_fieldBackground, ResourcesCompat.getColor(this.getResources(), this.fieldBackgroundColor, null));
        }
        catch(Exception e) {
            a.recycle();
        }

        this.rect = new Rect(0, 0, 0, 0); // better than allocate new instance every onDraw call

        this.backgroundPaint = new Paint();
        this.backgroundPaint.setColor(ResourcesCompat.getColor(this.getResources(), this.boardBackgroundColor, null));

        this.fieldBackgroundPaint = new Paint();
        this.fieldBackgroundPaint.setColor(ResourcesCompat.getColor(this.getResources(), this.fieldBackgroundColor, null));

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(this.paddingLeft == null) {
            this.paddingLeft = getPaddingLeft();
            this.paddingTop = getPaddingTop();
            this.paddingRight = getPaddingRight();
        }

        int width = getWidth() - paddingLeft - paddingRight;
        this.drawBoard(canvas, width);
        this.drawFields(canvas, paddingLeft, paddingTop, width);
    }

    private void drawBoard(Canvas canvas, int width) {
        this.rect.set(paddingLeft, paddingTop, width, width);
        canvas.drawRect(this.rect, this.backgroundPaint); // this isn't a typo
    }

    private void drawFields(Canvas canvas, int x, int y, int size) {
        int w = (size - ((this.boardDimension + 1) * this.padding))/this.boardDimension;

        for (int i = 1; i <= this.boardDimension; i++) {
            for (int j = 1; j <= this.boardDimension; j++) {
                this.drawField(canvas, x + i * this.padding + (i-1) * w, y + j * this.padding + (j-1) * w, w);
            }
        }
    }

    private void drawField(Canvas canvas, int x, int y, int size) {
        this.rect.set(x, y, x + size, y + size);
        canvas.drawRect(this.rect, this.fieldBackgroundPaint);
    }


    public int getBoardDimension() {
        return this.boardDimension;
    }

    public void setBoardDimension(int dimension) {
        this.boardDimension = dimension;
    }

}
