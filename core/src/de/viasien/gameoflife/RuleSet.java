package de.viasien.gameoflife;

/**
 * Created by jannis on 24.09.17.
 */
public class RuleSet {

    private boolean[][] currentField;

    public RuleSet(boolean[][] currentField) {
        this.currentField = currentField;
    }

    public boolean[][] basicRules() {
        boolean[][] newPhase = new boolean[currentField.length][currentField[0].length];

        for(int x=0; x<currentField.length; x++) {
            for(int y=0; y<currentField[x].length; y++) {
                int livingNeighbours = getLivingNeighbourCount(x,y);

                if( currentField[x][y] ) {
                    // current cell is alive

                    if( livingNeighbours < 2 )
                        newPhase[x][y] = false;
                    else if( livingNeighbours > 3 )
                        newPhase[x][y] = false;
                    else // 2 or 3
                        newPhase[x][y] = true;

                } else {
                    // current cell is dead

                    if( livingNeighbours == 3 ) {
                        newPhase[x][y] = true;
                    }

                }

            }
        }

        currentField = newPhase;
        return newPhase;
    }

    private int getLivingNeighbourCount(int x, int y) {
        if( isInBounds(x,y) ) {

            int counter = 0;

            for(int i=x-1; i<=x+1; i++) {

                if( isInBounds(i, y+1) && currentField[i][y+1] ) {
                    counter++;
                }

                if( x!=i && isInBounds(i, y) && currentField[i][y] ) {
                    counter++;
                }

                if( isInBounds(i, y-1) && currentField[i][y-1] ) {
                    counter++;
                }

            }

            return counter;

        } else {
            return 0;
        }
    }

    private boolean isInBounds(int x, int y) {
        return x>=0 && y>=0 && x<currentField.length && y<currentField[0].length;
    }

}
