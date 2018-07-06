package rs.org.amss.model;

import java.io.Serializable;

public class RoadConditionsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7243654544091109555L;
	public boolean vremenskePrilike;
	public boolean radoviNaPutu;
	public boolean obaveznaZimskaOprema;
	public boolean granicniPrelaz;
	public boolean ogranicenjaZaTeretnaVozila;
	public boolean obustaveSaobracaja;
	public boolean informacijeOStanju;
	public boolean crneTacke;
	public boolean klizavKolovoz;
	public boolean opasnostOdOdrona;
	public boolean naizmenicnoPropustanje;
	public boolean jakVetar;
	public boolean opasnostNaPutu;
	
	public boolean anythingSelected() {
		return vremenskePrilike 
			|| radoviNaPutu
			|| obaveznaZimskaOprema
			|| granicniPrelaz
			|| ogranicenjaZaTeretnaVozila
			|| obustaveSaobracaja
			|| informacijeOStanju
			|| crneTacke
			|| klizavKolovoz
			|| opasnostOdOdrona
			|| naizmenicnoPropustanje
			|| jakVetar
			|| opasnostNaPutu;
	}
}
