import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.*;

import generated.Locations;
import generated.Locations.Location;
import generated.SiteRep;
import generated.ThreeHourly;
import generated.ThreeHourly.DV.Location.Period;

public class Search {
	static Scanner scan = new Scanner(System.in);
	static int forecastChoice, dataType = 0;
	static ArrayList<Location> looc = new ArrayList<Location>();
	static int userSelection;
	static String forecastType;

	public static void main(String[] args) throws MalformedURLException {
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"---------------------------------------Welcome to the //TODO:Weather Forecast System---------------------------------------");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

		try {
			do {
				System.out.println("Would you like to see (1).Stored Data or (2).Live Data?");
				dataType = scan.nextInt();
			} while (dataType != 1 && dataType != 2);

			do {
				System.out.println("Would you like to see a (1).3-Hourly forecast or (2).Daily forecast?");
				forecastChoice = scan.nextInt();
			} while (forecastChoice != 1 && forecastChoice != 2);

			if (dataType == 1) {
				getStoredLocation();
				if (forecastChoice == 1) {
					forecastType = "3hourly";
					get3HourlyStoredWeather();

				} else {
					forecastType = "daily";
					getDailyStoredWeather();
				}
			} else {
				getLiveLocation();
				if (forecastChoice == 1) {
					forecastType = "3hourly";
					get3HourlyWeather();

				} else {
					forecastType = "daily";
					getDailyWeather();
				}
				storeWeatherData();

			}
		} catch (InputMismatchException e) {
			System.out.println("The response you submitted was not of the correct type. Please try again.");
		}

	}

	private static void getDailyWeather() {
		// TODO Auto-generated method stub
		try {
			JAXBContext dailyContext = JAXBContext.newInstance(SiteRep.class);
			try {
				URL url = new URL(getURL(userSelection, forecastChoice));
				URLConnection connection = url.openConnection();
				connection.connect();
				Unmarshaller unmarshall = dailyContext.createUnmarshaller();
				SiteRep daily = (SiteRep) unmarshall.unmarshal(url);
				for (SiteRep.DV.Location.Period periodd : daily.getDV().getLocation().getPeriod()) {
					String Date = periodd.getValue().toString();
					Date = deleteCharAt(Date, 10);
					System.out
							.println(daily.getDV().getLocation().getName() + " " + daily.getDV().getLocation().getI()); // need
																														// to
																														// delete
					System.out.println("--------------------------Date: " + Date + "--------------------------");
					System.out.println();
					System.out.println();
					for (generated.SiteRep.DV.Location.Period.Rep repp : periodd.getRep()) {

						System.out.println("Weather Forecast type: " + repp.getValue());
						if (repp.getValue().equals("Day")) {
							System.out.println("Wind direction: " + getDirection(repp.getD()));
							System.out.println("Wind gust noon: " + repp.getGn() + "mph");
							System.out.println("Screen Relative Humidity noon: " + repp.getHn() + "%");
							System.out.println("Precipitation Probability day: " + repp.getPPd() + "%");
							System.out.println("Wind Speed: " + repp.getS() + "mph");
							System.out.println("Visibility: " + getVisibility(repp.getV()));
							System.out.println("Day Maximum Temperature: " + repp.getDm() + "°C");
							System.out.println("Feels like day Maximum temperature: " + repp.getFDm() + "°C");
							System.out.println("Weather Type: " + getWeather(repp.getW().toString()));
							System.out.println("Max UV index: " + repp.getU());
						} else {
							System.out.println("Wind direction: " + getDirection(repp.getD()));
							System.out.println("Wind gust midnight: " + repp.getGm() + "mph");
							System.out.println("Screen Relative Humidity midnight: " + repp.getHm() + "%");
							System.out.println("Precipitation Probability night: " + repp.getPPn() + "%");
							System.out.println("Wind Speed: " + repp.getS() + "mph");
							System.out.println("Visibility: " + getVisibility(repp.getV()));
							System.out.println("Night Minimum Temperature: " + repp.getNm() + "°C");
							System.out.println("Feels like Night Minimum temperature: " + repp.getFNm() + "°C");
							System.out.println("Weather Type: " + getWeather(repp.getW().toString()));
						}

						System.out.println();
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Unmarshaller unmarshall = dailyContext.createUnmarshaller();
				SiteRep daily = (SiteRep) unmarshall.unmarshal(new File(
						"\\data\\" + looc.get(userSelection - 1).getId().toString() + " " + forecastType + ".xml"));
				for (SiteRep.DV.Location.Period periodd : daily.getDV().getLocation().getPeriod()) {
					String Date = periodd.getValue().toString();
					Date = deleteCharAt(Date, 10);
					System.out
							.println(daily.getDV().getLocation().getName() + " " + daily.getDV().getLocation().getI()); // need
																														// to
																														// delete
					System.out.println("--------------------------Date: " + Date + "--------------------------");
					System.out.println();
					System.out.println();
					for (generated.SiteRep.DV.Location.Period.Rep repp : periodd.getRep()) {

						System.out.println("Weather Forecast type: " + repp.getValue());
						if (repp.getValue().equals("Day")) {
							System.out.println("Wind direction: " + getDirection(repp.getD()));
							System.out.println("Wind gust noon: " + repp.getGn() + "mph");
							System.out.println("Screen Relative Humidity noon: " + repp.getHn() + "%");
							System.out.println("Precipitation Probability day: " + repp.getPPd() + "%");
							System.out.println("Wind Speed: " + repp.getS() + "mph");
							System.out.println("Visibility: " + getVisibility(repp.getV()));
							System.out.println("Day Maximum Temperature: " + repp.getDm() + "°C");
							System.out.println("Feels like day Maximum temperature: " + repp.getFDm() + "°C");
							System.out.println("Weather Type: " + getWeather(repp.getW().toString()));
							System.out.println("Max UV index: " + repp.getU());
						} else {
							System.out.println("Wind direction: " + getDirection(repp.getD()));
							System.out.println("Wind gust midnight: " + repp.getGm() + "mph");
							System.out.println("Screen Relative Humidity midnight: " + repp.getHm() + "%");
							System.out.println("Precipitation Probability night: " + repp.getPPn() + "%");
							System.out.println("Wind Speed: " + repp.getS() + "mph");
							System.out.println("Visibility: " + getVisibility(repp.getV()));
							System.out.println("Night Minimum Temperature: " + repp.getNm() + "°C");
							System.out.println("Feels like Night Minimum temperature: " + repp.getFNm() + "°C");
							System.out.println("Weather Type: " + getWeather(repp.getW().toString()));
						}

						System.out.println();
					}
				}
			}
		} catch (JAXBException e) {
			System.out.println("dail live");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void getDailyStoredWeather() {
		// TODO Auto-generated method stub
		try {
			JAXBContext dailyContext = JAXBContext.newInstance(SiteRep.class);

			Unmarshaller unmarshall = dailyContext.createUnmarshaller();
			SiteRep daily = (SiteRep) unmarshall
					.unmarshal(new File("SavedData//" + looc.get(userSelection - 1).getId().toString() + " daily.xml"));
			for (SiteRep.DV.Location.Period periodd : daily.getDV().getLocation().getPeriod()) {
				String Date = periodd.getValue().toString();
				Date = deleteCharAt(Date, 10);
				System.out.println(daily.getDV().getLocation().getName() + " " + daily.getDV().getLocation().getI()); // need
																														// to
																														// delete
				System.out.println("--------------------------Data Date: " + Date + "--------------------------");
				System.out.println("---------------------------Data Time: " + daily.getDV().getDataDate().getHour()
						+ ":" + daily.getDV().getDataDate().getMinute() + "0:0"
						+ daily.getDV().getDataDate().getSecond() + "---------------------------");

				System.out.println();
				System.out.println();
				for (generated.SiteRep.DV.Location.Period.Rep repp : periodd.getRep()) {

					System.out.println("Weather Forecast type: " + repp.getValue());
					if (repp.getValue().equals("Day")) {
						System.out.println("Wind direction: " + getDirection(repp.getD()));
						System.out.println("Wind gust noon: " + repp.getGn() + "mph");
						System.out.println("Screen Relative Humidity noon: " + repp.getHn() + "%");
						System.out.println("Precipitation Probability day: " + repp.getPPd() + "%");
						System.out.println("Wind Speed: " + repp.getS() + "mph");
						System.out.println("Visibility: " + getVisibility(repp.getV()));
						System.out.println("Day Maximum Temperature: " + repp.getDm() + "°C");
						System.out.println("Feels like day Maximum temperature: " + repp.getFDm() + "°C");
						System.out.println("Weather Type: " + getWeather(repp.getW().toString()));
						System.out.println("Max UV index: " + repp.getU());
					} else {
						System.out.println("Wind direction: " + getDirection(repp.getD()));
						System.out.println("Wind gust midnight: " + repp.getGm() + "mph");
						System.out.println("Screen Relative Humidity midnight: " + repp.getHm() + "%");
						System.out.println("Precipitation Probability night: " + repp.getPPn() + "%");
						System.out.println("Wind Speed: " + repp.getS() + "mph");
						System.out.println("Visibility: " + getVisibility(repp.getV()));
						System.out.println("Night Minimum Temperature: " + repp.getNm() + "°C");
						System.out.println("Feels like Night Minimum temperature: " + repp.getFNm() + "°C");
						System.out.println("Weather Type: " + getWeather(repp.getW().toString()));
					}

					System.out.println();
				}
			}

		} catch (JAXBException e) {
			String ans;
			System.out.println("The File you have searched for is not available");
			do {
				System.out.println("Would you like to see Live data instead? (Yes/no)");
				ans = scan.next();
				// ans = scan.nextLine();
			} while (!ans.equalsIgnoreCase("yes") && !ans.equalsIgnoreCase("no"));

			if (ans.equalsIgnoreCase("yes")) {
				getDailyWeather();
				storeWeatherData();
			} else if (ans.equalsIgnoreCase("no")) {
				try {
					main(null);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	public static void saveFileFromUrlWithCommonsIO(String fileName, String fileUrl)
			throws MalformedURLException, IOException {
		FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
	}

	public static void storeWeatherData() {
		String save;
		do {
			System.out.println("Would you like to Update the data in the saved file for this location?(Yes/No)");
			save = scan.next();
		} while (!save.equalsIgnoreCase("yes") && !save.equalsIgnoreCase("no"));
		// Make sure that this directory exists
		if (save.equalsIgnoreCase("yes")) {

			String dirName = "SavedData";
			try {
				saveFileFromUrlWithCommonsIO(
						dirName + "\\" + looc.get(userSelection - 1).getId().toString() + " " + forecastType + ".xml",
						getURL(userSelection, forecastChoice));
				// dirName + "\\"+ looc.get(userSelection-1).getName() + " " + forecastType
				// +".xml", getURL(userSelection, forecastChoice));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("savingggg");
				e.printStackTrace();
			}
		}
		try {
			main(null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void get3HourlyWeather() {
		try {
			JAXBContext context = JAXBContext.newInstance(ThreeHourly.class);
// url = null;
			try {
				URL url = new URL(getURL(userSelection, forecastChoice));

				Unmarshaller unmarshaller = context.createUnmarshaller();
				ThreeHourly siterep = (ThreeHourly) unmarshaller.unmarshal(url);

				for (ThreeHourly.DV.Location.Period period : siterep.getDV().getLocation().getPeriod()) {

					String Date = period.getValue().toString();
					Date = deleteCharAt(Date, 10);
					System.out.println(
							siterep.getDV().getLocation().getName() + " " + siterep.getDV().getLocation().getI()); // need
																													// to
																													// delete
					System.out.println("--------------------------Date: " + Date + "--------------------------");
					System.out.println();
					System.out.println();

					for (Period.Rep rep : period.getRep()) {
						int t = rep.getValue();
						int hours = t / 60; // since both are ints, you get an int
						int minutes = t % 60;
						System.out.printf("Time : %d:%02d %n", hours, minutes);
						System.out.println("Wind direction: " + getDirection(rep.getD()));
						System.out.println("Wind gust: " + rep.getG() + "mph");
						System.out.println("Wind Speed: " + rep.getS() + "mph");
						System.out.println("Feels like temperature: " + rep.getF() + "°C");
						System.out.println("Screen Relative Humidity: " + rep.getH() + "%");
						System.out.println("Temperature: " + rep.getT() + "°C");
						System.out.println("Visibility: " + getVisibility(rep.getV()));
						System.out.println("Max UV index: " + rep.getU());
						System.out.println("Weather Type: " + getWeather(rep.getW().toString()));
						System.out.println("Precipitation Probability: " + rep.getPp() + "%");
						System.out.println();
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(locations);

		} catch (JAXBException e) {
			System.out.println("3hrlylive");

			e.printStackTrace();
		}
	}

	public static void get3HourlyStoredWeather() {
		try {
			JAXBContext context = JAXBContext.newInstance(ThreeHourly.class);
// url = null;

			Unmarshaller unmarshaller = context.createUnmarshaller();
			ThreeHourly siterep = (ThreeHourly) unmarshaller.unmarshal(
					new File("SavedData//" + looc.get(userSelection - 1).getId().toString() + " 3hourly.xml"));

			for (ThreeHourly.DV.Location.Period period : siterep.getDV().getLocation().getPeriod()) {

				String Date = period.getValue().toString();
				Date = deleteCharAt(Date, 10);
				System.out
						.println(siterep.getDV().getLocation().getName() + " " + siterep.getDV().getLocation().getI()); // need
																														// to
																														// delete
				System.out.println("--------------------------Data Date: " + Date + "--------------------------");
				System.out.println("---------------------------Data Time: " + siterep.getDV().getDataDate().getHour()
						+ ":" + siterep.getDV().getDataDate().getMinute() + "0:0"
						+ siterep.getDV().getDataDate().getSecond() + "---------------------------");

				System.out.println();
				System.out.println();

				for (Period.Rep rep : period.getRep()) {
					int t = rep.getValue();
					int hours = t / 60; // since both are ints, you get an int
					int minutes = t % 60;
					System.out.printf("Time : %d:%02d %n", hours, minutes);
					System.out.println("Wind direction: " + getDirection(rep.getD()));
					System.out.println("Wind gust: " + rep.getG() + "mph");
					System.out.println("Wind Speed: " + rep.getS() + "mph");
					System.out.println("Feels like temperature: " + rep.getF() + "°C");
					System.out.println("Screen Relative Humidity: " + rep.getH() + "%");
					System.out.println("Temperature: " + rep.getT() + "°C");
					System.out.println("Visibility: " + getVisibility(rep.getV()));
					System.out.println("Max UV index: " + rep.getU());
					System.out.println("Weather Type: " + getWeather(rep.getW().toString()));
					System.out.println("Precipitation Probability: " + rep.getPp() + "%");
					System.out.println();
				}
			}
			String ans;

			do {
				System.out.println("Would you like to go back to the Home screen? (Yes/no)");
				ans = scan.next();
				// ans = scan.ne1ine();
			} while (!ans.equalsIgnoreCase("yes") && !ans.equalsIgnoreCase("no"));
			if (ans.equalsIgnoreCase("no")) {
				System.out.println("Goodbye!");
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"--------------------------------------- PROGRAMMED WITH LOVE BY //TODO: Team Name ---------------------------------------");
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------");

			} else if (ans.equalsIgnoreCase("yes")) {
				try {
					main(null);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// System.out.println(locations);

		} catch (JAXBException e) {
			String ans;
			System.out.println("The File you have searched for is not available");
			do {
				System.out.println("Would you like to see Live data instead? (Yes/no)");
				ans = scan.next();
				// ans = scan.ne1ine();
			} while (!ans.equalsIgnoreCase("yes") && !ans.equalsIgnoreCase("no"));

			if (ans.equalsIgnoreCase("yes")) {
				get3HourlyWeather();
				storeWeatherData();
			} else if (ans.equalsIgnoreCase("no")) {
				try {
					main(null);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// e.printStackTrace();
		}
	}

	public static void getStoredLocation() {

		looc.clear();
		System.out.println("Enter name of Location:");
		System.out.println();
		int count = 1;
		boolean notfound = true;
		String searchTerm = scan.next();
		try {

			JAXBContext context = JAXBContext.newInstance(Locations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Locations locations = (Locations) unmarshaller.unmarshal(new File("SiteList.xml"));
			// searchTerm = scan.nextLine();
			for (Locations.Location loc : locations.getLocation()) {
				Boolean search = loc.getName().toLowerCase().contains(searchTerm.toLowerCase());

				if (search == true) {
					if (count <= 20) {

						looc.add(loc);
						System.out.println(count + " " + loc.getName() + ", " + loc.getUnitaryAuthArea());
						count++;
					} else {
						System.out.println("Here are your top 20 results.");
						break;
					}
				} else if (loc.getId() == 355998 && count == 1) {
					System.out.println("Your search returned no Results. Try again.");
					getStoredLocation();
				}
			}
			if (count != 1) {
				do {
					System.out.println("Please select a number or type \"0\" to Search Again:");
					userSelection = scan.nextInt();
				} while (userSelection > count - 1);
				if (userSelection == 0) {
					getStoredLocation();
				}
			}

		} catch (JAXBException e) {
			System.out.println("moo i'm a chicken");

			e.printStackTrace();

		}
	}

	public static void getLiveLocation() {

		looc.clear();
		System.out.println("Enter name of Location:");
		System.out.println();
		int count = 1;
		boolean notfound = true;
		String searchTerm = scan.next();
		try {

			JAXBContext context = JAXBContext.newInstance(Locations.class);
			// URL url = null;
			try {
				URL url = new URL(
						"http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/xml/sitelist?key=6dd3ad66-8571-4e7d-a390-b94ed4ce35f6");

				Unmarshaller unmarshaller = context.createUnmarshaller();
				Locations locations = (Locations) unmarshaller.unmarshal(url);
				for (Locations.Location loc : locations.getLocation()) {
					Boolean search = loc.getName().toLowerCase().contains(searchTerm.toLowerCase());

					if (search == true) {
						if (count <= 20) {

							looc.add(loc);
							System.out.println(count + " " + loc.getName() + ", " + loc.getUnitaryAuthArea());
							count++;
						} else {
							System.out.println("Here are your top 20 results.");
							break;
						}
					} else if (loc.getId() == 355998 && count == 1) {
						System.out.println("Your search returned no Results. Try again.");
						getStoredLocation();
					}
				}
				if (count != 1) {
					do {
						System.out.println("Please select a number or type \"0\" to Search Again:");
						userSelection = scan.nextInt();
					} while (userSelection > count - 1);
					if (userSelection == 0) {
						getStoredLocation();
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JAXBException e) {
			System.out.println("The location could not be found. Try running the program again.");
			// e.printStackTrace();

		}
	}

	private static String deleteCharAt(String strValue, int index) {
		return strValue.substring(0, index) + strValue.substring(index + 1);

	}

	public static String getURL(int selection, int forecastselection) {
		if (forecastselection == 1) {
			return "http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/xml/"
					+ looc.get(selection - 1).getId().toString() + "?res=" + forecastType
					+ "&key=6dd3ad66-8571-4e7d-a390-b94ed4ce35f6";
		} else {
			return "http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/xml/"
					+ looc.get(selection - 1).getId().toString() + "?res=" + forecastType
					+ "&key=6dd3ad66-8571-4e7d-a390-b94ed4ce35f6";
		}
	}

	public static String getWeather(String s) {

		int weatherType;

		weatherType = Integer.parseInt(s);

		switch (weatherType) {
		case 0:
			return "Clear night";

		case 1:
			return "Sunny Day";

		case 2:
			return "Partly cloudy (night)";

		case 3:
			return "Partly cloudy (day)";

		case 4:
			return "Not used";

		case 5:
			return "Mist";

		case 6:
			return "Fog";

		case 7:
			return "Cloudy";

		case 8:
			return "Overcast";

		case 9:
			return "Light rain shower (night)";

		case 10:
			return "Light rain shower (day)";

		case 11:
			return "Drizzle";

		case 12:
			return "Light rain";

		case 13:
			return "Heavy rain (night)";

		case 14:
			return "Heavy rain (day)";

		case 15:
			return "Heavy rain";

		case 16:
			return "Sleet shower (night)";

		case 17:
			return "Sleet shower (day)";

		case 18:
			return "Sleet";

		case 19:
			return "Hail shower (night)";

		case 20:
			return "Hail shower (day)";

		case 21:
			return "Hail";

		case 22:
			return "Light snow shower (night)";

		case 23:
			return "Light snow shower (day)";

		case 24:
			return "Light snow";

		case 25:
			return "Heavy snow shower (night)";

		case 26:
			return "Heavy snow shower (day)";

		case 27:
			return "Heavy snow";

		case 28:
			return "Thunder shower (night)";

		case 29:
			return "Thunder shower (day)";

		case 30:
			return "Thunder";

		default:
			return "Not available";
		}
	}

	public static String getVisibility(String s) {

		switch (s) {
		case "UN":
			return "Unknown";

		case "VP":
			return "Very Poor";

		case "PO":
			return "Poor";

		case "MO":
			return "Moderate";

		case "GO":
			return "Good";

		case "VG":
			return "Very Good";

		case "EX":
			return "Excellent";

		default:
			return "Not available";
		}
	}

	public static String getDirection(String s) {

		switch (s) {
		case "N":
			return "North";

		case "NNE":
			return "North-Northeast";

		case "NE":
			return "Northeast";

		case "ENE":
			return "East-Northeast";

		case "E":
			return "East";

		case "ESE":
			return "East Southeast";

		case "SE":
			return "Southeast";

		case "SSE":
			return "South-Southeast";

		case "S":
			return "South";

		case "SSW":
			return "South-Southwest";

		case "SW":
			return "Southwest";

		case "WSW":
			return "West-Southwest";

		case "W":
			return "West";

		case "WNW":
			return "West-Northwest";

		case "NW":
			return "Northwest";

		case "NNW":
			return "North-Northwest";

		default:
			return "No data available";
		}
	}

}