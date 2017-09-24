package de.viasien.gameoflife;

/**
 * Created by jannis on 23.09.17.
 */
public class Field {

    private boolean play = false;

    private boolean[][] isAlive;

    private long lastPhaseChange;
    public int phaseCounter = 0;

    public Field() {

        reset();

    }

    public void reset() {
        int width = Parameters.nCellsX;
        int height = Parameters.nCellsY;

        isAlive = new boolean[width][height];

        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                isAlive[x][y] = false;
            }
        }

        lastPhaseChange = System.currentTimeMillis();
        phaseCounter = 0;

    }

    public void phasePush() {
        // apply rules
        phaseCounter++;

        RuleSet ruleSet = new RuleSet(isAlive);
        isAlive = ruleSet.basicRules();
    }

    public void act() {
        if(play && System.currentTimeMillis() - lastPhaseChange > Parameters.updateInterval) {
            lastPhaseChange = System.currentTimeMillis();
            phasePush();
        }
    }

    public void pause() {
        play = false;
    }

    public void play() {
        play = true;
    }

    public int getWidth() { return Parameters.nCellsX; }

    public int getHeight() { return Parameters.nCellsY; }

    /**
     * x and y starting at 0
     * @param x
     * @param y
     * @return
     */
    public boolean isAlive(int x, int y) {
        if( !isInBounds(x,y) )  {
            throw new IllegalArgumentException("Coordinates exceed limits");
        }
        return isAlive[x][y];
    }

    public void setAlive(boolean alive, int x, int y) {
        if( !isInBounds(x,y) )  {
            throw new IllegalArgumentException("Coordinates exceed limits");
        }

        isAlive[x][y] = alive;
    }

    public boolean isInBounds(int x, int y) {
        return x>=0 && y>=0 & x<isAlive.length && y<isAlive[0].length;
    }

}
