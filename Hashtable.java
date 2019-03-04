class Hashtable<T extends Comparable<T>>
{
	int capacity, size, maximumLoadFactor;
	double incPercentage;
	Object table[];

	public Hashtable() {
		this(10, 75, 20.0);
	}

	public Hashtable(int cap, int maxLDF, double incPct) {
		capacity = cap;
		maximumLoadFactor = maxLDF;
		incPercentage = incPct  / 100;
		size = 0;
		table = new Object[capacity];
	}

	public Hashtable<T> put(T obj) {
		int home = hash(obj.hashCode());

		while (table[home] != null) home = (home+1)%capacity;
		table[home] = obj;
		++size;
		if ((((float) size) / capacity * 100) > maximumLoadFactor) rehash();
		return this;
	}

	public int hash(int hCode) {
		return hCode%capacity;
	}

	public int hash(T obj) {
		return obj.hashCode()%capacity;
	}

	protected void rehash() {
		Object tmpTable[] = table;

		capacity = (int) (capacity * 1+incPercentage);
		table = new Object[capacity];
		size = 0;
		for (int i  =0; i < tmpTable.length; ++i) {
			if (tmpTable[i] != null)
				put((T) tmpTable[i]);
		}
	}

	protected int locate(T obj) {
		int home = hash(obj);
		return recLocate(obj, home);
	}

	protected int recLocate(T obj, int start) {
		if (table[start] == null ||
			 obj.compareTo((T) table[start]) == 0) return start;
		return recLocate(obj, (start+1)%capacity);
		//home = (home+1)%table.length;
	}

	public T remove(T obj) {
		int addr = locate(obj);
		T tmpObj = (T) table[addr];

		if (obj == null) return null;
		table[addr] = null; --size;
		shift(addr, (addr+1)%capacity);

		return recRemove(obj, addr);
	}

	protected T recRemove(T obj, int start) {
		if (table[start] == null)
			return null;
		return (obj.compareTo((T) table[start]) == 0) ? (T) table[start]
				 : recRemove(obj, (start+1)%capacity);
	}
	protected void shift(int empty, int objLocation) {
		if (table[objLocation] == null) return;
		int home = hash((T) table[objLocation]);
		int emptyToHome = distance(home, empty);
		int currToHome = distance(home, objLocation);

		if (emptyToHome > currToHome) shift(empty, (objLocation+1)%capacity);
		else {
			table[empty] = table[objLocation];
			table[objLocation] = null;
			shift(objLocation, (objLocation+1)%capacity);
		}
	}

	protected int distance(int from, int to) {
		return from > to ? from - to : from+(capacity-to);
	}


	public int search(T obj) {
		int home = hash(obj);
		return recSearch(obj, home, 0);
	}

	private int recSearch(T obj, int nextAddr, int n) {
		if (table[nextAddr] == null) return (n+1)*-1;
		if (obj.compareTo((T) table[nextAddr]) == 0) return n+1;
		return recSearch(obj, (nextAddr+1)%capacity, n+1);
	}
}
