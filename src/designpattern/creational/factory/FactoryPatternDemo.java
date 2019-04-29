package designpattern.creational.factory;

public class FactoryPatternDemo {
	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();
		
		// get an Object if Circle and call its draw method.
		// Circle인 경우 객체를 가져와서 draw 메서드를 호출하기
		Shape shape1 = shapeFactory.getShape("CIRCLE");
		shape1.draw();
		
		Shape shape2 = shapeFactory.getShape("RECTANGLE");
		shape2.draw();
		
		Shape shape3 = shapeFactory.getShape("SQUARE");
		shape3.draw();
	}
}