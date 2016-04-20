package com.company;

import Game.CPU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FourChess implements ActionListener {
    //Setting up ALL the variables
    JFrame window = new JFrame("Four Chess");

    JMenuBar mnuMain = new JMenuBar();
    JMenuItem mnuNewGame = new JMenuItem("New Game"),
            mnuInstruction = new JMenuItem("Instructions"),
            mnuExit = new JMenuItem("Exit"),
            mnuAbout = new JMenuItem("About");

    JButton btn1v1 = new JButton("Player vs Player"),
            btnQuit = new JButton("Quit"),
            btnSetName = new JButton("Set Player Names"),
            btnContinue = new JButton("Continue..."),
            btnTryAgain = new JButton("Try Again?");
    JButton btnEmpty[] = new JButton[17];

    JPanel pnlNewGame = new JPanel(),
            pnlMenu = new JPanel(),
            pnlMain = new JPanel(),
            pnlTop = new JPanel(),
            pnlBottom = new JPanel(),
            pnlQuitNTryAgain = new JPanel(),
            pnlPlayingField = new JPanel();

    JLabel lblTitle = new JLabel("Four Chess"),
            lblTurn = new JLabel(),
            lblStatus = new JLabel("", JLabel.CENTER),
            lblMode = new JLabel("", JLabel.LEFT);
    JTextArea txtMessage = new JTextArea();

    final int winGame[][] = new int[][]{
            {1, 2, 3, 4}, {1, 5, 9, 13},
            {5, 6, 7, 8}, {2, 6, 10, 14},
            {9, 10, 11, 12}, {3, 7, 11, 15},
            {13, 14, 15, 16}, {4, 8, 12, 16},
            {1, 6, 11, 16}, {4, 7, 10, 13}
/*Horizontal Wins*/	/*Vertical Wins*/ /*Diagonal Wins*/
    };
    int X = 500;                      //size of window vertical
    int Y = 500;                      //size of window horizontal
    int mainColorR = 50;             // size of lettering - FIRST GAMER
    int mainColorG = 50;
    int mainColorB = 50;
    int mainColorE = 50;

    int turn = 1,
            player1Won = 0, player2Won = 0,
            wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1, wonNumber4 = 1,
            option;
    boolean inGame = false,
            CPUGame = false,
            win = false;
    String message,
            Player1 = "Player 1", Player2 = "Player 2",
            tempPlayer2 = "Player 2";

    public FourChess() {    //Setting game properties and layout and sytle...
        //Setting window properties:
        window.setSize(X, Y);
        window.setLocation(100, 100);              //where to show the window in desktop on the computer
        //window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Setting Menu, Main, Top, Bottom Panel Layout/Backgrounds
        pnlMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));

        pnlNewGame.setBackground(new Color(mainColorR - 50, mainColorG - 50, mainColorB - 50, mainColorE - 50));
        pnlMenu.setBackground(new Color((mainColorR - 50), (mainColorG - 50), (mainColorB - 50), (mainColorE - 50)));
        pnlMain.setBackground(new Color(mainColorR, mainColorG, mainColorB, mainColorE));
        pnlTop.setBackground(new Color(mainColorR, mainColorG, mainColorB, mainColorE));
        pnlBottom.setBackground(new Color(mainColorR, mainColorG, mainColorB, mainColorE));

        //Setting up Panel QuitNTryAgain in row
        pnlQuitNTryAgain.setLayout(new GridLayout(1, 2, 2, 2));
        pnlQuitNTryAgain.add(btnTryAgain);
        pnlQuitNTryAgain.add(btnQuit);

        //Adding menu items to menu bar
        mnuMain.add(mnuNewGame);
        mnuMain.add(mnuInstruction);
        mnuMain.add(mnuAbout);
        mnuMain.add(mnuExit);//	Menu Bar is Complete

        //Adding buttons to NewGame panel
        pnlNewGame.setLayout(new GridLayout(1, 1, 2, 10));
        pnlNewGame.add(btnContinue);
        pnlNewGame.add(btn1v1);
        pnlNewGame.add(btnSetName);

        //Setting Button propertied
        btnTryAgain.setEnabled(false);
        btnContinue.setEnabled(false);

        //Setting txtMessage Properties
        txtMessage.setBackground(new Color(mainColorR - 30, mainColorG - 30, mainColorB - 30, mainColorE - 30));
        txtMessage.setForeground(Color.white);
        txtMessage.setEditable(false);

        //Adding Action Listener to all the Buttons and Menu Items
        mnuNewGame.addActionListener(this);
        mnuExit.addActionListener(this);
        mnuInstruction.addActionListener(this);
        mnuAbout.addActionListener(this);
        btn1v1.addActionListener(this);
        //btn1vCPU.addActionListener(this);
        btnQuit.addActionListener(this);
        btnSetName.addActionListener(this);
        btnContinue.addActionListener(this);
        btnTryAgain.addActionListener(this);

        //Setting up the playing field
        pnlPlayingField.setLayout(new GridLayout(4, 4, 3, 3));
        pnlPlayingField.setBackground(Color.RED);
        for (int i = 1; i <= 16; i++) {
            btnEmpty[i] = new JButton();
            //btnEmpty[i].setBackground(new Color(btnColorR, btnColorG, btnColorB, btnColorE));
            btnEmpty[i].addActionListener(this);
            pnlPlayingField.add(btnEmpty[i]);   //	Playing Field is Complete
        }

        //Adding everything needed to pnlMenu and pnlMain
        lblMode.setForeground(Color.WHITE);
        pnlMenu.add(lblMode);
        pnlMenu.add(mnuMain);
        pnlMain.add(lblTitle);

        //Adding to window and Showing window
        window.add(pnlMenu, BorderLayout.NORTH);
        window.add(pnlMain, BorderLayout.CENTER);
        window.setVisible(true);
    }
    public static void main(String[] args) {
        new FourChess(); //	Calling the class constructor.
        // PROGRAM STARTS HERE!
    }

    /*
                -------------------------
                Start of all METHODS.	|
                -------------------------
        */
    public void showGame() {    //	Shows the Playing Field
        //	*IMPORTANT*- Does not start out brand new (meaning just shows what it had before)
        clearPanelSouth();
        pnlMain.setLayout(new BorderLayout());
        pnlTop.setLayout(new BorderLayout());
        pnlBottom.setLayout(new BorderLayout());
        pnlTop.add(pnlPlayingField);
        pnlBottom.add(lblTurn, BorderLayout.WEST);
        pnlBottom.add(lblStatus, BorderLayout.CENTER);
        pnlBottom.add(pnlQuitNTryAgain, BorderLayout.EAST);
        pnlMain.add(pnlTop, BorderLayout.CENTER);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);
        pnlPlayingField.requestFocus();
        inGame = true;
        checkTurn();
        checkWinStatus();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public void newGame() {    //	Sets all the game required variables to default
        //	and then shows the playing field.
        //	(Basically: Starts a new 1v1 Game)
        for (int i = 1; i < 16; i++) {
            btnEmpty[i].setText("");
            btnEmpty[i].setEnabled(true);
        }
        turn = 1;
        win = false;
        showGame();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public void quit() {
        inGame = false;
        lblMode.setText("");
        btnContinue.setEnabled(false);
        clearPanelSouth();
        setDefaultLayout();
        pnlTop.add(pnlNewGame);
        pnlMain.add(pnlTop);
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public void checkWin() {        //	checks if there are 4 symbols in a row vertically, diagonally, or horizontally.
        //	then shows a message and disables buttons. If the game is over then it asks
        //	if you want to play again.
        for (int i = 0; i < 15; i++) {
            if (
                    !btnEmpty[winGame[i][0]].getText().equals("") &&
                            btnEmpty[winGame[i][0]].getText().equals(btnEmpty[winGame[i][1]].getText()) &&
                            //								if {0 == 1 && 1 == 2}
                            btnEmpty[winGame[i][1]].getText().equals(btnEmpty[winGame[i][2]].getText()) &&
                            //								if {2 == 3}
                            btnEmpty[winGame[i][2]].getText().equals(btnEmpty[winGame[i][3]].getText())) {
                /*
					The way this checks the if someone won is:
					First: it checks if the btnEmpty[x] is not equal to an empty string-	x being the array number
						inside the multi-dimensional array winGame[checks inside each of the 7 sets][the first number]
					Second: it checks if btnEmpty[x] is equal to btnEmpty[y]- x being winGame[each set][the first number]
						y being winGame[each set the same as x][the second number] (So basically checks if the first and
						second number in each set is equal to each other)
					Third: it checks if btnEmtpy[y] is eual to btnEmpty[z]- y being the same y as last time and z being
						winGame[each set as y][the third number]
					Fourth: it checks if btnEmtpy[y] is eual to btnEmpty[z]- y being the same y as last time and z being
						winGame[each set as y][the fourth number]
					Conclusion:	So basically it checks if it is equal to the btnEmpty is equal to each set of numbers
				*/
                win = true;
                wonNumber1 = winGame[i][0];
                wonNumber2 = winGame[i][1];
                wonNumber3 = winGame[i][2];
                wonNumber4 = winGame[i][3];
                break;
            }
        }
        if (win || (!win && turn > 16)) {
            if (win) {
                if (btnEmpty[wonNumber1].getText().equals("FIRST GAMER")) {
                    message = Player1 + " Has won";
                    player1Won++;
                } else {
                    message = Player2 + " Has won";
                    player2Won++;
                }
            } else if (!win && turn > 15)
                message = "Both players have tied!\nBetter luck next time.";
            showMessage(message);
            for (int i = 1; i <= 16; i++) {
                btnEmpty[i].setEnabled(false);
            }
            btnTryAgain.setEnabled(true);
            checkWinStatus();
        } else
            checkTurn();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void AI() {
        int computerButton;
        if (turn <= 16) {
            turn++;
            computerButton = CPU.doMove(
                    btnEmpty[1], btnEmpty[2], btnEmpty[3], btnEmpty[4],
                    btnEmpty[5], btnEmpty[6], btnEmpty[7], btnEmpty[8],
                    btnEmpty[9], btnEmpty[10], btnEmpty[11], btnEmpty[12],
                    btnEmpty[13], btnEmpty[14], btnEmpty[15], btnEmpty[16]);
            if (computerButton == 0)
                Random();
            else {
                btnEmpty[computerButton].setText("SECOND GAMER");
                btnEmpty[computerButton].setEnabled(false);
            }
            checkWin();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void Random() {
        int random;
        if (turn <= 16) {
            random = 0;
            while (random == 0) {
                random = (int) (Math.random() * 10);
            }
            if (CPU.doRandomMove(btnEmpty[random])) {
                btnEmpty[random].setText("SECOND GAMER");
                btnEmpty[random].setEnabled(false);
            } else {
                Random();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void checkTurn() {
        String whoTurn;
        if (!(turn % 2 == 0)) {
            whoTurn = Player1 + " FIRST GAMER";
        } else {
            whoTurn = Player2 + " SECOND GAMER";
        }
        lblTurn.setText("Turn: " + whoTurn);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void askUserForPlayerNames() {
        String temp;
        boolean tempIsValid = false;
        temp = getInput("Enter player 1 name:", Player1);
        if (temp == null) {/*Do Nothing*/} else if (temp.equals(""))
            showMessage("Invalid Name!");
        else if (temp.equals(Player2)) {
            option = askMessage("Player 1 name matches Player 2's\nDo you want to continue?", "Name Match", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
                tempIsValid = true;
        } else if (temp != null) {
            tempIsValid = true;
        }
        if (tempIsValid) {
            Player1 = temp;
            tempIsValid = false;
        }

        temp = getInput("Enter player 2 name:", Player2);
        if (temp == null) {/*Do Nothing*/} else if (temp.equals(""))
            showMessage("Invalid Name!");
        else if (temp.equals(Player1)) {
            option = askMessage("Player 2 name matches Player 1's\nDo you want to continue?", "Name Match", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
                tempIsValid = true;
        } else if (temp != null) {
            tempIsValid = true;
        }
        if (tempIsValid) {
            Player2 = temp;
            tempPlayer2 = temp;
            tempIsValid = false;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void setDefaultLayout() {
        pnlMain.setLayout(new GridLayout(2, 2, 2, 2));
        pnlTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void checkWinStatus() {
        lblStatus.setText(Player1 + ": " + player1Won + " | " + Player2 + ": " + player2Won);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public int askMessage(String msg, String tle, int op) {
        return JOptionPane.showConfirmDialog(null, msg, tle, op);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public String getInput(String msg, String setText) {
        return JOptionPane.showInputDialog(null, msg, setText);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void clearPanelSouth() {    //Removes all the possible panels
        //that pnlMain, pnlTop, pnlBottom
        //could have.
        pnlMain.remove(lblTitle);
        pnlMain.remove(pnlTop);
        pnlMain.remove(pnlBottom);
        pnlTop.remove(pnlNewGame);
        pnlTop.remove(txtMessage);
        pnlTop.remove(pnlPlayingField);
        pnlBottom.remove(lblTurn);
        pnlBottom.remove(pnlQuitNTryAgain);
    }
    /*
            -------------------------------------
            End of all non-Abstract METHODS.		|
            -------------------------------------
    */
    //-------------------ACTION PERFORMED METHOD (Button Click --> Action?)-------------------------//
    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource();
        for (int i = 1; i <= 16; i++) {
            if (source == btnEmpty[i] && turn < 18) {
                if (!(turn % 2 == 0))
                    btnEmpty[i].setText("1 GAMER");
                else
                    btnEmpty[i].setText("2 GAMER");
                btnEmpty[i].setEnabled(false);
                pnlPlayingField.requestFocus();
                turn++;
                checkWin();
                if (CPUGame && win == false)
                    AI();
            }
        }
        if (source == mnuNewGame || source == mnuInstruction || source == mnuAbout) {
            clearPanelSouth();
            setDefaultLayout();

            if (source == mnuNewGame) {                       //NewGame
                pnlTop.add(pnlNewGame);
            } else if (source == mnuInstruction || source == mnuAbout) {
                if (source == mnuInstruction) {                // Instructions
                    message = "Instructions:\n\n" +
                            "Your goal is to be the first player to get 4 FIRST GAMER's or SECOND GAMER's in a\n" +
                            "row. (horizontally, diagonally, or vertically)\n" +
                            Player1 + ": 1 GAMER\n" +
                            Player2 + ": 2 GAMER\n";
                } else {                                       //About
                    message = "About:\n\n" +
                            "Title: Four Chess\n" +
                            "Creator: Hong Kong\n";
                }
                txtMessage.setText(message);
                pnlTop.add(txtMessage);
            }
            pnlMain.add(pnlTop);
        } else if (source == btn1v1 ) {
            if (inGame) {
                option = askMessage("If you start a new game," +
                                "your current game will be lost..." + "\n" +
                                "Are you sure you want to continue?",
                        "Quit Game?", JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION)
                    inGame = false;
            }
            if (!inGame) {
                btnContinue.setEnabled(true);
                if (source == btn1v1) {                       // 1 vs 1 Game
                    Player2 = tempPlayer2;
                    player1Won = 0;
                    player2Won = 0;
                    lblMode.setText("1 vs 1");
                    newGame();
                } else {
                    player1Won = 0;
                    player2Won = 0;
                    newGame();
                }
            }
        } else if (source == btnContinue) {
            checkTurn();
            showGame();
        } else if (source == btnSetName) {
            askUserForPlayerNames();
        } else if (source == mnuExit) {
            option = askMessage("Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
                System.exit(0);
        } else if (source == btnTryAgain) {
            newGame();
            btnTryAgain.setEnabled(false);
        } else if (source == btnQuit) {
            quit();
        }
        pnlMain.setVisible(false);
        pnlMain.setVisible(true);
    }
}
