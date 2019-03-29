/* Karan Shah
 * CNT 4717 - Spring 2019
 * Program 1: Event Driven Programming
 * Sunday January 27th 2019
 */

package book_store;

import java.awt.BorderLayout;
import javax.swing.JFrame;

//Purpose of the MainFrame is to construct the initial frame
//And to initialize the input & button pannel
//The mainframe creates the GUI, the handling is done in the button panel class

public class MainFrame extends JFrame {
    
    private final InputPanel input_panel; //Contains the labels & input fields
    private final ButtonPanel button_panel; //Contains the buttons
    
    public MainFrame() {
       //Initializes the main frame of the GUI 
       setTitle("Book Shop"); 
       setSize(800,275);
       setLocationRelativeTo(null);
       setResizable(false);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       //Initializes the input & button pannel
       input_panel = new InputPanel();              //See InputPanel class
       button_panel = new ButtonPanel(input_panel); //See ButtonPanel class
       
       //Main frame adds both panels to the frame
       add(input_panel, BorderLayout.CENTER);
       add(button_panel, BorderLayout.SOUTH);
       
       //Visibility is set
       setVisible(true);
    }
}
