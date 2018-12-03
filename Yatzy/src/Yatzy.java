import java.util.Random;

public class Yatzy {

    private int[] values = new int[5];
    private int throwCount = 0; // Number of times the 5 dice have been thrown.
    private int[] frequenzy = new int[7];
    private Random random = new Random();

    /**
     * Rolls the 5 dice. <br/>
     * Only roll dice that are not hold. <br/>
     * Requires: holds contain 5 boolean values.
     */
    public void throwDice(boolean[] holds) {
       this.throwCount ++;
       for (int die = 0; die < values.length; die ++) {
           if (holds[die] == false) {
               values[die] = random.nextInt(6) +1;
           }
       }
        
    }

    /**
     * Returns the number of times the five dice have been thrown.
     */
    public int getThrowCount() {
        return throwCount;
    }

    /**
     * Resets the throw count.
     */
    public void resetThrowCount() {
        throwCount = 0;
    }

    /**
     * Get current dice values
     */
    public int[] getValues() {
        return values;
    }

    /**
     * Set the current dice values
     */
    public void setValues(int[] values) {
        this.values = values;
    }

    /**
     * Returns all results possible with the current face values. <br/>
     * The order of the results is the same as on the score board.
     */
    public int[] getPossibleResults() {
        int[] results = new int[15];
        for (int i = 0; i <= 5; i++) {
            results[i] = this.valueSpecificFace(i + 1);
        }
        results[6] = this.valueOnePair();
        results[7] = this.valueTwoPair();
        results[8] = this.valueThree();
        results[9] = this.valueFour();
        results[10] = this.valueFullHouse();
        results[11] = this.valueSmallStraight();
        results[12] = this.valueLargeStraight();
        results[13] = this.valueChance();
        results[14] = this.valueYatzy();
        return results;
    }

    /**
     * Returns an int[7] containing the frequency of face values. <br/>
     * Frequency at index v is the number of dice with the face value v, 1 <= v <= 6. <br/>
     * Index 0 is not used.
     */
    private int[] freqFaceValue() {
        int[] current = new int[7];
        for (int face = 1; face <= 6; face++) {
            for (int value = 0; value < this.values.length; value++) {
                if (this.values[value] == face) {
                    current[face]++;
                }
            }
        }
        return current;
    }



    /**
     * Returns the total value for the dice currently showing the given face value
     * @param face the face value to return values for
     */
    public int valueSpecificFace(int face) {
        int total = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == face) {
                total += face;
            }
        }
        return total;
    }

    /**
     * Returns the total values of the roll in a whole game
     * @return
     */
    private void freqFaceValueGame() { //Ikke i opgaven
        for (int face = 1; face <= 6; face++) {
            for (int value = 0; value < values.length; value++) {
                if (values[value] == face) {
                    this.frequenzy[value]++;
                }
            }
        }
    }

    /**
     * Returns the maximum value for n-of-a-kind for the given n. <br/>
     * For example, valueManyOfAKind(3) returns the maximum value for 3 of-a-kind. <br/>
     * Requires: 1 <= faceValue and faceValue <= 6
     * @param n number of kind
     */
    public int valueManyOfAKind(int n) {
        int[] current = freqFaceValue();
        int place = 0;
        for (int i = 1; i < current.length; i++) {
            if (current[i] >= n) {
                place = i;
            }
        }
        int sum = place * n;

        return sum;


    }

    public int howManyOfFace (int n) { // Ikke i opgaven
        int counter = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == n) {
                counter++;
            }
        }
        return counter;

    }

    /**
     * The current value if you try to score the current face values as Yatzy.
     */
    public int valueYatzy() {
        int[] current = freqFaceValue();
        int sum = 0;
        for(int value : current) {
            if (value == 5) {
                sum = 50;
            }
        }
        return sum;
    }

    /**
     * Returns the current score if used as "chance".
     */
    public int valueChance() {
        int sum = 0;
        for (int value : this.values) {
            sum += value;
        }
        return sum;
    }

    /**
     * Returns the current score for one pair.
     */
    public int valueOnePair() {
        int[] current = freqFaceValue();
        int sum = 0;
        for (int i = 1; i < current.length; i++) {
            if (current[i] >= 2) {
                sum = i * 2;
            }
        }

        return sum;
    }

    /**
     * Returns the current score for two pairs.
     */
    public int valueTwoPair() {
        int[] current = freqFaceValue();
        int counter = 0;
        int sum = 0;
        for (int i = 1; i < current.length; i++) {
            if (current[i] >= 2) {
                sum += (i * 2);
                counter ++;
            }


        }
        if (counter != 2) {
            sum = 0;
        }
        return sum;
    }

    /**
     * Returns the current score for three of a kind.
     */
    public int valueThree() {
        int sum = valueManyOfAKind(3);
        return sum;
    }

    /**
     * Returns the current score for four of a kind.
     */
    public int valueFour() {
        int sum = valueManyOfAKind(4);
        return sum;
    }

    /**
     * Returns the value of a small straight with the current face values.
     */
    public int valueSmallStraight() {
        int[] current = freqFaceValue();
        int sum = 0;
        boolean small = true;
        for (int i = 1; i < current.length -1; i++) {
            if (current[i] != 1) {
                small = false;
            }
        }
        if (small == true) {
            sum = 1 +2+3+4+5;

        }
        return sum;
    }

    /**
     * Returns the value of a large straight with the current face values.
     */
    public int valueLargeStraight() {
        int[] current = freqFaceValue();
        int sum = 0;
        boolean large = true;
        for (int i = 2; i < current.length -2; i++) {
            if (current[i] != 1) {
                large = false;
            }
        }
        if (large == true) {
            sum = 2+3+4+5+6;

        }
        return sum;
    }

    /**
     * Returns the value of a full house with the current face values.
     */
    public int valueFullHouse() {
        int[] current = freqFaceValue();
        int sum = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 1; i < current.length; i++) {
            if (current[i] == 2) {
                sum2 = (i * 2);

            }
            else if (current[i] == 3) {
                sum3 = (i * 3) ;
            }
            if (sum2 != 0  && sum3 != 0) {
                sum = sum2 + sum3;
            }



        }

        return sum;
    }
}
