
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CherylRuo
 */
public class PA1 {
    
    Map<String, Value> dictionary = new HashMap<String, Value>();
    PrintWriter writer;
    public PA1()
    {
        try {
            writer = new PrintWriter("pa1output.txt", "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PA1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PA1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void table1()
    {
        int count = 0;
        writer.println();
        writer.println("Table 1: Current Inventory");
        writer.println("Serial#   Item   Quantity   Cost");
        for (String key : dictionary.keySet()) 
        {
            if(!dictionary.get(key).quantity.isEmpty())
            {
                for(int i=0; i<dictionary.get(key).quantity.size(); i++)
                {
                    count++;
                    writer.println(count +"          "+ key + "      " + 
                        dictionary.get(key).quantity.get(i)+ "       " + dictionary.get(key).price.get(i));
                }
            }
        }
        writer.close();
    }
        
    public void table2() throws Exception
    {  
        int serialNo = 0;
        writer.println("Table 2: Cost Basis and Gains");
        writer.println("Serial#    Item    Quantity    Cost Basis($)    Gains($)");
        Scanner in = null;
        try {
            in = new Scanner(new File("src/pa1input.txt"));
            while(in.hasNextLine())
            {
                String str = in.nextLine();
                String[] splitStr = str.split("\\s+");
                String index = splitStr[0]; // index is "in" or "out"
                String name = splitStr[1].substring(0, splitStr[1].length()-1);
                int quantity = Integer.parseInt(splitStr[2]);
                double price = Double.parseDouble(splitStr[4].substring(1));
                double gains = 0;
                if(index.equals("in"))
                {
                    if(dictionary.containsKey(name))
                    {
                        dictionary.get(name).quantity.add(quantity);
                        dictionary.get(name).price.add(price);
                    }
                    else
                    {
                        Value value = new Value();
                        value.addElement(quantity, price);
                        dictionary.put(name, value);
                    }
                }
                
                if(index.equals("out"))
                {
                    serialNo++;
                    if(!dictionary.containsKey(name)) 
                    {
                        throw new Exception("There is inadequate " + name +" item to sell.");
                    }
                    double basis = 0;
                    int count = 0;
                    DecimalFormat df = new DecimalFormat("#.00");
                    while(quantity > count && !dictionary.get(name).quantity.isEmpty())
                    {
                        int top = dictionary.get(name).quantity.get(0);                    
                        count = count + top;
                        if(quantity - count >= 0)
                        {
                            basis += dictionary.get(name).price.get(0)* dictionary.get(name).quantity.get(0);
                            dictionary.get(name).price.remove(0);
                            dictionary.get(name).quantity.remove(0);
                        }
                        else
                        {
                            basis = (top-(count - quantity)) * dictionary.get(name).price.get(0);
                            dictionary.get(name).quantity.set(0, count - quantity);
                        }
                    }
                    if(count < quantity)
                    {
                        throw new Exception("There is inadequate " + name + " item to sell.");
                    }
                    gains = quantity*price - basis;
                    writer.println(serialNo + "           " + name + "        " + 
                            quantity + "           " + df.format(basis) + "           " + df.format(gains));
                } 
            }    
            in.close();
        }
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(PA1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    
    public static void main(String args[]) throws Exception
    {
        PA1 pa1 = new PA1();
        pa1.table2();
        pa1.table1();       
    }
}
