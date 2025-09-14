class DynamicArray {

    private int arr[];
    private int capacity;
    private int length;

    public DynamicArray(int capacity) {

        this.capacity=capacity;
        this.length=0;
        this.arr=new int[this.capacity];

    }

    public int get(int i) {
        return arr[i];
    }

    public void set(int i, int n) {
        arr[i]=n;
    }

    public void pushback(int n) {
        if(length==capacity)
        {
            resize();
        }
        arr[length]=n;
        length++;
    }

    public int popback() {
        if(length>0)
        {
            length--;
        }
        return arr[length];

    }

    private void resize() {
        capacity*=2;
        int [] newArray=new int[capacity];
        for(int i=0;i<length;i++)
        {
            newArray[i]=arr[i];
        }
        arr=newArray;

    }

    public int getSize() {
        return length;
    }

    public int getCapacity() {
        return capacity;

    }
}


