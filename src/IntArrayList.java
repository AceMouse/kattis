import java.util.Arrays;

public class IntArrayList {
    private int[] list;
    private int size = 0;
    public IntArrayList(int initialSize){
        list = new int[initialSize];
    }

    public void clear(){
        size = 0;
    }

    public void sort(){
        Arrays.sort(list, 0, size);
    }

    public int add(int i){
        if (size == list.length)
            resize(size+size);
        list[size] = i;
        return size++;
    }

    public int add(int i, int index){
        if (Math.max(size,index) > list.length)
            resize(Math.max(size,index)<<1);
        list[index] = i;
        return index;
    }
    public int get(int i){
        return list[i];
    }

    private void resize(int newSize){
        int[] temp = list;
        list = new int[newSize];
        for (int i = 0; i < temp.length; i++) {
            list[i] = temp[i];
        }
    }
    public int size(){
        return size;
    }
    public int contains(int i){
        int j = -1;
        while(++j < size){
            if (list[j] == i) return j;
        }
        return -1;
    }
    public int remove(){
        return list[--size];
    }
    public boolean remove(int i){
        int index = -1;
        while(++index < size){
            if (list[index] == i){
                while(index < size-2){
                    list[index] = list[++index];
                }
                size--;
                return true;
            }
        }
        return false;
    }
    public int[] getArray(){
        return list;
    }
}