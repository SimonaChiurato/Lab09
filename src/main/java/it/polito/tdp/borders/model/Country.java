package it.polito.tdp.borders.model;

public class Country {
	private String stateAbb;
	private int cod;
	private String stateName;
	
	
	
	public Country(String stateAbb, int cod, String stateName) {
		super();
		this.stateAbb = stateAbb;
		this.cod = cod;
		this.stateName = stateName;
	}
	public String getStateAbb() {
		return stateAbb;
	}
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateAbb == null) ? 0 : stateAbb.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (stateAbb == null) {
			if (other.stateAbb != null)
				return false;
		} else if (!stateAbb.equals(other.stateAbb))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return stateName ;
	}
	
	
	

}
