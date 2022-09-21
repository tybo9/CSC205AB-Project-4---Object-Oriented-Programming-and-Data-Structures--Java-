/*
 *       File: DLList.java
 *      Class: CSC205
 * Programmer: 
 *    Purpose: To write a class that is a representation of a doubly linked list.
 */
package dllist;

import java.util.*;

public class DLList<E>
{
    //--------data
    protected DLLNode<E> head;
    protected DLLNode<E> tail;
    
    //--------constructor
    public DLList()
    {
        head = tail = null;
    }

    //--------methods
    
    //addFirst - adds a node with theData to the front of the list
    public void addFirst(E theData)
    {
        DLLNode<E> temp = new DLLNode<E>(theData);   //creates a another node to hold data
        
        //case where the list was empty
        if (head == null)
            head = tail = temp;

        else
        {
            head.prev = temp;
            temp.next = head;
            head = temp;
        }
    }

    // addLast - adds a node with theData to the end of the list
    public void addLast(E theData)
    {
        DLLNode<E> temp = new DLLNode<E>(theData);  //creates a another node to hold data

        //case where the list was empty
        if (head == null)
            head = tail = temp;    //head and tail point at the new node

        else    //case where there was only 1 OR there are many element(s)
        {
            tail.next = temp;   //reset the last link
            temp.prev = tail;
            tail = temp;    // tail is reset to the new last node
        }
    }
    
    //toString - returns its representation as a String
    public String toString()
    {
        String retString = " ";
        {
            //traverse through the whole list (starting at head, until last link is null)
            DLLNode cursor = head;
            while (cursor != null)
            {
                if (cursor == head)
                        retString = retString + "" + cursor.data;
                else   //not first element so put in the comma
                        retString = retString + ", " + cursor.data;
                cursor = cursor.next;
            }
        }
        
        return "[" + retString + " ]";
    }

    //backwards - returns its representation as a String, only with the elements listed backwards
    public String backwards()
    {
        String retString = " ";
        
        {			
            //traverse through the whole list (starting at head, until last link is null)
            DLLNode cursor = tail;
            while(cursor != null)
            {
                if (cursor != tail)
                    retString += ", ";
                retString = retString + cursor.data;
                cursor = cursor.prev;
            }
        }
        return "[" + retString + " ]";
    }

    //recursive toString - this is the method that is called from "outside"
    //and just calls the recursive version
    public String anotherToString()
    {
        return recursiveToString(head);
    }

    //recursive version - calls itself
    private String recursiveToString(DLLNode<E> subList)
    {
        if (subList == null)
            return "";
        else
            return recursiveToString(subList.next) + "  " + subList.data;
    }

    //getFirst - returns the first element on the list (wiithout deleting it)
    public E getFirst()
    {
        if (head == null)   //empty
            throw new NoSuchElementException("cannot getFirst from empty list");

        return head.data;
    }

    //getLast - returns the last element on the list (without deleting it)
    public E getLast()
    {
        if (head == null)   //empty
            throw new NoSuchElementException("cannot getLast from empty list");

        return tail.data;
    }
    
    //contains - returns true if it contains the data that is received
    public boolean contains(E something)
    {
        DLLNode<E> cursor = head;
        
        while (cursor != null)
        {
            if (cursor.data.equals(something))
                return true;     // found it so return now!
            cursor = cursor.next;
        }

        return false;     // if we got through the whole loop and didn't return, its not there
    }
    
    //size - returns its size (number of nodes)
    public int size()
    {
        int count = 0;

        //traverse through the whole list (starting at head, until last link is null)
        DLLNode cursor = head;
        while(cursor != null)
        {
            count++;
            cursor = cursor.next;
        }

        return count;
    }

    //add(int index, E elt)  -  inserts a new node at the position specified by index
    public void add(int index, E elt)
    {
        //is index okay?
        if (index < 0 || index > size())
            throw new IllegalArgumentException("index " + index + " is out of bounds");

        if (index == 0)    //goes at the head
            addFirst(elt);  //call our own method
        else if (index == size())    //goes at the tail
            addLast(elt);   //call our own method
        else    //goes in the middle of nowhere
        {
            DLLNode<E> cursor = tail;   //set up a cursor
            int count = 0;
            
            while(count < index-1)      //traverse through the list
            {
                cursor = cursor.prev;
                count++;
            }

            //the cursor should have stopped at the node right after the insertion			
            //so just create the new node and then change the link
            DLLNode<E> theNewOne = new DLLNode<E>(elt);

            theNewOne.prev = cursor.prev;
            cursor.prev = theNewOne;
            cursor.prev.prev.next = theNewOne;
            theNewOne.next = cursor;
        }
    }

    //removeFirst - removes and returns the first element
    public E removeFirst()
    {
        //case1: list is empty
        if (head == null)
            throw new NoSuchElementException("cannot removeFirst from empty list");

        //case2: list only has 1 element
        else if (head == tail)
        {
            E whatToReturn = head.data;      //keep track of it
            head = tail = null;
            return whatToReturn;
        }
        
        //case3: list has many element
        else
        {
            E whatToReturn = head.data;     //keep track of it
            head = head.next;
            head.prev = null;
            return whatToReturn;
        }
    }


    //removeLast - removes the last node and returns its data
    public E removeLast()
    {
        //case1: list is empty
        if (tail == null)
            throw new NoSuchElementException("Cannot removeFirst from empty list");

        //case2: list only has 1 element
        else if (head == tail)
        {
            E whatToReturn = head.data;      //keeps track of it
            head = tail = null;
            return whatToReturn;
        }

        //case3: list has many elements
        else
        {
            E whatToReturn = tail.data;     //keeps track of it
            tail = tail.prev;
            tail.next = null;
            return whatToReturn;
        }
    }

    //remove - remove and return the first occurrance of an element
    public boolean remove(E doomedElt)
    {
        //if the list is empty, then it obviously can't be removed
        if (head == null)
            return false;

        //if there is one element
        else if (doomedElt.equals(head.data))
        {
            removeFirst();
            return true;
        }

        //if there is more than one element
        else
        {
            //find it if it exists
            DLLNode<E> cursor = head;
            while(cursor.next != null && !cursor.next.data.equals(doomedElt))
                cursor = cursor.next;

            //if its got all the way through the list (so it points at null now)
            //then it did not find doomedElt
            if (cursor.next == null)
                return false;

            //If the element is at the end then use removeLast.
            else if (cursor.next.data.equals(doomedElt) && cursor.next.next == null)
            {
                removeLast();
                return true;
            }

            //else, adjust links to insert into the middle of list
            else
            {
                cursor.next = cursor.next.next;
                cursor.next.prev = cursor;
                return true;
            }
        }
    }
    
    //isEmpty - returns true if it is empty
    public boolean isEmpty()
    {
        return head == null;
    }
}

//------------------------------------------------------------------------------------------------
//DLLNode will implement a node that will go on the list
//------------------------------------------------------------------------------------------------

class DLLNode<E>
{
    //--------data
    protected E data;
    protected DLLNode<E> next;
    protected DLLNode<E> prev;

    //--------constructor
    public DLLNode(E theData)
    {
        data = theData;
        next = null;
        prev = null;
    }

    //--------methods
    public String toString()
    {
        return data.toString();
    }
}