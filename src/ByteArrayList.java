public class ByteArrayList {
    private byte[] list;
    private int size = 0;
    public ByteArrayList(int initialSize){
        list = new byte[initialSize];
    }
    public int add(byte i){
        if (size == list.length)
            resize(size+size);
        list[size] = i;
        return size++;
    }
    public byte get(int i){
        return list[i];
    }
    /*private void resize(int newSize){
        list = Arrays.copyOf(list, newSize);
    }*/
    private void resize(int newSize){
        byte[] temp = list;
        list = new byte[newSize];
        System.arraycopy(temp, 0,list,0,temp.length);
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
    public String toString(){
        return new String(list, 0, size);
    }
}