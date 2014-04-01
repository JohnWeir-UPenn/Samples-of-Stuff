import java.io.IOException;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// Code to scrape the CIA factbook to find what South American countries are 
// most prone to earthquakes. This code can be easily modified to find any 
// bit of genaral information about any country or region

public class HTMLScrape_Basic {

	// list of all country objects
	public static HashSet<Country> countrySet = new HashSet<Country>();
	// list of addresses to all country based web pages
	public static HashSet<String> addressSet = new HashSet<String>();

	
	//intented input https://www.cia.gov/library/publications/the-world-factbook/print/textversion.html
	public static void main(String[] args) {
		scan(args[0]);
		for (Country country : countrySet) {
			System.out.println(country.getName());
		}
	}

	public static void scan(String address) {
		Document doc;
		try {

			// need http protocol
			doc = Jsoup.connect(address).get();

			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {

				Country country = new Country(link.text());

				String linkString = link.attr("href");

				// get followable links to all possible country pages
				if (address.endsWith(".html")) {
					address = address.substring(0, address.length()-23);
				}
				address = address.replaceAll("print/textversion/","");
				if (linkString.lastIndexOf(".html") != -1) {
					linkString = linkString.substring
							(0, linkString.lastIndexOf(".html") + 5);
				}
				if (linkString.startsWith("..")) {
					linkString = linkString.substring(2);
				}

				String nextAddress = address + linkString;
				Document countryDoc;

				try {
					countryDoc = Jsoup.connect(nextAddress).get();

					//get all links
					Elements countryLinks = countryDoc.select("a[href]");
					String text = countryDoc.body().text();
					
					for (Element countryElement : countryLinks) {
						if (countryElement.text().contains("South America") && text.contains("earthquake")) {
							countrySet.add(country);
							addressSet.add(nextAddress);
						}
					}
				}

				catch (IOException E) {
					// ignore exceptions, these aren't links to country pages
				}
			}
		}
		catch (IOException E) {
			E.getStackTrace();
		}
	}
}