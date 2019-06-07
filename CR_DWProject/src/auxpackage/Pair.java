package auxpackage;

public class Pair<T1, T2> {
	private T1 var1;
	private T2 var2;
	
	public Pair(T1 var1, T2 var2) {
		this.var1 = var1;
		this.var2 = var2;
	}
	
	public T1 getVar1() { return var1; }
	public T2 getVar2() { return var2; }
}
