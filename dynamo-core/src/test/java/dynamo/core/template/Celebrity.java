package dynamo.core.template;

/**
 * Celebrity.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class Celebrity {

	private String name;
	private String city;
	private String country;

	/**
	 * Constructor.
	 * 
	 * @param name the name
	 * @param city the city
	 * @param country the country
	 */
	public Celebrity(String name, String city, String country) {
		this.name = name;
		this.city = city;
		this.country = country;
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
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}