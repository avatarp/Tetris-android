package com.example.tetris;

public class GameStats {
    private int score;
    private int linesCleared;
    private int totalBlocks;
    private int rotationsLeft;
    private int rotationsRight;
    private int movesLeft;
    private int movesRight;

    public GameStats()
    {
        score=0;
        linesCleared=0;
        totalBlocks=0;
        rotationsLeft=0;
        rotationsRight=0;
        movesLeft=0;
        movesRight=0;
    }
    void setStats(int newScore,int newLines,int newBlocks,int newRLeft,int newRRight,
                  int newMovesLeft, int newMovesRight){
        score=newScore;
        linesCleared=newLines;
        totalBlocks=newBlocks;
        rotationsLeft=newRLeft;
        rotationsRight=newRRight;
        movesLeft=newMovesLeft;
        movesRight=newMovesRight;
    }

    public void incScore(int scored) {
        score+=scored;}
    public void incLinesCleared(int lines) {
        linesCleared+=lines;}
    public void incBlocks() {
        totalBlocks++;}
    public void incRotLeft() {
        rotationsLeft++;}
    public void incRotRight() {
        rotationsRight++;}
    public void incMovesLeft() {
        movesLeft++;}
    public void incMovesRight() {
        movesRight++;}

    public int getScore(){return score;}    
        
    public int getLinesCleared() {
        return linesCleared;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public int getRotationsLeft() {
        return rotationsLeft;
    }

    public int getRotationsRight() {
        return rotationsRight;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public int getMovesRight() {
        return movesRight;
    }
}
