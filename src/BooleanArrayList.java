import java.util.Arrays;

public class BooleanArrayList {
    private boolean[] list;
    private int size = 0;
    public BooleanArrayList(int initialSize){
        list = new boolean[initialSize];
    }
    public void add(boolean b){
        if (size == list.length)
            resize(size+size);
        list[size++] = b;
    }
    public boolean get(int i){
        return list[i];
    }

    private void resize(int newSize){
        list = Arrays.copyOf(list, newSize);
    }
    public int size(){
        return size;
    }
    public int contains(boolean b){
        int j = -1;
        while(++j < size){
            if (list[j] == b) return j;
        }
        return -1;
    }
    public boolean remove(){
        return list[--size];
    }
    public void set(int i){
        if (i<size) list[i] = true;
    }

    public void clear(int i){
        if (i<size) list[i] = false;
    }
}