package id208133280_id207496068;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Set<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected T[] allObjects;
	protected int numOfObjects;

	public Set(Class<T> T, int size) {
		@SuppressWarnings({ "unchecked" })
		T[] allObjects = (T[]) Array.newInstance(T, size);
		this.allObjects = allObjects;
		numOfObjects=0;
	}

	public boolean add(T whatToAdd) {
		if(numOfObjects==allObjects.length) {
			extend();
		}
		if (!(contains(whatToAdd))) {
			allObjects[numOfObjects++] = whatToAdd;
			return true;
		}
		else {
			return false;
		}
	}

	public void remove(int index) {
		@SuppressWarnings({ "unchecked" })
		T[] removed = (T[]) Array.newInstance(allObjects[0].getClass(), 10);
		int i=0;
		for (; i < index; i++) {
			removed[i]=allObjects[i];
		}
		for (int j = index+1; j < numOfObjects; j++) {
			removed[i++]=allObjects[j];
		}
		numOfObjects--;
		this.allObjects=removed;
	}

	public T get(int index) {
		return allObjects[index];
	}

	public boolean contains(T ObjectToCheck) {
		int count = 0;
		for (int i = 0; i < numOfObjects; i++) {
			if (ObjectToCheck.equals(allObjects[i])) {
				count++;
			}
		}
		if (count != 0) {
			return true;
		} else {
			return false;
		}
	}

	public int size() {
		return numOfObjects;
	}

	public void set(int index,T newObject) {
		allObjects[index]=newObject;
	}
	public void extend() {
		@SuppressWarnings({ "unchecked" })
		T[] extended = (T[]) Array.newInstance(allObjects[0].getClass(), allObjects.length*2);
		extended=Arrays.copyOf(allObjects, numOfObjects);
		this.allObjects=extended;
	}
	
}
