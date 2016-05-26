/* Jonathan Hofler, Ani Desai, & Donna Ridgeway
 * Hangman Case Study
 * March 23, 2016
 * CIS218 Dr. Harris
 */

//libraries needed to run game
import java.awt.*; 
import java.applet.*; 
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import java.awt.geom.Line2D;

public class Hangman extends Applet{ // Beginning of class 

	String hiddenWord="", guessWord, guessList;  // Number of wrong letter guesses
	int missCount;                              // Counts the missed
	int maxMisses;                              // Counts the maximum times it is been missed
	boolean win, gameOver; 
	boolean[] knownChars;
	Image picture;

	Color bgColor = new Color(0xCCFFFF); //background color using HEX code CCFFFF

 public void init()
     {
	             
      setSize(1000,625);
      setBackground(bgColor);
          
      String firstName;
      firstName = JOptionPane.showInputDialog(" Welcome to Hangman!\n\n Enter your first name: "); // Welcome message for user
	
      String welcome;  
      welcome = "Welcome " + firstName +"!";
      JOptionPane.showMessageDialog(null, welcome);	// centers message
	
      Component frame = null;
      Object[] options = {"Yes",
      "No"};
      int n = JOptionPane.showOptionDialog(frame,
      "Do you want to view the game manual before you play?",  // prompt the user choose to see manual before playing
	  "Question",
	  JOptionPane.YES_NO_OPTION,
	  JOptionPane.QUESTION_MESSAGE,
	  null,     //do not use a custom Icon
	  options,  //the titles of buttons
	  options[0]); //default button title
			  
      // if JOption Yes/No Syntax
      if(n == JOptionPane.YES_OPTION)
      	{  
		// Displays user the game manual 
		JOptionPane.showMessageDialog(null, 
			"                                                                                         (Hangman Game Manual)\n"   
			+ "(1)  This is a 1 player game only.\n"
			+ "(2)  The player selects a letter of the alphabet.\n"
			+ "(3)  If the letter is contained in the word player can keep guessing a letter.\n"
			+ "(4)  Selecting the letter will trigger the letter to be revealed if it is contained in the word.\n"
			+ "(5)  If the letter is not contained in the word player can try again but with each wrong letter a portion of the hangman is added.\n"
			+ "(6)  The game continues until the word is guessed (all letters are revealed) – WINNER or all the parts of the hangman are displayed – LOSER\n"
				,"Game Manual",JOptionPane.INFORMATION_MESSAGE);
      	} // end if 
        
        newGame(); // new game method being called 
     } //end main

     public void newGame() // newGame method
     {
        hiddenWord = getHiddenWord();
        guessList = "";
        knownChars = new boolean[hiddenWord.length()];
        
        // make known characters false unless it happens to be a space to guess entire word
        for (int i = 0; i < hiddenWord.length(); i++)
        {     
             if (hiddenWord.charAt(i) == ' ')
                  knownChars[i] = true;
             else
                  knownChars[i] = false;
        } // end for statement
        win = false;
        missCount = 0;
        maxMisses = 6;
        gameOver = false;
        
     } // end method newGame
     
