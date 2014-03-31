package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="t_customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	private List<Loan> loans;

	public Customer() {
	}  
	
	public Customer(int id, String name) {
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
	@OneToMany( mappedBy = "customer" )
	public List<Loan> getLoans() {
		return loans;
	}
	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
	
	
   
}
