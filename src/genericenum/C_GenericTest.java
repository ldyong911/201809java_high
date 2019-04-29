package genericenum;

public class C_GenericTest {
	/*
	 * 제너릭 클래스 만드는 방법
	 * 형식)
	 *     class 클래스명<제너릭타입글자>{
	 *         제너릭타입글자 변수명; // 변수 선언에 제너릭을 사용할 경우
	 *         ...
	 *         제너릭타입글자 메서드명(){	// 반환값이 있는 메서드에서 사용
	 *             ...
	 *       	   return 값;
	 *         }
	 *         ...
	 *     }
	 *       
	 * -- 제너릭 타입글자 --
	 * T => Type
	 * K => Key
	 * V => Value
	 * E => Element(자료구에 들어가는 것들을 나타날 때 사용)
	 * 
	 * class Box<T>{} 에서
	 * 1. Box<T> => 지네릭 클래스. 'T의 Box' 또는 'T Box'라고 읽는다.
	 * 2. T => 타입 변수 또는 타입 매개변수(T는 타입 문자)
	 * 3. Box => 원시 타입(raw type)
	 * 
	 * 제너릭스의 장점 => 타입 안정성 제공, 타입체크와 형변환을 생략할 수 있으므로 코드가 간결해짐
	 * 
	 */
	
	public static void main(String[] args) {
		NonGenericClass ng1 = new NonGenericClass();
		ng1.setVal("가나다라마");
		
		NonGenericClass ng2 = new NonGenericClass();
		ng2.setVal(100);
		
		String rtnNg1 = (String)ng1.getVal();
		System.out.println("문자열 반환값 rtnNg1 => " + rtnNg1);
		
		Integer irtnNg2 = (Integer)ng2.getVal();
		System.out.println("정수 반환값 irtnNg2 => " + irtnNg2);

		System.out.println("==============================");
		
		MyGeneric<String> mg1 = new MyGeneric(); //MyGeneric<String> mg1 = new MyGeneric<Apple>(); 와 같은 문장이다(JDK 1.7부터 생략가능)
		MyGeneric<Integer> mg2 = new MyGeneric();
		
		mg1.setVal("우리나라");
		mg2.setVal(500);
		
		rtnNg1 = mg1.getVal();
		irtnNg2 = mg2.getVal();
		
		System.out.println("제너릭 문자열 반환값: " + rtnNg1);
		System.out.println("제너릭 문자열 반환값: " + irtnNg2);
		
	}
}

class NonGenericClass{
	private Object val;

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}
}

class MyGeneric<T>{
	private T val;
	
	public T getVal() {
		return val;
	}
	
	public void setVal(T val) {
		this.val = val;
	}
}