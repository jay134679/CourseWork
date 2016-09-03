package nyu.cs.pqs.ps5.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import nyu.cs.pqs.ps5.grid.GridColor;
import nyu.cs.pqs.ps5.listener.Connect4Listener;
import nyu.cs.pqs.ps5.model.Connect4Model;

/***
 * A view that has registered to Connect4Model. This view displays the interface
 * for user to click on different columns for coin to be added.
 * @author Anurag
 *
 */
public class Connect4View implements Connect4Listener, ActionListener {

  private Connect4Model model;
  private int rows = 6;
  private int cols = 7;
  private JFrame gameFrame = new JFrame();
  private JPanel gamePanel = new JPanel();
  
  private JButton[] columnButtons = new JButton[cols];
  private JPanel[][] panelsOnMainBoard = new JPanel[rows][cols];
  private Border grayLine = BorderFactory.createLineBorder(Color.GRAY);
  private JPanel updatePanel = new JPanel();
  private JTextField updateTextField = new JTextField("6", 3);
  
  public Connect4View(Connect4Model model) {
    this.model = model;
    model.addListener(this);
    
    gamePanel.setLayout(new GridLayout(0, cols));
    gameFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);
    
    
    // ALl the slots on the board.
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // the grids that get colored are JPanel implementations
        JPanel slot = new JPanel();
        gamePanel.add(slot);
        slot.setBorder(grayLine);
        panelsOnMainBoard[i][j] = slot;
      }
    }
    
    // ALl the buttons on the board.
    for (int i = 0; i < cols; i++) {
      JButton columnButton = new JButton(Integer.toString(i + 1));
      columnButton.addActionListener(this);
      gamePanel.add(columnButton);
      columnButtons[i] = columnButton;
    }
    
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setVisible(true);
    gameFrame.setSize(cols * 60, cols * 60);
    gameFrame.setLocationRelativeTo(null);
    
    
    updatePanel.setLayout(new GridLayout(0, 1));
    updatePanel.add(updateTextField);
    gameFrame.getContentPane().add(updatePanel, BorderLayout.SOUTH);
  }

  /***
   * Run when game started event is generated by the model.
   */
  @Override
  public void gameStart() {
    updateTextField.setText("Game started, play!");
  }

  /***
   * Run when game draw event is generated by the model.
   */
  @Override
  public void gameDraw() {
    updateTextField.setText("Game draw!");

  }

  /***
   * Run when game end event is generated by the model.
   */
  @Override
  public void gameEnd() {
    updateTextField.setText("Game has either not started or has ended!");
  }

  /***
   * Run when invalid event is generated by the model.
   */
  @Override
  public void invalidMove() {
    updateTextField.setText("Column full! Add again!");
  }

  /***
   * Run when player won event is generated by the model.
   */
  @Override
  public void playerWon(GridColor color) {
    if(color == GridColor.BLUE) {
      setAllPanelsSameColor(Color.BLUE);
    } else {
      setAllPanelsSameColor(Color.RED);
    }
    updateTextField.setText(color.toString() + " won!");
  }
  
  private void setAllPanelsSameColor(Color color) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // the grids that get colored are JPanel implementations
        panelsOnMainBoard[i][j].setBackground(color);
      }
    }
  }

  /***
   * Run when coin added event is generated by the model.
   */
  @Override
  public void coinAdded(int row, int column, GridColor color) {
    if(color == GridColor.BLUE) {
      panelsOnMainBoard[row][column].setBackground(Color.BLUE);
      updateTextField.setText("Red's turn!");
    } else {
      panelsOnMainBoard[row][column].setBackground(Color.RED);
      updateTextField.setText("Blue's turn!");
    }
  }
  
  /***
   * This is the actionPerformed event that is coupled to the buttons in UI.
   * BAsed on the button clicked corresponding call is made to the model.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    for(int i = 0; i < cols; i++) {
      if(e.getSource() == columnButtons[i]) {
        model.insertCoin(i);
      }
    }
  }
}