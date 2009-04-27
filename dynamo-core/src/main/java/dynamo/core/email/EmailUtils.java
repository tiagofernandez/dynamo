package dynamo.core.email;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;

import org.apache.commons.lang.StringUtils;

/**
 * EmailUtils.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public final class EmailUtils {

	private EmailUtils() {
		super();
	}

	/**
	 * Checks whether the input string is a valid email address.
	 * 
	 * @param email the address to validate
	 * @return true when valid
	 */
	public static boolean isValid(String emailAddress) {
		boolean isValid = false;
		
		// Ignore blank values
		if (StringUtils.isNotBlank(emailAddress)) {
			
			// [\\w\\.-]+ : Begins with word characters, (may include periods and hypens)
			// @ : It must have a '@' symbol after initial characters
			// ([\\w\\-]+\\.)+ : '@' must follow by more alphanumeric characters (may include hypens)
			// [A-Z]{2,4}$ : Must end with two to four alaphabets
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			
			// Make the pattern insensitive
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(emailAddress.trim());
			
			// Check if it matches to valid pattern
			isValid = matcher.matches();			
		}
		
		return isValid;
	}

	/**
	 * Gets a receivers array, concerning the addresses separated by ',' or ';'.
	 * 
	 * @param to something like "foo@foo.com, bar@bar.com; xyz@xyz.com"
	 * @return an String[] containing the e-mails
	 */
	public static String[] getReceivers(String to) {
		List<String> receivers = new ArrayList<String>();
		
		// Ignore blank values
		if (StringUtils.isNotBlank(to)) {
			
			// Make sure we're dealing only with ','
			String receiversString = to.replace(';', ',');
			StringTokenizer st = new StringTokenizer(receiversString, ",");
			
			// Read all values from CSV
			while (st.hasMoreTokens()) {
				String email = st.nextToken().trim();
				
				// Skip invalid e-mails
				if (!isValid(email)) {
					continue;
				}				
				// Add valid e-mail to list
				receivers.add(email);
			}
		}
		return receivers.toArray(new String[receivers.size()]);
	}

	/**
	 * Gets a String representation for an address array.
	 * 
	 * @param addresses to be evaluated
	 * @return the String representation of all addresses
	 */
	public static String getAddresses(Address[] addresses) {
		StringBuilder sb = new StringBuilder();
		
		// Ignore null params
		if (addresses != null) {
			
			// Read all addresses
			for (int i = 0; i < addresses.length; i++) {
				
				// Skip null values
				if (addresses[i] == null) {
					continue;
				}
				// Add valid address to buffer
				sb.append(addresses[i].toString());
				
				// Append the comma when having more elements
				if (i < addresses.length - 1) {
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}

}