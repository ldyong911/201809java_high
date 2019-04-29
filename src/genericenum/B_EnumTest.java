package genericenum;

public class B_EnumTest {
	/*
	 * 열거형 => 상수값들을 선언하는 방법
	 *  
	 * static final int A = 0;
	 * static final int B = 1;
	 * static final int C = 2;
	 * static final int D = 3;
	 * 
	 * enum Data {A, B, C, D};
	 * 
	 * 열거형 선언하는 방법
	 * enum 열거형이름 {상수값1, 상수값2, ..., 상수값n};
	 * 
	 */
	
	// City 열거형 객체 선언(기본값을 이용하는 열거형)
	public enum City {서울, 부산, 대구, 광주, 대전};
	
	// 데이터값을 임의로 지정한 열거형 객체 선언
	// 데이터값을 정해 줄 경우에는 생성자를 만들어서 괄호속의 값이 변수에 저장되도록 해야 한다.
	public enum Season{
		봄("3월부터 5월까지"), 여름("6월부터 8월까지"), 가을("9월부터 11월까지"), 겨울("12월부터 2월까지");
		
		// 괄호속의 값이 저장될 변수 선언
		private String str;
		
		// 생성자 만들기(열거형의 생성자는 제어자가 묵시적으로 'private'이다.)
		Season(String data){ // => private Season(String data){ 와 같다.
			str = data;
		}
		
		// 값을 반환하는 메서드 작성
		public String getStr() {
			return str;
		}
	}
	
	public static void main(String[] args) {
		// true지만 false이어야 의미상 맞음
		System.out.println(Card.CLOVER == Card.TWO);
		
		// 값은 같지만 타입이 다른 경우...
		//System.out.println(EnumCard.Kind.CLOVER == EnumCard.Value.TWO);
		
		//위에 4줄은 enum을 쓰는 이유(형식상 true이지만 의미상으로 타입은 다르기때문에 false가 나와야 맞기때문에 enum으로 타입을 비교)
		
		/*
		 * 열거형에 사용되는 메서드
		 * 1. name() => 열거형 상수의 이름을 문자열로 반환한다.
		 * 2. ordinal() => 열거형 상수가 정의된 순서값을 반환한다.(기본적으로 0부터 시작)
		 * 3. valueOf("열거형상수이름"); => 지정된 열거형에서 '열거형상수이름'과 일치하는 열거형 상수를 반환함.
		 *  
		 */
		
		City mycity1; // 열거형 객체변수 선언
		City mycity2;
		
		// 열거형 객체변수에 값 저장하기
		mycity1 = City.valueOf("서울"); // City enum에서 '서울' 데이터를 가져온다.
		mycity2 = City.서울;
		
		System.out.println("mycity1: " + mycity1.name());
		System.out.println("mycity1의 ordinal: " + mycity1.ordinal());

		System.out.println("mycity2: " + mycity2.name());
		System.out.println("mycity2의 ordinal: " + mycity2.ordinal());
		System.out.println("==========================================");
		
		Season ss = Season.valueOf("여름"); // Season ss = Season.여름;
		System.out.println("name => " + ss.name());
		System.out.println("ordinal => " + ss.ordinal());
		System.out.println("get메서드 => " + ss.getStr());
		System.out.println("==========================================");
		
		// 열거형이름.values() => 데이터를 배열로 가져온다.
		Season[] enumArr = Season.values();
		for(int i=0; i<enumArr.length; i++) {
			System.out.println(enumArr[i].name() + " : " + enumArr[i].getStr());
		}
		System.out.println("==========================================");
		
		for(City city : City.values()) {
			System.out.println(city + " : " + city.ordinal());
		}
		System.out.println("==========================================");
		
		City city = City.대구;
		
		System.out.println(city == City.대전);
		System.out.println(city == City.대구);
		
		System.out.println("대구=> " + city.compareTo(City.대구));
		System.out.println("서울=> " + city.compareTo(City.서울));
		System.out.println("대전=> " + city.compareTo(City.대전));
	}
}

class Card{
	static final int CLOVER = 0;
	static final int HEART = 1;
	static final int DIAMOND = 2;
	static final int SPADE = 3;
	
	static final int TWO = 0;
	static final int THREE = 1;
	static final int FOUR = 2;
	
	final int kind = 0;
	final int num = 0;
}

class EnumCard{
	enum Kind {CLOVER, HEART, DIAMOND, SPADE};
	enum Value{TWO, THREE, FOUR};
	
	final Kind kind = Kind.CLOVER;
	final Value value = Value.TWO;
}