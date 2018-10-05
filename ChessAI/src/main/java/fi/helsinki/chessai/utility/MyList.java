/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.chessai.utility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom List class with iterator.
 * @author janne
 */
public class MyList<E> implements Iterable<E> {
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object elements[];
 
    public MyList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }
 
    /**
     * Adds a new object to the list.
     * @param e 
     */
    public void add(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }
     
    /**
     * Returns an object at index i.
     * @param i
     * @return 
     */
    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (E) elements[i];
    }
     
    /**
     * Removes an object at index i from the list.
     * @param i
     * @return 
     */
    @SuppressWarnings("unchecked")
    public E remove(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        Object item = elements[i];
        int numElts = elements.length - ( i + 1 ) ;
        System.arraycopy( elements, i + 1, elements, i, numElts ) ;
        size--;
        return (E) item;
    }
    
    /**
     * Returns the size of the list.
     * @return 
     */
    public int size() {
        return size;
    }
     
    @Override
    public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         for(int i = 0; i < size ;i++) {
             sb.append(elements[i].toString());
             if(i<size-1){
                 sb.append(",");
             }
         }
         sb.append(']');
         return sb.toString();
    }
    
    /**
     * Doubles the size of the list if the capacity is about to be exceeded.
     */
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
    
    /**
     * Returns true if the list is empty.
     * @return 
     */
    public boolean isEmpty() {
        return this == null || this.size() == 0;
    }
    
    /**
     * Adds all items of another MyList to this list.
     * @param c
     * @return 
     */
    public boolean addAll(MyList<E> c) {
        MyList previous = this;
        for(E e : c) {
            this.add(e);
        }
        return previous != this;
        
    }
    
    //TODO Make faster
    /**
     * Checks if the list contains an item.
     * @param e
     * @return 
     */
    public boolean contains(E e) {
        if (e == null) {
            return false;
        }
        for (E ex : this) {
            if(e.equals(ex)) return true;
        }
        return false;
    }  
    
    /**
     * Returns the iterator.
     * @return 
     */
    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    /**
     * Iterator for the class.
     */
    class MyListIterator implements Iterator<E>{
        int currentPointer = 0;

        /**
         * Returns the true if the iterator contains more items.
         * @return 
         */
        @Override
        public boolean hasNext() {
           return (currentPointer < size); 
        }

        /**
         * Returns the next item on the list.
         * @return 
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            @SuppressWarnings("unchecked")
            E val = (E) elements[currentPointer];
            currentPointer++;

            return val;
        
        }
    }    
}
