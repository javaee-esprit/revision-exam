package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="t_loan")

public class Loan implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LoanPK pk;
	
	private int duration;
	private Customer customer;
	private Book book;

	public Loan() {
	}   
	
	public Loan(Customer customer, Book book, Date startDate, int duration) {
		this.getPk().setCustomerId(customer.getId());
		this.getPk().setBookId(book.getId());
		this.getPk().setStartDate(startDate);
		this.duration = duration;
		this.customer = customer;
		this.book = book;
	}

	@EmbeddedId
	public LoanPK getPk() {
		if (pk == null) {
			pk = new LoanPK();
		}
		return pk;
	}
	public void setPk(LoanPK pk) {
		this.pk = pk;
	}
	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@ManyToOne
	@JoinColumn( name = "customer_fk" , insertable = false, updatable = false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@ManyToOne
	@JoinColumn( name = "book_fk" , insertable = false, updatable = false)
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
   
}
