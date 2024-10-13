package hospital_assingma;

public class patient {
	int age ;
	String name;
	String categorie;
	String time;
	String comment;
	public patient(int age, String name, String categorie, String time,	String comment) {
		super();
		this.age = age;
		this.name = name;
		this.categorie = categorie;
		this.time = time;
		this.comment=comment;
	}
	public String getComment() {
		return comment;
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}

	public String getCategorie() {
		return categorie;
	}
	public String getTime() {
		return time;
	}
	public String toString() {
		return "patient [age=" + age + ", name=" + name + ", categorie=" + categorie + ", time=" + time +", comment"+ comment+ "]";
	}
	

}
