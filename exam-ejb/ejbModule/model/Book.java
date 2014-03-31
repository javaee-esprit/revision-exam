package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	private Category category;
	private List<Loan> loans;

	public Book() {
	}   
	
	public Book(int id, String name) {
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
	@ManyToOne
	@JoinColumn( name = "category_fk" )
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@OneToMany( mappedBy = "book" )
	public List<Loan> getLoans() {
		return loans;
	}
	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public String toString() {
		return "Book [id=" + id + ", name=" + name + "]";
	}
	
	
   
}
