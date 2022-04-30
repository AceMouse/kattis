public class LongArrayList {
    private long[] list;
    private int size = 0;
    public LongArrayList(int initialSize){
        list = new long[initialSize];
    }
    public void add(long i){
        if (size == list.length)
            resize(size+size);
        list[size++] = i;
    }
    public long get(int i){
        return list[i];
    }
    /*private void resize(int newSize){
        long[] temp = list;
        list = new long[newSize];
        System.arraycopy(temp, 0, list, 0, temp.length);
    }*/
    private void resize(int newSize){
        long[] temp = list;
        list = new long[newSize];
        for (int i = 0; i < temp.length; i++) {
            list[i] = temp[i];
        }
    }
    public int size(){
        return size;
    }
    public boolean contains(long i){
        int j = -1;
        while(++j < size){
            if (list[j] == i) return true;
        }
        return false;
    }
}