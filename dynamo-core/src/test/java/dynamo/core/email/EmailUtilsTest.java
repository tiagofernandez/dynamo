package dynamo.core.email;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test case for EmailUtils.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class EmailUtilsTest {

	@DataProvider(name = "emailAddressDataProvider")
	public Object[][] getEmailAddressesTestData() {
		return new Object[][] {
			{ new String[] { "foo@bar.com", "abc@def.com", "john@doe.com", "something at gmail.com" } },
			{ new String[] { "tiago182@ymail.com", "tiago182@gmail.com", "my yahoo mail@yahoo.com" } }
		};
	}

	@Test(dataProvider = "emailAddressDataProvider")
	public void resolveEmailAddresses(String[] addresses) throws Exception {
		StringBuilder addressesCsv = new StringBuilder();
		for (int i = 0; i < addresses.length; i++) {
			addressesCsv.append(addresses[i]);
			if (i < addresses.length - 1) {
				addressesCsv.append(", ");
			}
		}
		List<String> receiverList = Arrays.asList(EmailUtils.getReceivers(addressesCsv.toString()));
		for (int i = 0; i < addresses.length; i++) {
			if (addresses[i] != null && EmailUtils.isValid(addresses[i])) {
				assertTrue(receiverList.contains(addresses[i]));
			}
			else {
				assertFalse(receiverList.contains(addresses[i]));
			}
		}
	}

	@Test(dataProvider = "emailAddressDataProvider")
	public void extractInternetAddresses(String[] addresses) throws Exception {
		InternetAddress[] internetAddresses = new InternetAddress[addresses.length];
		for (int i = 0; i < addresses.length; i++) {
			try {
				internetAddresses[i] = new InternetAddress(addresses[i]);
			}
			catch (AddressException ex) {
				continue; // Ignore it!
			}
		}
		String addressesCsv = EmailUtils.getAddresses(internetAddresses);
		for (int i = 0; i < addresses.length; i++) {
			if (internetAddresses[i] != null) { // Skip invalid addresses
				assertTrue(addressesCsv.indexOf(addresses[i]) > -1);
			}
		}
	}
	
}