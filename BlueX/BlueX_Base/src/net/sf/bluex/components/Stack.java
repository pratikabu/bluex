/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import java.util.ArrayList;

/**
 *
 * @author Blue
 */
public class Stack<E> {
    /**
     * @private members
     */
    private ArrayList<E> arrItems;
    
    private int index=-1;
    
    /**
     * public constructor
     */
    public Stack(){
        //init ArrayList object
        arrItems=new ArrayList<E>();
    }
    
    /**
     * @public methods
     */
    public void push(E obj){
        //do remove elements till the index so as to maintain the travering easier
        if((arrItems.size()-1)!=index){
            //remove all items above index
            for(int i=arrItems.size()-1;i>index;i--){
                arrItems.remove(i);
            }
            arrItems.trimToSize();
        }
        
////        System.out.println("ArrayList size: "+arrItems.size()+", Index: "+index);
        
        //add item to arraylist
        arrItems.add(obj);
        //set index
        index=arrItems.size()-1;
    }
    
    public E pop(){
        if(!arrItems.isEmpty()){
            //do return the object from last
            return arrItems.remove(arrItems.size()-1);
        }
        else
            return null;
    }
    
    /**
     * returns object from the last if you want the top element then pass 1 in it.
     * @param i
     * @return Throws ArrayIndexOutOfBoundException if index is out of bound
     */
    public E elementAt(int i){
        if(i<0 || i>=arrItems.size())
            throw new ArrayIndexOutOfBoundsException("The index is out of bound.");
        
        return arrItems.get(i);
    }
    
    /**
     * this method will return the parent file object it is used by Back button
     */
    public E getBackItem(){
        if(ensureBack()){
            //do return the object from last
            return arrItems.get(--index);
        }
        else
            return null;
    }
    
    /**
     * this method will return the child file object it is used by forward button
     */
    public E getForwardItem(){
        if(ensureForward()){
            //do return the object from last
            return arrItems.get(++index);
        }
        else
            return null;
    }
    
    /**
     * remove method it will remove the selected item from the stack
     * and returns true if successfully deleted else false
     */
    public boolean removeItem(int pos){
        boolean removed=false;
        
        Object obj=arrItems.remove(pos);
        
        if(obj!=null){
            removed=true;
            //update index variable
            index=arrItems.size()-1;
        }
        return removed;
    }
    
    /**
     * remove all method
     */
    public void removeAll(){
        int count=arrItems.size();
        for(int i=0;i<count;i++){
            arrItems.remove(0);
        }
        
        //set index
        index=arrItems.size()-1;
    }
   
    /**
     * These methods will ensure the back and forward moves so that forward and backward buttons can be disabled or enabled
     */
    public boolean ensureBack(){
        if(arrItems.isEmpty() || index==0)
            return false;
        else
            return true;
    }
    
    public boolean ensureForward(){
        int limit=arrItems.size()-1;
        if(arrItems.isEmpty() || index==limit)
            return false;
        else
            return true;
    }
    
    /**
     * returns the number of items in array list
     */
    public int size(){
        return arrItems.size();
    }

    /**
     * this method will give the position of the last element according to the traversing system of Back and Forward
     * @return
     */
    public int getBackPosition(){
        return index;
    }

    /**
     * this method returns the copy of the stack with a new reference
     * @return
     */
    public Stack<E> getAnotherCopy(){
        Stack stNew=new Stack();

        for(Object obj : arrItems)
            stNew.push(obj);

        return stNew;
    }

    /**
     * this method returns the copy of the stack with a new reference but in reverse order
     * @return
     */
    public Stack<E> getReverseCopy(){
        Stack stNew=new Stack();

        int i=arrItems.size()-1;
        for(;i>=0;i--)
            stNew.push(arrItems.get(i));

        return stNew;
    }
}
