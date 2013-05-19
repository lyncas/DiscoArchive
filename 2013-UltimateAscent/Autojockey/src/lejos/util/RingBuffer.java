package lejos.util;

;

/**
 * This class implements a ring buffer, useful in stream-based communication.
 * 
 * @author Daniele Benedettelli
 */

public class RingBuffer {

  private byte[] buffer;

  private int count;
  
  private int start;
  
  private int size;
  
  
  /**
   * Instantiates a new ring buffer.
   *
   * @param n the buffer size
   */
  public RingBuffer(int n) {
    buffer = new byte[n];
    start = 0;
    size = n;
    count = 0;
  }
  
  /**
   * Checks if the buffer is full.
   *
   * @return true, if the buffer is full
   */
  boolean isFull() {
	  return count==size;
  }

  /**
   * Checks if the buffer is empty.
   *
   * @return true, if is empty
   */
  boolean isEmpty() {
	  return count==0;
  }
  
  /**
   * Add an array of bytes to the buffer. 
   *
   * @param elements the byte array
   * @param len the number of bytes
   */
  public void write(byte[] elements, int len) {
	  if (len>elements.length) len = elements.length;
	  for (int i = 0; i<len; i++) {
		  write(elements[i]);		  
	  }
  }
  
  /**
   * Write new element. App could check isFull() first.
   *
   * @param element the element
   * @return true, if successful
   * @returns true is overrun occurred
   */
  public boolean write(byte element) {
	  int end = (start + count) % size;
	  buffer[end] = element;
	  if (count==size) {
		  start = (start+1) % size; // full, overwrite
		  return true;
	  } else {
		  count++;
		  return false;
	  }
  }
  
  /**
   * Get the number of bytes written in the buffer.
   *
   * @return the number of bytes written in the buffer.
   */
  public int available() {
	  return count;
  }
  
  /**
   * Get the size of the buffer.
   *
   * @return the size of the buffer
   */
  public int size() {
	  return size;
  }
  
  /**
   * Read the oldest byte from the buffer and remove it. App must ensure !IsEmpty() first. 
   *
   * @return the byte
   */
  public byte read() {
	  byte t = 0;
	  t =  buffer[start];
	  start = (start+1) % size;
	  count--;
	  if (count<0) count = 0;
	  return t;
  }  
  
  /**
   * Read the oldest byte from the buffer without removing it. App must ensure !IsEmpty() first. 
   *
   * @return the byte
   */ 
  public byte peek() {
	  return buffer[start];
  }
   
  public String toString() {
    return "RB ( size=" + size + ", start=" + start + ", count=" + count +" )";
  }
}
