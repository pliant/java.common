package code.pliant.common.core;

public class ReflectionsVO{
	
	private String name = null;
	
	private int age = 10;
	
	/**
	 * @param name
	 * @param age
	 */
	public ReflectionsVO(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * 
	 * @return
	 */
	public int doubleAge(){
		return 2 * age;
	}
}