class Hashtable<T> 
{
	int capacity = 10, size = 0, maximumLoadFactor = 75, incPercentage;
	Object table[] = new Object[capacity];

	public Hashtable() {}

	public Hashtable(int cap, int maxLDF, int incPct) {}

	public Hashtable<T> put(T obj) {
		int home = hash(obj.hashCode());

		while (table[home] != null) home = (home+1)%capacity;
		table[home] = object;
		++size;
		if ((float) size / capacity * 100 > maximumLoadFactor) rehash();
		return this;
	}

	public int hash(int hCode) {
		return hCode%capacity;
	}

	public T hash(T obj) {
		obj.hashCode%capacity;
	}

	protected void rehash() {
		Object tmpTable = table;

		capacity *= 1+incPercentage;
		table = new Object[capacity];
		size = 0;
		for (int i  =0; i < table.length; ++i) {
			tmpTable[i] != null ? put(table[i]) : ;
		}
	}

	protected T locate(T obj) {
		int home = hash(obj);
		return recLocate(obj, home)}

	protected T recLocate(T obj, int start) {
		if (table[start] == null ||
			 obj.compareTo(table[home]) == 0) return start;
		return recLocate((start+1)%capacity, start);
		//home = (home+1)%table.length;
	}

	public T remove(T obj) {
		int addr = locate(obj);
		T tmpObj = table[addr];

		if (obj == null) return null;
		table[addr] = null; --size;
		shift(addr, (addr+1)%capacity);

		return recRemove(obj, home);
	}

	protected void shift(int empty, int objLocation) {
		if (table[objLocation] == null) return;
		int home = hash(table[objLocation]);
		int emptyToHome = distance(home, empty);
		int currToHome = distance(home, objLocation);

		if (emptyToHome > currToHome) shift(empty, (objLocation+1)%capacity);
		else {
			table[empty] = table[objLocation];
			table[objLocation] = null;
			shift(objLocation, (objLocation+1)%capacity);
		}
	}

	protected int distance(int, from, int to) {
		return from > to ? from - to : from+(capacity-to);
	}

	protected T recRemove(T obj, int start) {
		table[home] == null ? return null : ;
		obj.compareTo(table[home]) == 0 ? return table[home] : ;
		return recRemove((home+1)%capacity);
	}
}
