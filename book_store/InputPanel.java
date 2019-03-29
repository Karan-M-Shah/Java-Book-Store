/* Karan Shah
 * CNT 4717 - Spring 2019
 * Program 1: Event Driven Programming
 * Sunday January 27th 2019
 */

package book_store;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//The Input Panel contains the labels & text fields of the GUI
//This panel contains data that must be parsed or transformed 

public class InputPanel extends JPanel {
    
    //JLabels are initialized
    private static JLabel item_num;
    private static JLabel book_id;
    private static JLabel quantity;
    private static JLabel item_info;
    private static JLabel subtotal;
    
    //JTextFields are initialized
    private static JTextField t_item_num;
    private static JTextField t_book_id;
    private static JTextField t_quantity;
    private static JTextField t_item_info;
    private static JTextField t_subtotal;                   
    
    public InputPanel() {
        
       //Layout Manager of the InputPanel (flow)
       FlowLayout flow_input_layout = new FlowLayout(FlowLayout.TRAILING, 75, 10);
       setLayout(flow_input_layout);
       setBackground(Color.yellow);
       
       //Text Fields and Labels are created and added to the panel
       
       //Item number
       item_num = new JLabel("Enter number of items in this order:");
       add(item_num);
       t_item_num = new JTextField(35);
       add(t_item_num);
       
       //Book ID
       book_id = new JLabel("Enter Book ID for Item #1:");
       add(book_id);
       t_book_id = new JTextField(35);
       add(t_book_id);
       
       //Quantity
       quantity = new JLabel("Enter quantity for Item #1:");
       add(quantity);
       t_quantity = new JTextField(35);
       add(t_quantity);
       
       //Item Info
       item_info = new JLabel("Item # 1 Info:");
       add(item_info);
       t_item_info = new JTextField(35);
       t_item_info.setEditable(false);
       add(t_item_info);
       
       //Subtotal
       subtotal = new JLabel("Order subtotal for 0 item(s):");
       add(subtotal);
       t_subtotal = new JTextField(35);
       t_subtotal.setEditable(false);
       add(t_subtotal);
    }
    
    //Clear method which will be called by the "New Order" button
    //This method clears all of the JTextFields and restes buttons & labels
    public void clear(){
        t_subtotal.setText("");
        t_item_info.setText("");
        t_quantity.setText("");
        t_book_id.setText("");
        t_item_num.setText("");
        book_id.setText("Enter Book ID for Item #1:");
        quantity.setText("Enter quantity for Item #1:");
        item_info.setText("Item # 1 Info:");
        subtotal.setText("Order subtotal for 0 item(s):");
    }
    
    //Setters & Getters below to obtain text from the text fields or make changes to labels
    
    //Toggles the state of the number of items field
    public void toggleNum(String toggle){
        if(toggle.equals("on"))
            t_item_num.setEditable(true);
        else
            t_item_num.setEditable(false);
    }
    
    //Modifies the book ID label
    public void modifyID(int count){
        book_id.setText("Enter Book ID for Item #" + count + ":");
    }
    
    //Modifies the book ID label
    public void modifyQuantity(int count){
        quantity.setText("Enter quantity for Item #" + count + ":");
    }
    
    //Modifies the subtotal label
    public void modifySubtotal(int count){
        subtotal.setText("Order subtotal for " + count + " item(s):");
    }
    
    //Set the text of Book ID field
    public void setBookID(String text){
        t_book_id.setText(text);
    }
    
    //Set the text of quantity field
    public void setQuantity(String text){
        t_quantity.setText(text);
    }
    
    //Set the text of item information field
    public void setInfo(String text){
        t_item_info.setText(text);
    }
    
    //Set the text of subtotal field
    public void setSubtotal(double cost){
        String sum = String.format("%.2f", cost);
        if(!t_subtotal.getText().isEmpty())
        {
            sum = String.format("%.2f",cost + Double.parseDouble(t_subtotal.getText().substring(1)));
            t_subtotal.setText("$" + sum);
        }
        else
        {
            t_subtotal.setText("$" + sum);
        }
    }
    
    //Get text from item number field
    public String getNum(){
        return t_item_num.getText();
    }
    
    //Get text from item info field
    public String getInfo(){
        return t_item_info.getText();
    }
    
    //Get text from quantity field
    public String getQuantity(){
        return t_quantity.getText();
    }
    
    //Get text from ID field
    public String getBookID(){
        return t_book_id.getText();
    }
    
    //Get text from subtotal field
    public String getSubtotal(){
        return t_subtotal.getText().substring(1);
    }   
}
