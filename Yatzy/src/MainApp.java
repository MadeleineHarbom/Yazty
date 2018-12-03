import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MainApp extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Yatzy");
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // -------------------------------------------------------------------------

    // The Yatzy game object
    private Yatzy yatzy = new Yatzy();
    // Shows the face values of the 5 dice.
    private TextField[] txfValues;
    // Shows the hold status of the 5 dice.
    private CheckBox[] chbHolds;

    private TextField txfSumSame, txfBonus, txfSumOther, txfTotal;
    private Label lblRolled, lblscore, lblpreview;
    private Button btnRoll;
    private String[] butLbl = {"1", "2", "3", "4", "5", "6", "One Pair", "Two Pairs", "Three of a kind",
            "Four of a kind", "Full House ", "Small Straight", "Large Straight", "Chance", "Yatzy"};
    private Button[] butArray = new Button[butLbl.length]; // Array with all buttons
    private TextField totalTF;
    private TextField sumTF;
    private TextField[] txfResults = new TextField[butLbl.length]; //Holds tsaves points
    private TextField[] txfResultTest = new TextField[butLbl.length];
    private int totalint = 0;
    private int sumint;
    private int bonus;
    private boolean allCheck = false;

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        // ---------------------------------------------------------------------

        GridPane dicePane = new GridPane();
        pane.add(dicePane, 0, 0);
        dicePane.setGridLinesVisible(false);
        dicePane.setPadding(new Insets(10));
        dicePane.setHgap(10);
        dicePane.setVgap(10);
        dicePane.setStyle("-fx-border-color: black");

        lblRolled = new Label("Rolls: " + yatzy.getThrowCount());
        dicePane.add(lblRolled,4, 3);

        // HBox box1 = new Hbox(yatzy.getValues().length-1]);
        this.txfValues = new TextField[yatzy.getValues().length];
        this.chbHolds = new CheckBox[yatzy.getValues().length];
        for (int i = 0; i < txfValues.length; i++) {
            txfValues[i] = new TextField();
            txfValues[i].setPrefSize(80.0, 80.0);
            chbHolds[i] = new CheckBox();
            chbHolds[i].setText("Hold");
            dicePane.add(txfValues[i], i, 1);
            dicePane.add(chbHolds[i], i,2);;
        }


        

        // ---------------------------------------------------------------------

        GridPane scorePane = new GridPane();
        pane.add(scorePane, 0, 1);
        scorePane.setGridLinesVisible(false);
        scorePane.setPadding(new Insets(10));
        scorePane.setVgap(5);
        scorePane.setHgap(10);
        scorePane.setStyle("-fx-border-color: black");



        btnRoll = new Button("ROLL!");
        dicePane.add(btnRoll, 3, 3);
        btnRoll.setOnAction(event -> this.rollAction());




        for (int i = 0; i <= butLbl.length +1; i ++) {
            if (i <6) {
                Button bk = new Button(butLbl[i]);
                butArray[i] = bk;
                bk.setMinWidth(115.0);
                scorePane.add(butArray[i], 1, i+1);
                TextField td = new TextField("0");
                txfResults[i] = td;
                txfResults[i].setMaxWidth(50.0);
                txfResults[i].setMinWidth(50.0);
                td.setEditable(false);
                scorePane.add(txfResults[i], 2, i+1);
                TextField rt = new TextField("0"); //Made
                txfResultTest[i] = rt; //Made
                txfResultTest[i].setMaxWidth(50.0);
                txfResultTest[i].setMinWidth(50.0);
                rt.setEditable(false); //Made
                scorePane.add(txfResultTest[i], 3, i+1); //Made
            }
            else if (i == 6 ) {
                Label lbltot = new Label("Sum");
                scorePane.add(lbltot, 1 ,i+1);

                sumTF = new TextField("0");
                sumTF.setMaxWidth(50.0);
                sumTF.setMinWidth(50.0);
                scorePane.add(sumTF, 2, i+1);

                Label lblbonus = new Label("Bonus");
                scorePane.add(lblbonus, 1,i+2);

                txfBonus = new TextField("0");
                scorePane.add(this.txfBonus, 2, i+2);
                txfBonus.setMinWidth(50.0);
                txfBonus.setMaxWidth(50.0);
            }
            else if (i <= butArray.length) {
                Button bk = new Button(butLbl[i -1 ]);
                butArray[i -1] = bk;
                bk.setMinWidth(115.0);
                scorePane.add(butArray[i -1], 1, i+3);
                TextField td = new TextField("0");
                txfResults[i -1] = td;
                txfResults[i -1].setMaxWidth(50.0);
                txfResults[i -1].setMinWidth(50.0);
                td.setEditable(false);
                scorePane.add(txfResults[i -1], 2, i+3);
                TextField rt = new TextField("0"); //Made
                txfResultTest[i-1 ] = rt; //Made
                txfResultTest[i-1].setMaxWidth(50.0);
                txfResultTest[i-1].setMaxWidth(50.0);
                rt.setEditable(false); //Made
                scorePane.add(txfResultTest[i-1], 3, i+3); //Made
            }
            else {
                totalTF = new TextField("0");
                totalTF.setMaxWidth(50.0);
                totalTF.setMinWidth(50.0);
                scorePane.add(this.totalTF, 2, i+3);

                Label lbltot = new Label("total");
                scorePane.add(lbltot,1,i+3);
            }
            /**
            Button bk = new Button(butLbl[i]);
            butArray[i] = bk;
            bk.setMinWidth(115.0);
            scorePane.add(butArray[i], 1, i+1);
            TextField td = new TextField("0");
            txfResults[i] = td;
            txfResults[i].setMaxWidth(50.0);
            txfResults[i].setMinWidth(50.0);
            td.setEditable(false);
            scorePane.add(txfResults[i], 2, i+1);
            TextField rt = new TextField("0"); //Made
            txfResultTest[i] = rt; //Made
            txfResultTest[i].setMaxWidth(50.0);
            txfResultTest[i].setMaxWidth(50.0);
            rt.setEditable(false); //Made
            scorePane.add(txfResultTest[i], 3, i+1); //Made
*/

        }

        lblscore = new Label("score");
        scorePane.add(lblscore,2,0);

        lblpreview = new Label("preview");
        scorePane.add(lblpreview, 3,0);

        //sumTF = new TextField("0");
        //scorePane.add(sumTF, 6, 4);

        //txfBonus = new TextField("0");
        //scorePane.add(this.txfBonus, 6, 5);

        //totalTF = new TextField("0");
        //totalTF.setMaxWidth(50.0);
        //totalTF.setMinWidth(50.0);
        //scorePane.add(this.totalTF, 2, 16);

        //Label lbltot = new Label("total");
        //scorePane.add(lbltot,1,16);

        //Label lblbonus = new Label("bonus");
        //scorePane.add(lblbonus, 5,5);

        //Label lblsum = new Label("sum");
        //scorePane.add(lblsum, 5, 4);

        for (int i = 0; i < butArray.length; i++) { // Binds the buttons to saveAction function

            int b = i;
            butArray[i].setOnAction(event -> saveAction(b));
        }


        // TODO: Initialize labels for results, txfResults,
        // labels and text fields for sums, bonus and total.
    }

    // -------------------------------------------------------------------------

    // TODO: Create a method for btnRoll's action.
    private void rollAction() {
        boolean[] holds = new boolean[this.chbHolds.length];

        for (int i = 0; i < holds.length; i++) {
            if (this.chbHolds[i].isSelected()) {
                holds[i] = true;
            }
            else {
                holds[i] = false;
            }
        }

;
        yatzy.throwDice(holds);
        lblRolled.setText("Rolls: " + yatzy.getThrowCount());
        int[] rolls = yatzy.getValues();

        for (int i = 0; i < rolls.length; i++) {
            this.txfValues[i].setText("" + rolls[i]);
        }

        if (yatzy.getThrowCount() >= 3) {
            btnRoll.setDisable(true);
        }

        getPossible();




    }

    // -------------------------------------------------------------------------


    private void getPossible() {
        int[] results = yatzy.getPossibleResults();


        for (int i = 0; i < results.length; i++) {
            txfResultTest[i].setText("" + results[i]);
        }

    }



    private void saveAction (int button) {
        int[] dice = yatzy.getValues();
        if (yatzy.getThrowCount() == 0) {
            Alert needRoll = new Alert(Alert.AlertType.INFORMATION);
            needRoll.setContentText("You need to roll first");
            needRoll.show();
        }
        else {
            int[] possible = yatzy.getPossibleResults();
            int score = possible[button];
            txfResults[button].setText("" + score);
            this.totalint += score;
            this.totalTF.setText(Integer.toString(this.totalint));


            if (button < 7) {
                this.sumint += score;
                if (this.sumint > 62) {
                    this.bonus = 50;
                    this.totalint += 50;
                    //Alert altbonus = // Made
                }
                this.txfBonus.setText(Integer.toString(this.bonus));
            }
            this.sumTF.setText(Integer.toString(this.sumint));

            for (int i = 0; i < this.txfResultTest.length; i++) { //Resets preview
                txfResultTest[i].setText("");
            }

            butArray[button].setDisable(true);
            this.btnRoll.setDisable(false);
            yatzy.resetThrowCount();
            for (int i = 0; i < chbHolds.length; i++) { // Removes holds from checkbuttons
                chbHolds[i].setSelected(false);
            }


            this.allCheck = true; // Checking is the game is over
            for (int i = 0; i < this.butArray.length; i++) {
                if (this.butArray[i].isDisable() == false) {
                    this.allCheck = false;
                }
            }

            if (allCheck == true) { // Ends the game
                Alert print = new Alert(Alert.AlertType.INFORMATION);
                print.setContentText("GameOver! Du har " + Integer.toString(this.totalint) + "points");
                print.show();
                resetBoard();

            }

            for (int i = 0; i < this.txfValues.length; i++) { //Resets displayed rolls, not roll values (yet)
                txfValues[i].setText("");
            }

            int[] temp = {0, 0, 0, 0, 0};
            yatzy.setValues(temp);

            yatzy.resetThrowCount();

        }
    }
        /**
        int[] possible = yatzy.getPossibleResults();
        int score = possible[button];
        txfResults[button].setText("" + score);
        this.totalint += score;
        this.totalTF.setText(Integer.toString(this.totalint));


        if (button < 7) {
            this.sumint += score;
            if (this.sumint > 62) {
                this.bonus = 50;
                this.totalint += 50;
                //Alert altbonus = // Made
            }
            this.txfBonus.setText(Integer.toString(this.bonus));
        }
        this.sumTF.setText(Integer.toString(this.sumint));

        for (int i = 0; i < this.txfResultTest.length; i++) { //Resets preview
            txfResultTest[i].setText("");
        }

        butArray[button].setDisable(true);
        this.btnRoll.setDisable(false);
        yatzy.resetThrowCount();
        for (int i = 0; i < chbHolds.length; i++) { // Removes holds from checkbuttons
            chbHolds[i].setSelected(false);
        }



        this.allCheck = true; // Checking is the game is over
        for (int i = 0; i < this.butArray.length; i++) {
            if (this.butArray[i].isDisable() == false) {
                this.allCheck = false;
            }
        }

        if (allCheck == true) { // Ends the game
            Alert print = new Alert(Alert.AlertType.INFORMATION);
            print.setContentText("GameOver! Du har " + Integer.toString(this.totalint) + "points");
            print.show();
            resetBoard();

        }

        for (int i = 0; i < this.txfValues.length; i++) { //Resets displayed rolls, not roll values (yet)
            txfValues[i].setText("");
        }
    }

    /**
     * Resets the board
     */

    private void resetBoard () { //WTF
        this.sumTF.setText("0");
        this.totalint = 0;
        this.totalTF.setText("0");
        this.sumint = 0;
        this.bonus = 0;
        this.txfBonus.setText("0");
        for (int i = 0; i < txfResults.length; i++) {
            txfResults[i].setText("");
            butArray[i].setDisable(false);
            txfResultTest[i].setText("");


        }
    }


    // TODO: Create a method for mouse click on one of the text fields in txfResults.
    // Hint: Create small helper methods to be used in the mouse click method.


}
