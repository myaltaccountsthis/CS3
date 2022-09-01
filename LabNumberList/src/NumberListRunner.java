public class NumberListRunner 
{
	static NumberList list = new NumberList(); //the number list object the tests will work with
	
	public static void main(String[] args) 
	{
		/*
		 * NumberList objects should function in the same way as an ArrayList that stores Integers
		 * 
		 * in other words, NumberList list = new NumberList(); should work the
		 * same as ArrayList<Integrer> list = new ArrayList<>();
		 * 
		 * Comment out other test calls if you want to debug a particular method
		 */

		test00isEmptyAndSize();
		
		test01outOfBoundsTwoParamAdd();
		
		test02twoParamAdd();

		test03twoParamAddOutOfBounds();
		
		test04oneParamAdd();
		
		test05get();
		
		test06set();
		
		test07remove();
		
		test08removeAll();
	}
	
	//make sure the number list starts empty
	public static void test00isEmptyAndSize() {
		if (list.isEmpty() && list.size() == 0)
			System.out.println("Pass: Your isEmpty/size/constructor methods seems to be working!");
		else
			System.out.println("Fail: Your isEmpty and/or size methods may not be working");
	}
	
	//test cases that will go out of bounds
	public static void test01outOfBoundsTwoParamAdd() {
		try { 
			list.add(-1, 5); //add element 5 at index -1
		} 
		catch (IndexOutOfBoundsException e) { 
			System.out.println("Pass: Your out of bounds exception handling seems to be working");
			return;
		}
		System.out.println("Fail: Your add method did not catch the out of bounds exception");
	}
	
	//test two parameter add method, list should begin empty
	public static void test02twoParamAdd() {
		//adding at 'size' is allowed, per the lab doc
		list.add(0, 23); list.add(1, 17); list.add(0, 44);
		
		if (list.size() == 3 && list.toString().equals("[44, 23, 17]"))
			System.out.println("Pass: Your two-param add method seems to be working!");
		else
			System.out.println("Fail: There was a problem with your two-param add method");
	}
	
	//test the two-param add with a value that will be out of bounds
	public static void test03twoParamAddOutOfBounds() {
		try {
			list.add(4, 100);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Pass: Caught the two-parameter add out of bounds exception!");
			return;
		}
		System.out.println("Fail: There's a problem with your two-parameter add method's exception handling");
	}
	
	//test the add() method and toString() method
	public static void test04oneParamAdd() {
		list.add(6); list.add(8); //may require capacity doubling, depending on the initial size of the backing array
		
		if (list.size() == 5 && list.toString().equals("[44, 23, 17, 6, 8]"))
			System.out.println("Pass: Your single-parameter add method / toString method seems to be working!");
		else
			System.out.println("Fail: Either your single-parameter add method or your toString method is not working");
	}
	
	//test the get() method
	public static void test05get() {
		int a = list.get(0);               //44
		int b = list.get(1);               //23
		int c = list.get(list.size() - 1); //8
		
		if (a == 44 && b == 23 && c == 8)
			System.out.println("Pass: Your get method seems to be working!");
		else
			System.out.println("Fail: There's a problem with the get method");
	}
	
	//test the set() method
	public static void test06set() {
		list.set(0, 20);
		list.set(2, 40);
		
		//size shouldn't change with set
		if (list.size() == 5 && list.get(0) == 20 && list.get(2) == 40)
			System.out.println("Pass: Your set method seems to be working!");
		else
			System.out.println("Fail: There's a problem with your set method");
	}
	
	//test the remove() method
	public static void test07remove() {
		//list = [20, 23, 40, 6, 8]
		int a = list.remove(0); //remove 20 from the list, list is now [23, 40, 6, 8]
		int b = list.remove(list.size() - 2); //remove 6 from list, list is now [23, 40, 8]

		if (list.size() == 3 && a == 20 && b == 6 && list.toString().equals("[23, 40, 8]"))
			System.out.println("Pass: Your remove method seems to be working!");
		else
			System.out.println("Fail: There's a problem with your remove method");
	}
	
	public static void test08removeAll() {
		while (!list.isEmpty())
			list.remove(0);
		
		if (list.toString().equals("[]") && list.size() == 0 && list.isEmpty())
			System.out.println("Pass: All items removed from list successfully!");
		else
			System.out.println("Fail: There was a problem removing the remainder of the items from the list");
	}
}