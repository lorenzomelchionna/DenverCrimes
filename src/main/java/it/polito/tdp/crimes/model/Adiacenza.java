package it.polito.tdp.crimes.model;

public class Adiacenza {
	
	private String b1;
	private String b2;
	private double peso;
	
	public Adiacenza(String b1, String b2, double peso) {
		super();
		this.b1 = b1;
		this.b2 = b2;
		this.peso = peso;
	}

	public String getB1() {
		return b1;
	}

	public void setB1(String b1) {
		this.b1 = b1;
	}

	public String getB2() {
		return b2;
	}

	public void setB2(String b2) {
		this.b2 = b2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return b1 + " - " + b2 + " = " + peso + "\n";
	}
	
}
