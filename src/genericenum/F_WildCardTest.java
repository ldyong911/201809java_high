package genericenum;

import java.util.Arrays;

// 와일드카드 타입 예제
public class F_WildCardTest {
	/*
	 * 와일드 카드
	 * <? extends T> => 와일드 카드의 상한 제한. T와 그 자손들만 가능
	 * <? super T> => 와일드 카드의 하한 제한. T와 그 조상들만 가능
	 * <?> => 제한 없음. 모든 타입이 가능. <? extends Object>와 동일
	 * 
	 * 와일드 카드가 필요한 이유는 의미상은 자손인데 제너릭타입이 다른 객체의 경우 매개변수가 될 수 없기 때문에
	 * 
	 */
	
	// 모든과정
	public static void registerCourse(Course<?> course) {
		System.out.println(course.getName() + " 수강생: " + Arrays.toString(course.getStudents()));
	}
	
	// 학생과정
	public static void registerCourseStudent(Course<? extends Student> course) { // Student와 Student 자손들만 가능
		System.out.println(course.getName() + " 수강생: " + Arrays.toString(course.getStudents()));
	}
	
	// 직장인과정
	public static void registerCourseWorker(Course<? super Worker> course) { // Worker와 Worker 조상들만 가능
		System.out.println(course.getName() + " 수강생: " + Arrays.toString(course.getStudents()));
	}
	
	public static void main(String[] args) {
		
		Course<Person> personCourse = new Course("일반인과정", 5);
		personCourse.add(new Person("일반인1"));
		personCourse.add(new Worker("직장인1"));
		personCourse.add(new Student("학생1"));
		personCourse.add(new HighStudent("고등학생1"));
		
		Course<Worker> workerCourse = new Course("직장인과정", 5);
		workerCourse.add(new Worker("직장인1"));
		
		Course<Student> studentCourse = new Course("학생과정", 5);
		studentCourse.add(new Student("학생1"));
		studentCourse.add(new HighStudent("고등학생1"));
		
		Course<HighStudent> highStudentCourse = new Course("고등학생과정", 5);
		highStudentCourse.add(new HighStudent("고등학생1"));
		
		registerCourse(personCourse);
		registerCourse(workerCourse);
		registerCourse(studentCourse);
		registerCourse(highStudentCourse);
		System.out.println("=====================================================================================");
		
		//registerCourseStudent(personCourse);
		//registerCourseStudent(workerCourse);
		registerCourseStudent(studentCourse);
		registerCourseStudent(highStudentCourse);
		System.out.println("=====================================================================================");
		
		registerCourseWorker(personCourse);
		registerCourseWorker(workerCourse);
		//registerCourseWorker(studentCourse);
		//registerCourseWorker(highStudentCourse);
	}
}

// 수강코스
class Course<T>{
	private String name; // 코스명
	private T[] students; // 수강생 배열
	
	public Course(String name, int capacity) {
		this.name = name;
		// 타입 파라미터로 배열을 생성시 오브젝트 배열을 생성후, 타입파라미터 배열로 캐스팅 처리해야함.
		students = (T[])(new Object[capacity]); 
	}
	
	public String getName() {
		return name;
	}
	public T[] getStudents() {
		return students;
	}
	
	public void add(T t) {
		for(int i=0; i<students.length; i++) {
			if(students[i] == null) {
				students[i] = t;
				break;
			}
		}
	}
}

class Person{
	String name; // 이름
	public Person(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "(수강생:" + name + ")";
	}
}

class Worker extends Person{
	public Worker(String name) {
		super(name);
	}
}

class Student extends Person{
	public Student(String name) {
		super(name);
	}
}

class HighStudent extends Student{
	public HighStudent(String name) {
		super(name);
	}
}