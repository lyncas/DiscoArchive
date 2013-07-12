package org.discobots.frc.ascent.framework.collections;

/**
 *
 * @author Nolan Shah
 */
public class Queue {
    private static final int expansionScale = 256;
    
    private Object[] data = new Object[256];
    private int readObjects = 0;
    private int arrayIndex = -1; // Current filled position
    
    public synchronized void addObject(Object newObject) {
        arrayIndex++;
        if (arrayIndex >= data.length - 1) {
            expandArray();
        }
        data[arrayIndex] = newObject;
    }
    public synchronized Object removeObject() {
        if (arrayIndex == -1) return null;
        Object removedObject = data[0];
        arrayIndex--;
        System.arraycopy(data, 1, data, 0, data.length - 1);
        return removedObject;
    }
    private void expandArray() {
        Object[] temp = new Object[data.length];
        System.arraycopy(data, 0, temp, 0, data.length - 1);
        int newDataSize = data.length + expansionScale;
        data = new Object[newDataSize];
        System.arraycopy(temp, 0, data, 0, temp.length - 1);
    }
    public synchronized int size() {
        return arrayIndex + 1;
    }
}