     public void paint(Graphics g)
     {
    	 Graphics2D g3 = (Graphics2D) g;
    	  
    	 g.setColor(new Color(0x00eeeeee)); // Applet background color
         g.fillRect(50, 60, 100, 30);
          
         g.setColor(new Color(0x00aaaaaa));
         g.drawRect(49, 59, 102, 32);
          
         g.setColor(Color.black); 
         g.setFont(new Font("Helvetica", Font.BOLD, 16)); 
          
         g.drawString("New Word", 64, 80); // New Word box font, size and bold 
         
         // text that tells player can guess entire word & to click on background to start game
         g.drawString("Press space bar to guess entire word", 100, 600);
         g.drawString("Click on the blue space before starting the game", 550, 600);
         g.setFont(new Font("Helvetica", Font.BOLD, 32)); 
                    
         // base
         g3.setStroke(new BasicStroke(10));
         g.drawLine(50,550,375,550);
          
         // vertical pole
         g3.setStroke(new BasicStroke(10));
         g.drawLine(150,550,150,150);
         
         // cross-bar
         g3.setStroke(new BasicStroke(10));
         g.drawLine(150,150,375,150);
        
         // rope
         g3.setStroke(new BasicStroke(10));
         g.drawLine(375,150,375,199);
         
         if (!gameOver)
          {
        	  
        	  for(int i = 0; i <=(hiddenWord.length()-1)*2; i++)
        	  {
        		  if (i %2 == 0)
        		  {
                    if (hiddenWord.charAt(i/2) != ' ')
                         g.drawLine(i * 15 + 225,100,(i+1) * 15 + 225,100);
                    if (knownChars[i/2] == true)
                         g.drawString(""+hiddenWord.charAt(i/2), i * 15 + 224, 95);
                   } // end if statement
               } // end for loop
          
          ((Graphics2D) g).draw(new Line2D.Float(50,550,375,550)); // makes the parts and the rope bold and thicker
          switch (missCount)
          {
          
          	case 6: g.drawLine(375,270,300,280);  // Hangman parts 
          	case 5: g.drawLine(375,270,450,280);  // Hangman parts 
          	case 4: g.drawLine(375,400,325,450);  // Hangman parts 
          	case 3: g.drawLine(375,400,425,450);  // Hangman parts 
          	case 2: g.drawLine(375,250,375,400);  // Hangman parts 
          	case 1: {g.drawOval(349,199,51,51); g.setColor(new Color(0x00ffcc99)); g.fillOval(350,200,50,50);} // Draws oval for hangman with color
          
          } // end switch statement
          
          g.setColor(Color.black);
          
          for(int i = 0; i  <guessList.length(); i++)
          {
               g.drawString(""+guessList.charAt(i),50 + i * 28,40);
          } // end for loop
                    
          if (win == true)
          {
               
               g.setColor(new Color(0x00009900)); // 0x00009900 is dark green
               g.drawString("You Win!",600,200);
               
               gameOver = true;
               
               for(int i = 0; i <= (hiddenWord.length()-1)*2; i++)
               {
                    if (i%2 == 0)
                    {
                         g.drawString(""+hiddenWord.charAt(i/2), i * 15 + 224, 95);
                    } // end if statement 
               } // end for loop
          } // end if statement 
          
          if (missCount == maxMisses)
          {
               g.setColor(Color.red);
               g.drawString("Hangman!",500,300);
               g.drawString("You Lose!",500,400);
               gameOver = true;
               for(int i=0; i<=(hiddenWord.length()-1)*2; i++)
               {
                    if (i%2 == 0)
                    {
                         g.drawString(""+hiddenWord.charAt(i/2), i * 15 + 224, 95);
                    } // end if statement 
               } // end for loop
          } // end if statement 
          } // end if statement 
          
     } // end method paint(graphics g)
     
     public String getHiddenWord() 
     {
        String[] wordList = {"pizza" , "watermelon","pomegranate","artichokes","basketball",  // Game words 
				             "gymnastics", "volleyball", "racing", "australia", "vatican", 
				             "zimbabwe", "philadelphia"};
          
        hiddenWord = wordList[(int)(Math.random()*12)]; // Math.random()*12 : 12 Random Words 
        hiddenWord = hiddenWord.toLowerCase();         // lower case letters 
          
        return hiddenWord;
          
     } // end method getHiddenWord
     
  
     
     public boolean validateGuess(String guess)
     {
          if (guess.equalsIgnoreCase(hiddenWord))
               return true;
          else
               return false;
          
     } // end method validateGuess
     
     public void guessTheWord() // We added guess a new word feature with a dialogue box that a allows user to guess the entire word.
     {
          guessWord = JOptionPane.showInputDialog(null, "Guess the entire word:"); // dialog box appears 
          
          if (validateGuess(guessWord))
          {
               win = true;
          } // end if statement
          
          else
          {
               missCount = maxMisses;
          } // end else
          
     }// end method guessTheWord
     
     @SuppressWarnings("deprecation")
     
	public boolean keyDown(Event e, int k)
     {
          // Cast the key pressed to a character
    	 
          boolean rightGuess = false;
          char keyChar = (char) k;
          
          if (Character.isLetter(keyChar))
          {
               for(int i = 0; i < guessList.length(); i++)
               {
                    if (keyChar == guessList.charAt(i))
                         return true;
               } // end for loop
               guessList = guessList+keyChar;
               
           
               
               for(int i = 0; i < hiddenWord.length(); i++)
               {
                    if (keyChar == hiddenWord.charAt(i))
                    {
                         rightGuess = true;
                         knownChars[i] = true;
                    }  // end if statement                
               } // end for loop
               
               // this loop makes "win = false" if there are any unknown characters otherwise "win = true"
               win = true;
               
               for(int i = 0; i < hiddenWord.length(); i++)
               {
                    if (knownChars[i] == false)
                    {
                         win = false;
                    } // end if statement
               } // end for loop
               
               if (rightGuess == false)
                    missCount++;
          } // end if statement
          
          else if (Character.isSpace(keyChar))
          {
               guessTheWord();
          } // end else if 

          repaint();
          return true;
     } // end method keyDown
     
     public boolean mouseDown(Event evt, int x, int y)
     {
          if ((x > 50 && x < 150) && (y > 60 && y < 90))
          {
               newGame();
               repaint();
               
          } // end if statement
          
          return true;
          
     } // end method mouseDown
     
} //end class Hangman