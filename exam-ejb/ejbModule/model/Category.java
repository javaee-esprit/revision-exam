package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	private List<Book> books;

	public Category() {
	}
	
	
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	@Id    
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany( mappedBy = "category" , cascade = CascadeType.PERSIST)
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}


	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
	
   
}
