package org.discobots.frc.ascent.framework.collections;

/**
 *
 * @author Nolan Shah
 */
public class List {
    private static final int expansionScale = 128;
    protected Object[] data;
    private int arrayIndex = -1;
    
    public List() {
        data = new Object[List.expansionScale];
    }
    
    public synchronized void addObject(Object newObject) {
        arrayIndex++;
        if (arrayIndex >= data.length - 1) {
            expandArray();
        }
        data[arrayIndex] = newObject;
    }
    public synchronized Object getObject(int location) {
        if (location > arrayIndex || location < 0) 
            throw new NullPointerException();
        return data[location];
    }
    public synchronized Object removeObject(int location) {
        Object o = getObject(location);
        Object[] tempPre = new Object[location + 1];
        Object[] tempPos = new Object[size() - location];
        System.arraycopy(data, 0, tempPre, 0, location);
        System.arraycopy(data, 0, tempPos, 0, size() - location - 1);
        data = new Object[data.length];
        System.arraycopy(tempPre, 0, data, 0, tempPre.length - 1);
        System.arraycopy(tempPos, 0, data, tempPre.length, tempPos.length - 1);
        arrayIndex--;
        return o;
    }
    public synchronized Object removeObject() {
        if (arrayIndex == -1) return null;
        Object removedObject = data[0];
        arrayIndex--;
        System.arraycopy(data, 1, data, 0, data.length - 1);
        return removedObject;
    }
    public synchronized int size() {
        return arrayIndex + 1;
    }
    private void expandArray() {
        Object[] temp = new Object[data.length];
        System.arraycopy(data, 0, temp, 0, data.length - 1);
        int newDataSize = data.length + expansionScale;
        data = new Object[newDataSize];
        System.arraycopy(temp, 0, data, 0, temp.length - 1);
    }
}
