/*
 *       InfiniteInt.java
 *      CSC205
 * 
 *    Purpose: To write a class that acts as an infinite integer.
 */
package dllist;

import java.util.*;

public class InfiniteInt extends DLList <Integer> implements Comparable <InfiniteInt>
{
    //-----Data-----
    protected String token;
    protected StringTokenizer st;
    protected int int1, int2;

    //-----Constructors-----
    public InfiniteInt ()
    {
        super();
    }

    public InfiniteInt (String theNum)
    {
        st = new StringTokenizer(theNum, ",", false);    //creates new StringTokenizer, uses comma as delimiter

        while(st.hasMoreTokens())    //loops to get more tokens
        {
            token = st.nextToken();

            try
            {
                int1 = Integer.parseInt(token); //converts token to an integer
            }

            catch(Throwable E)
            {
                throw new IllegalArgumentException("Error: Illegal input!");   //throws an error if nothing is in the token
            }
            this.addLast(int1);
        }
    }
    
    //-----Methods-----
    //A static add method which will receive 2 InfiniteInts as arguments
    public static InfiniteInt add (InfiniteInt otherInt, InfiniteInt otherInt2)
    {
        DLLNode cursor1= otherInt.tail; //declares the cursors
        DLLNode cursor2 = otherInt2.tail;   //  at the tail
        InfiniteInt answer = new InfiniteInt(); //will return the answer
        InfiniteInt remainder = new InfiniteInt();
        int num1, num2, result, holder, length;
        int carry = 0;
        int otherCarry = 0;
        String digit1;

        if (otherInt.size() == otherInt2.size())
        {
            while(cursor1 != null)
            {
                num1 = (Integer)cursor1.data;   //typecasted so it can be added later
                num2 = (Integer)cursor2.data;
                result = carry + num1 + num2;
                carry = otherCarry; //resets to 0
                length = String.valueOf(result).length();   //converts result to a string and stores as an int
                digit1 = String.valueOf(result); //gets the first digit

                if (length > 3)
                {
                    carry = Integer.parseInt(digit1.substring(0, 1));   //first num of the result
                    result = result%1000;   //breaks it up by modulus
                    answer.addFirst(result);    //adds the result to the answer
                }                
                else
                    answer.addFirst(result);    //else add it to the linked list                
                cursor1 = cursor1.prev; //moves onto next node
                cursor2 = cursor2.prev;

                if(cursor1 == null && carry >= 1)
                    answer.add(0, carry);   //adds answer to the front of the list
            }
        }

        else if (otherInt.size() > otherInt2.size())
        {
            while(cursor2 != null)
            {
                num1 = (Integer)cursor1.data;   //typecasted so it can be added later
                num2 = (Integer)cursor2.data;
                result = carry + num1 + num2;
                carry = otherCarry;
                length = String.valueOf(result).length();
                digit1 = String.valueOf(result);

                if (length > 3)
                {
                    carry = Integer.parseInt(digit1.substring(0, 1));   //first num of the result
                    result = result%1000;   //breaks it up by modulus
                    answer.addFirst(result);    //adds the result to the answer
                }
                else
                    answer.addFirst(result);    //else add it to the linked list                
                cursor1 = cursor1.prev; //moves onto next node
                cursor2 = cursor2.prev;
            }
            //if it's nulll
            if(cursor2 == null)
            {
                holder = Math.abs(otherInt.size() - otherInt2.size());  //stores the difference in a holder
                
                for (int i = 0 ; i < holder ; i++)
                    remainder.addLast(otherInt.removeFirst());  //removes first node and adds to the remainder
                
                for (int j = 0 ; j <= remainder.size() ; j++)
                    answer.addFirst(remainder.removeLast() + carry);    //concatenate the remainder LL with answers LL and add carry
            }
        }
        else if (otherInt2.size() > otherInt.size())
        {
            while(cursor1 != null)
            {                
                num1 = (Integer)cursor1.data;   //typecasted so it can be added later
                num2 = (Integer)cursor2.data;
                result = carry + num1 + num2;
                carry = otherCarry; //resets to 0
                length = String.valueOf(result).length();   //converts result to a string and stores as an int
                digit1 = String.valueOf(result); //gets the first digit

                if (length > 3)
                {
                    carry = Integer.parseInt(digit1.substring(0, 1));   //first num of the result
                    result = result%1000;   //breaks it up by modulus
                    answer.addFirst(result);    //adds the result to the answer
                }
                else
                    answer.addFirst(result);    //else add it to the linked list                
                cursor1 = cursor1.prev; //moves onto next node
                cursor2 = cursor2.prev;
            }

            //When the cursor of newInt hits null.
            if(cursor1 == null)
            {
                holder = Math.abs(otherInt2.size() - otherInt.size());  //stores the difference in a holder
                
                for (int i = 0 ; i < holder ; i++)
                    remainder.addLast(otherInt2.removeFirst()); //removes first node and adds to the remainder
                
                for (int j = 0 ; j <= remainder.size() ; j++)
                    answer.addFirst(remainder.removeLast() + carry);    //concatenate the remainder LL with answers LL and add carry
            }
        }
        return answer;  //returns the answer
    }

    //equals method that receives another Object and returns true if it is equal to this InfiniteInt. 
    public boolean equals(Object obj)
    {
        int num1,num2 = 0;

        if (obj == null)
                return false;
        if (this.getClass() != obj.getClass())
                return false;        
        
        InfiniteInt another = (InfiniteInt)obj;

        if(this.size() == another.size())  //if ints are the same size
        {
            DLLNode cursor1 = this.tail;    //declares cursors
            DLLNode cursor2 = another.tail;
            num1 = (Integer)cursor1.data;   //typecasts as int
            num2 = (Integer)cursor2.data;

            if(num1 > num2)
                return true;
            else if (num1 < num2)
                return false;
            else        //compares the data
            {
                while(cursor1.prev != null && cursor2.prev != null)
                {
                    num1 = (Integer)cursor1.data;   //typecasts as integer
                    num2 = (Integer)cursor2.data;

                    if(num1 > num2)
                        return true;
                    else if (num1 < num2)
                        return false;
                    else
                    {
                        cursor1 = cursor1.prev;
                        cursor2 = cursor2.prev;
                    }
                }
            }
        }

        else if (this.size() > another.size())
            return true;
        return true;
    }

    //toString() - returns its representation as a String
    public String toString()
    {
        if(this.head == null && this.tail == null)
            throw new IllegalStateException ("Error: The linked list is empty");    //throws error if empty

        DLLNode cursor = head;
        String retString = "";  //initializes string variable

        while(cursor != null)
        {
            if(cursor == head)
                retString = retString + cursor.data;   //gets data if it is the head
            if (cursor != head)
            {
                retString += ",";
                retString = retString + String.format("%03d", cursor.data);
            }
            
            cursor = cursor.next;
        }

        return retString;
    }

    //compareTo(InfiniteInt o) that will implement the Comparable interface
    public int compareTo(InfiniteInt o)
    {
        if (this.size() > o.size())
            return  1;
        else if (this.size() < o.size())
            return -1;
        else
        {
            DLLNode cursor1 = this.tail;    //Declares the cursor of both nodes.
            DLLNode cursor2 = o.tail;

            while(cursor1 != null)
            {                
                if((Integer)cursor1.data > (Integer)cursor2.data)
                    return 1;   //returns 1 if it is bigger
                else if ((Integer)cursor1.data < (Integer)cursor2.data)
                    return -1;  //returns -1
                else
                {
                    cursor1 = cursor1.prev;
                    cursor2 = cursor2.prev;
                }
            }
        }
        
        return 0;
    }
}