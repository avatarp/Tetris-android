package com.example.tetris.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Renderer {
    private int gameHeight;
    private int gameWidth;

    private int nextBlockHeight;
    private int nextBlockWidth;

    private int partHeight;
    private int partWidth;

    private static final int gridRows=20;
    private static final int gridColumns=10;

    private Paint whitePaint;
    private Paint blackPaint;
    private Paint redPaint;
    private Paint greenPaint;
    private Paint bluePaint;

    public Renderer(int height,int width, int blockHeight,int blockWidth) {

        setupPaint();
        gameHeight=height;
        gameWidth=width;
        partHeight=gameHeight/gridRows;
        partWidth=gameWidth/gridColumns;
        nextBlockWidth=blockWidth;
        nextBlockHeight=blockHeight;
    }

    private void setupPaint(){

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL);

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.FILL);

        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);

        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(3);
    }

    private Paint choosePaint(Byte color) {
        Paint paint=whitePaint;
        switch (color) {
            case 1:paint=redPaint;break;
            case 2:paint=greenPaint;break;
            case 3:paint=bluePaint;break;
        }
        return paint;
    }

    public Bitmap drawGame(Engine game) {

        Canvas gameCanvas = new Canvas();
        Bitmap gameBitmap = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.ARGB_8888);
        gameCanvas.setBitmap(gameBitmap);

        Paint color;

        //draw static background
        for (int row = 0; row < gridRows; row++) {
            for (int column = 0; column < gridColumns; column++) {

                color=choosePaint(game.well.getGameGrid()[row][column]);

                if (game.well.getGameGrid()[row][column] > 0) {
                    //draw block
                    gameCanvas.drawRect(column * partWidth,
                            row * partHeight,
                            column * partWidth + partWidth,
                            row * partHeight + partHeight,
                            color);
                    //draw outline
                    gameCanvas.drawRect(column * partWidth,
                            row * partHeight,
                            column * partWidth + partWidth,
                            row * partHeight + partHeight,
                            blackPaint);
                }
            }
        }

        //draw current block on the background
        for (int row = 0; row < gridRows; row++) {
            for (int column = -3; column < gridColumns; column++) {
                if (game.currBlock.coordX == column && game.currBlock.coordY == row) {
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (game.currBlock.getPosition()[l][k]) {
                                //if block is not empty
                                color=choosePaint(game.currBlock.getColor());
                                if (game.currBlock.getColor() > 0) {
                                    //draw block
                                    gameCanvas.drawRect(column * partWidth + k * partWidth,
                                            row * partHeight + l * partHeight,
                                            column * partWidth + partWidth + k * partWidth,
                                            row * partHeight + partHeight + l * partHeight,
                                            color);
                                    //draw outline
                                    gameCanvas.drawRect(column * partWidth + k * partWidth,
                                            row * partHeight + l * partHeight,
                                            column * partWidth + partWidth + k * partWidth,
                                            row * partHeight + partHeight + l * partHeight,
                                            blackPaint);
                                }
                            }
                        }
                    }
                }
            }
        }
        //draw game outline
        gameCanvas.drawRect(0, 0, gameWidth - 1, gameHeight - 1, whitePaint);
        return gameBitmap;
    }


   public Bitmap drawNextBlock(Engine game) {

        Canvas nextBlockCanvas = new Canvas();
        Bitmap nextBlockMap = Bitmap.createBitmap(nextBlockWidth, nextBlockHeight,
                Bitmap.Config.ARGB_8888);
        nextBlockCanvas.setBitmap(nextBlockMap);

        Paint color;

        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (game.nextBlock.getPosition()[l][k]) {

                    color=choosePaint(game.nextBlock.getColor());

                    if (game.nextBlock.getColor() > 0) {
                        nextBlockCanvas.drawRect(k * partWidth,
                                l * partHeight,
                                partWidth + k * partWidth,
                                partHeight + l * partHeight,
                                color);
                        nextBlockCanvas.drawRect(k * partWidth,
                                l * partHeight,
                                partWidth + k * partWidth,
                                partHeight + l * partHeight,
                                blackPaint);
                    }
                }
            }
        }
        return nextBlockMap;
    }

}
