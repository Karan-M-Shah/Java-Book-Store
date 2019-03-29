/* Karan Shah
 * CNT 4717 - Spring 2019
 * Program 1: Event Driven Programming
 * Sunday January 27th 2019
 */

package book_store;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//The ButtonPanel is where the event handling occurs

//Impelents actionListener for event handling
public class ButtonPanel extends JPanel implements ActionListener {
    
    //Buttons are initialized
    private static JButton process_btn;
    private static JButton confirm_btn;
    private static JButton view_btn;
    private static JButton finish_btn;
    private static JButton new_btn;
    private static JButton exit_btn;
    
    //Input panel is passed through so that buttonPanel can access its public methods
    private final InputPanel inputPanel;
    
    //Scanner is initialized for parsing the inventory text file
    private static Scanner scan;
    private static String buffer = "";
    private static ArrayList<String> output = new ArrayList<String>();
    private static int count = 1;
    private static String cost;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public  ButtonPanel(final InputPanel inputPanel) {
       
        //Creating the scanner
        //File is located in the source directory
        try {
            scan = new Scanner(new File("src\\book_store\\inventory.txt"));
            scan.useDelimiter(",|\\n"); //Delimiter for parsing through the file
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
        
       this.inputPanel = inputPanel;
        
       //Layout Manager (flow)
       FlowLayout flow_input_layout = new FlowLayout();
       setLayout(flow_input_layout);
       setBackground(Color.blue);
       
       //Buttons are created, given an actionListener and then added to the panel
       
       //Process Button
       process_btn = new JButton("Process Item #1");
       process_btn.addActionListener(this);
       add(process_btn);
       
       //Confirm Button
       confirm_btn = new JButton("Confirm Item #1");
       confirm_btn.addActionListener(this);
       confirm_btn.setEnabled(false);
       add(confirm_btn);
       
       //View Button
       view_btn = new JButton("View Order");
       view_btn.addActionListener(this);
       view_btn.setEnabled(false);
       add(view_btn);
       
       //Finish Button
       finish_btn = new JButton("Finish Order");
       finish_btn.addActionListener(this);
       finish_btn.setEnabled(false);
       add(finish_btn);
       
       //New Button
       new_btn = new JButton("New Order");
       new_btn.addActionListener(this);
       add(new_btn);
       
       //Exit Button
       exit_btn = new JButton("Exit");
       exit_btn.addActionListener(this);
       add(exit_btn);
    }
    
    //Method for calculating the discount based on the quantity
    //This method is called during event handling when "Process" is selected
    public String calculateDiscount(String num){
        int value = Integer.parseInt(num);
        
        if(value >= 0 && value <= 4)
            return "0";
        else if(value > 4 && value <= 9)
            return "10";
        else if(value > 9 && value <= 14)
            return "15";
        else
            return "20";          
    }
    
    //Main event handling method will handle all of the buttons events
    @Override
    public void actionPerformed(ActionEvent event) {
        
        //If / else block to compare the source of the event in order to run
        //the correct action
        
        //Process Button Event
        if(event.getSource().equals(process_btn))
        {
            //Recreates the scanner each time this button is clicked
            //In order to reset its position to the start of the file
            try {
                scan = new Scanner(new File("src\\book_store\\inventory.txt"));
                scan.useDelimiter(",|\\n|\\r");
            } 
            catch (FileNotFoundException ex) {
            }
            //While loop to compare the book ID to inventory.txt file 
            while(scan.hasNextLine()) //Loop continues so long as there is a next line
            {
                if(this.inputPanel.getBookID().equals(scan.next())) //If a match for the ID is found within the file, proceed
                {
                    String var; //Buffer variable
                    //Very messy yet somehow functional code to write into the item info text field
                    this.inputPanel.setInfo(this.inputPanel.getBookID() + scan.next() + " $" + (var = scan.next()) + " " + this.inputPanel.getQuantity()
                    + " " + calculateDiscount(this.inputPanel.getQuantity()) + "% " + (cost = (String.format("%.2f",Double.parseDouble(this.inputPanel.getQuantity())*(Double.parseDouble(var) - 
                    (Double.parseDouble(var) * ((Double.parseDouble(calculateDiscount(this.inputPanel.getQuantity())))/100)))))));
                    
                    //Change status of buttons
                    process_btn.setEnabled(false);
                    confirm_btn.setEnabled(true);
                    
                    //Close the scanner and break the loop
                    scan.close();
                    return;
                } 
                else
                    scan.nextLine(); //While a match is not found, skip to the next line          
            } 
            //If no match is found, the book ID is incorrect, and an error message pops up
            JOptionPane.showMessageDialog(this, "Book ID " + this.inputPanel.getBookID() + " not in file", "Error", JOptionPane.WARNING_MESSAGE);
        }
        //Confirm Button Event
        else if(event.getSource().equals(confirm_btn))
        {
            //Update the buffer
            buffer+= count + ". " + this.inputPanel.getInfo() + "\n";
            output.add(this.inputPanel.getInfo());
            
            //Dialog box
            JOptionPane.showMessageDialog(this, "Item #" + count + " Accepted");
            
            //If the number of transactions is complete, run this sequence
            if(Integer.toString(count).equals(this.inputPanel.getNum()))
            {
                process_btn.setEnabled(false);
                confirm_btn.setEnabled(false);
                finish_btn.setEnabled(true);
                view_btn.setEnabled(true);
                this.inputPanel.setSubtotal(Double.parseDouble(cost+1));
                return;
            }
            
            //If additional transactions are still left, run this sequence
            count++;
            this.inputPanel.toggleNum("off");
            this.inputPanel.modifyID(count);
            this.inputPanel.modifyQuantity(count);
            this.inputPanel.modifySubtotal(count);
            modifyProcess(count);
            modifyConfirm(count);
            this.inputPanel.setBookID("");
            this.inputPanel.setQuantity("");
            this.inputPanel.setSubtotal(Double.parseDouble(cost));
            process_btn.setEnabled(true);
            confirm_btn.setEnabled(false);
            view_btn.setEnabled(true);
        }
        //View Button Event
        else if(event.getSource().equals(view_btn))
        {
            JOptionPane.showMessageDialog(this, buffer);
        }
        //Finish Button Event
        else if(event.getSource().equals(finish_btn))
        {       
            String temp;
            //Date timestamp
            String timeStamp = new SimpleDateFormat("DD/MM/YYYY, HH:mm:ss a z ").format(Calendar.getInstance().getTime());
            String timeStamp2 = new SimpleDateFormat("DDMMYYYYHHmm,").format(Calendar.getInstance().getTime());
            
            //Finish Order dialog box
            JOptionPane.showMessageDialog(this, "Date: " + timeStamp + "\n\nNumber of line items: " + 
            this.inputPanel.getNum() + "\n\nItem# / ID / Title / Price / Qty / Disc % / Subtotal\n\n" + 
            buffer + "\n\nOrder Subtotal: " + this.inputPanel.getSubtotal() + " " + 
            "\n\nTax Rate: 6%\n\n" + "Tax Amount: " + String.format("%.2f",Double.parseDouble(this.inputPanel.getSubtotal()) * 0.06) 
            + "\n\nOrder Total: " + String.format("%.2f", Double.parseDouble(this.inputPanel.getSubtotal()) + 
            (Double.parseDouble(this.inputPanel.getSubtotal()) * 0.06)) +
            "\n\nThanks for shopping at Ye Olde Book Shoppe!");
            
            //Write to the file
            try {
                FileWriter writer = new FileWriter("src\\book_store\\transaction.txt", true);
                for(int i = 0; i < output.size(); i++)
                {
                    temp = (i+1) + " " + timeStamp2 + " " + output.get(i) + " " + timeStamp + "\n";
                    output.set(i, temp);
                    writer.write(output.get(i));
                }
                writer.close();
            } catch (IOException ex) {
                System.out.println("File not found.");
            }
            
            //Clear and reset the GUI
            this.inputPanel.clear();
            view_btn.setEnabled(false);
            process_btn.setEnabled(true);
            finish_btn.setEnabled(false);
            this.inputPanel.toggleNum("on");
            count = 1;
            modifyProcess(count);
            modifyConfirm(count);
            buffer = "";
            output.clear();
        }
        //New Button Event
        else if(event.getSource().equals(new_btn))
        {
            //Calls the clear method from InputPanel
            this.inputPanel.clear();
            view_btn.setEnabled(false);
            process_btn.setEnabled(true);
            finish_btn.setEnabled(false);
            this.inputPanel.toggleNum("on");
            count = 1;
            modifyProcess(count);
            modifyConfirm(count);
            buffer = "";
        }
        //Exit Button Event
        else if(event.getSource().equals(exit_btn))
        {
           //Establishes the parent frame and then disposes it
           JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
           parent.dispose();
        }
    }
    
    //Modifies the process button text
    public void modifyProcess(int count){
        process_btn.setText("Process Item #" + count);
    }
    
    //Modifies the confirm button text
    public void modifyConfirm(int count){
        confirm_btn.setText("Confirm Item #" + count);
    }
}
