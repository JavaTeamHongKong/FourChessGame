package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fourchess implements ActionListener {
    //Setting up ALL the variables
    JFrame window = new JFrame("Four Chess");

    JMenuBar mnuMain = new JMenuBar();
    JMenuItem mnuNewGame = new JMenuItem("New Game"),
            mnuInstruction = new JMenuItem("Instructions"),
            mnuExit = new JMenuItem("Exit"),
            mnuAbout = new JMenuItem("About");

    JButton btn1v1 = new JButton("Player vs Player"),
            btn1vCPU = new JButton("Player vs Computer"),
            btnQuit = new JButton("Quit"),
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

    final int winCombo[][] = new int[][]{
            {1, 2, 3, 4}, {1, 5, 9, 13},
            {5, 6, 7, 8}, {2, 6, 10, 14},
            {9, 10, 11, 12}, {3, 7, 11, 15},
            {13, 14, 15, 16}, {4, 8, 12, 16},
            {1, 6, 11, 16}, {4, 7, 10, 13}
/*Horizontal Wins*/	/*Vertical Wins*/ /*Diagonal Wins*/
    };
    final int X = 535;
    final int Y = 535;
    final int mainColorR = 50;
    final int mainColorG = 50;
    final int mainColorB = 50;
    int mainColorE = 50;
    final int btnColorR = 70;
    final int btnColorG = 70;
    final int btnColorB = 70;
    final int btnColorE = 70;
    Color clrBtnWonColor = new Color(190, 190, 190, 190);
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


    public Fourchess() {    //Setting game properties and layout and sytle...
        //Setting window properties:
        window.setSize(X, Y);
        window.setLocation(300, 300);
        //window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Setting Menu, Main, Top, Bottom Panel Layout/Backgrounds
        pnlMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));

        pnlNewGame.setBackground(new Color(mainColorR - 50, mainColorG - 50, mainColorB - 50, mainColorE = 50));
        pnlMenu.setBackground(new Color((mainColorR - 50), (mainColorG - 50), (mainColorB - 50), (mainColorE = 50)));
        pnlMain.setBackground(new Color(mainColorR, mainColorG, mainColorB, mainColorE));
        pnlTop.setBackground(new Color(mainColorR, mainColorG, mainColorB, mainColorE));
        pnlBottom.setBackground(new Color(mainColorR, mainColorG, mainColorB, mainColorE));

        //Setting up Panel QuitNTryAgain
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
        pnlNewGame.add(btn1v1);
        pnlNewGame.add(btn1vCPU);

        //Setting Button propertied
        btnTryAgain.setEnabled(false);


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
        btn1vCPU.addActionListener(this);
        btnQuit.addActionListener(this);
        btnTryAgain.addActionListener(this);

        //Setting up the playing field
        pnlPlayingField.setLayout(new GridLayout(5, 5, 5, 2));
        pnlPlayingField.setBackground(Color.black);
        for (int i = 1; i <= 16; i++) {
            btnEmpty[i] = new JButton();
            btnEmpty[i].setBackground(new Color(btnColorR, btnColorG, btnColorB, btnColorE));
            btnEmpty[i].addActionListener(this);
            pnlPlayingField.add(btnEmpty[i]);//	Playing Field is Compelte
        }

        //Adding everything needed to pnlMenu and pnlMain
        lblMode.setForeground(Color.white);
        pnlMenu.add(lblMode);
        pnlMenu.add(mnuMain);
        pnlMain.add(lblTitle);

        //Adding to window and Showing window
        window.add(pnlMenu, BorderLayout.NORTH);
        window.add(pnlMain, BorderLayout.CENTER);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Fourchess();//	Calling the class construtor.
        //		PROGRAM STARTS HERE!
    }


}
