public class MyLinkedListRunner {

	public static void main(String[] args) {

		test00constructorToString();

		test01singleParamAdd();

		test02contains();

		test03indexOf();

		test04get();

		test05set();

		test06removeBasic();
		
		test07twoParamAdd();
		
		test08removeAll();
	}

	public static void test00constructorToString() {
		MyLinkedList a = new MyLinkedList();
		MyLinkedList b = new MyLinkedList(20);

		if ((a.size() == 0 && a.toString().equals("[]")) && 
			(b.size() == 1 && b.toString().equals("[20]"))) {
			System.out.println("Pass: your constructor/toString seem to be working!");
		}
		else 
			System.out.println("Fail: there's a problem with your constructor/toString");
	}

	public static void test01singleParamAdd() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);
		
		if (list.size() == 3 && list.toString().equals("[4, 5, 6]"))
			System.out.println("Pass: Your single-parameter add method seems to be working!");
		else
			System.out.println("Fail: There's a problem with your single-parameter add method");
	}

	public static void test02contains() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);

		if (!list.contains(3) && list.contains(4) && list.contains(6)) {
			System.out.println("Pass: your contains method seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem with your contains method");
		}
	}

	public static void test03indexOf() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);
		
		if (list.indexOf(4) == 0 && list.indexOf(6) == 2 && list.indexOf(42) == -1) {
			System.out.println("Pass: your indexOf method seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem with your indexOf method");
		}
	}

	public static void test04get() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(10);
		
		if (list.get(0) == 4 && list.get(list.size() - 1) == 10) {
			System.out.println("Pass: your get method seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem with your get method");
		}
	}

	public static void test05set() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);
		
		list.set(42, 2);
		list.set(100, 1);
		
		//note that size shouldn't change when setting
		if (list.size() == 3 && list.get(2) == 42 && list.get(1) == 100) {
			System.out.println("Pass: your set method seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem with your set method");
		}
	}

	public static void test06removeBasic() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);
		list.add(100);
		
		int a = list.remove(0); //remove 4
		
		int b = list.remove(list.size() - 1); //remove 100
		
		if (a == 4 && b == 100 && list.size() == 2 && list.toString().equals("[5, 6]")) {
			System.out.println("Pass: your remove method seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem with your remove method");
		}
	}
	
	public static void test07twoParamAdd() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);
		
		list.add(42, 0);           //42, 4, 5, 6
		list.add(50, list.size()); //42, 4, 5, 6, 50
		list.add(60, 2);           //42, 4, 60, 5, 6, 50
		
		if (list.size() == 6 && list.get(0) == 42 &&
			list.toString().equals("[42, 4, 60, 5, 6, 50]")) {
			System.out.println("Pass: your two-parameter add method seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem with your two-parmater add method");
		}
	}
	
	public static void test08removeAll() {
		MyLinkedList list = new MyLinkedList();
		list.add(4); 
		list.add(5); 
		list.add(6);
		
		while (!list.isEmpty())
			list.remove(0);
		
		if (list.size() == 0 && list.toString().equals("[]")) {
			System.out.println("Pass: removing everything seems to be working!");
		}
		else {
			System.out.println("Fail: there's a problem when removing everything");
		}
	}
	
	/** test ability to add at index 0 when size = 0 */
	@SuppressWarnings("unused")
	private static void testTwoParamAddWithEmptyList() { //edge case not tested in original tests
		MyLinkedList list = new MyLinkedList();
		list.add(2, 0);
		System.out.println(list);
		System.out.println(list.size());
	}

	//@SuppressWarnings("unused")
	//private static void testIterator() //test (Advanced) make linked list iterable
	//{
	//	System.out.println("Iterator test");
	//	
	//	MyLinkedList list = new MyLinkedList(1, 2, 3, 4);
	//	
	//	for (int val : list) //won't work unless class is Iterable
	//		System.out.println(val);
	//}
}
