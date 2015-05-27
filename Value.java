
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CherylRuo
 */
public class Value {
    
    public ArrayList<Integer> quantity = new ArrayList<Integer>();
    public ArrayList<Double> price = new ArrayList<Double>();

    public void addElement(int quantity, double price)
    {
        this.quantity.add(quantity);
        this.price.add(price);
    }
}
