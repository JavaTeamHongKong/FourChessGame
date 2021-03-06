package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    JButton btnEmpty[] = new JButton[sizeOfMatrix*sizeOfMatrix];

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

    int X = 500;                      //size of window vertical
    int Y = 500;                      //size of window horizontal
    int mainColorR = 50;             // size of lettering - FIRST GAMER
    int mainColorG = 50;
    int mainColorB = 50;
    int mainColorE = 50;
    static int sizeOfMatrix = 7;

    int turn = 1,
            player1Won = 0, player2Won = 0,
            wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1, wonNumber4 = 1,
            option;
    boolean inGame = false,
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
        pnlPlayingField.setLayout(new GridLayout(sizeOfMatrix, sizeOfMatrix, 3, 3));
        pnlPlayingField.setBackground(Color.RED);
        for (int i = 0; i < sizeOfMatrix * sizeOfMatrix; i++) {
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
        turn();
        checkWinStatus();
    }
    //-----------------------------------------------------------------------------------------------------------------------------------
    public void newGame() {    //	Sets all the game required variables to default
        //	and then shows the playing field.
        //	(Basically: Starts a new 1v1 Game)

        for (int i = 0; i < sizeOfMatrix*sizeOfMatrix; i++) {
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

//    public boolean compare_cells(int r1, int c1, int r2, int c2) {
//        return btnEmpty[winGame[r1][c1]].getText().equals(btnEmpty[winGame[r2][c2]].getText());
//    }
    private int checkHorizontal() {
        for (int i = 0; i < sizeOfMatrix; i++) {
            int counter1 = 0;
            int counter2 = 0;
            for (int j = 0; j < sizeOfMatrix; j++) {
                int index = i * sizeOfMatrix + j;
                if (btnEmpty[index].getText().equals("1 GAMER")) {
                    counter1++;
                    counter2 = 0;
                } else if (btnEmpty[index].getText().equals("2 GAMER")) {
                    counter2++;
                    counter1 = 0;
                }else if(btnEmpty[index].getText().equals("")) {
                    counter1 = 0;
                    counter2 = 0;
                }

                if (counter1 == 4) {
                    return 1;
                }
                if (counter2 == 4) {
                    return 2;
                }
            }
        }
        return 0;
    }
    private int checkVertical() {
        for (int i = 0; i < sizeOfMatrix; i++) {
            int counter1 = 0;
            int counter2 = 0;
            for (int j = 0; j < sizeOfMatrix; j++) {
                int index = j * sizeOfMatrix + i;
                if (btnEmpty[index].getText().equals("1 GAMER")) {
                    counter1++;
                    counter2 = 0;
                } else if (btnEmpty[index].getText().equals("2 GAMER")) {
                    counter2++;
                    counter1 = 0;
                }else if(btnEmpty[index].getText().equals("")) {
                    counter1 = 0;
                    counter2 = 0;
                }

                if (counter1 == 4) {
                    return 1;
                }
                if (counter2 == 4) {
                    return 2;
                }
            }
        }
        return 0;
    }
    public void checkWin() {
        int result = checkHorizontal();
        if (result == 1){
            message = Player1 + " Has won! Game over!";
            showMessage(message);
            newGame();
        }
        if (result == 2){
            message = Player2 + " Has won! Game over!";
            showMessage(message);
            newGame();
        }
        result = checkVertical();
        if (result == 1){
            message = Player1 + " Has won! Game over!";
            showMessage(message);
            newGame();
        }
        if (result == 2){
            message = Player2 + " Has won! Game over!";
            showMessage(message);
            newGame();
        }

    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void AI() {
        int computerButton;
        if (turn <= 16) {
            turn++;
            computerButton = Buttons.doMove(
                    btnEmpty[1], btnEmpty[2], btnEmpty[3], btnEmpty[4],
                    btnEmpty[5], btnEmpty[6], btnEmpty[7], btnEmpty[8],
                    btnEmpty[9], btnEmpty[10], btnEmpty[11], btnEmpty[12],
                    btnEmpty[13], btnEmpty[14], btnEmpty[15], btnEmpty[16]);
            if (computerButton == 0)
                Random();
            else {
                btnEmpty[computerButton].setText("2 GAMER");
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
            if (Buttons.doRandomMove(btnEmpty[random])) {
                btnEmpty[random].setText("2 GAMER");
                btnEmpty[random].setEnabled(false);
            } else {
                Random();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    public void turn() {
        String whoTurn;
        if (!(turn % 2 == 0)) {
            whoTurn = Player1 + " 1 GAMER";
        } else {
            whoTurn = Player2 + " 2 GAMER";
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
        for (int i = 0; i < sizeOfMatrix*sizeOfMatrix; i++) {
            if (source == btnEmpty[i] && turn < 18) {
                if (!(turn % 2 == 0))
                    btnEmpty[i].setText("1 GAMER");
                else
                    btnEmpty[i].setText("2 GAMER");

                btnEmpty[i].setEnabled(false);
                pnlPlayingField.requestFocus();
                turn++;
                checkWin();
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
                            "Your goal is to be the first player to get 4 1 GAMER's or 2 GAMER's in a\n" +
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
            turn();
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
