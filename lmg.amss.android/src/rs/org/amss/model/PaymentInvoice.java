package rs.org.amss.model;

import java.io.Serializable;

public class PaymentInvoice implements Serializable {
	private static final long serialVersionUID = -4759556332926792284L;
	public String payee;
	public String purpose;
	public double amount;
}
