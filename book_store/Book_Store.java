/* Karan Shah
 * CNT 4717 - Spring 2019
 * Program 1: Event Driven Programming
 * Sunday January 27th 2019
 */
package book_store;

import java.awt.EventQueue;

public class Book_Store {
    
    //Main method - Calls the constructor which will initialize the GUI
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(); //GUI Constructor
        });    
    }
}
