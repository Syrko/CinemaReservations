package cinemacomponents;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Film {
	
	private String filmID;
	private String filmTitle;
	private String filmCategory;
	private String filmDescription;
	
	// Constructor
	public Film(String filmID, String filmTitle, String filmCategory, String filmDescription) {
		setFilmID(filmID);
		setFilmTitle(filmTitle);
		setFilmCategory(filmCategory);
		setFilmDescription(filmDescription);
		System.out.println();
		System.out.println("The film: " + getFilmTitle() + ", " + getFilmCategory() + " (ID: " + getFilmID() + ") was created successfully!");
		System.out.println("Description: " + getFilmDescription());
		System.out.println();
	}
	
	public void writeToFile() {
		try {
			FileWriter fileWriter = new FileWriter(this.getClass().getSimpleName() + ".txt", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println("ID: " + getFilmID());
			printWriter.println("Title: " + getFilmTitle());
			printWriter.println("Category: " + getFilmCategory());
			printWriter.println("Description: " + getFilmDescription());
			printWriter.println("=============================");
			printWriter.close();
		}
		catch(Exception e) {
			
		}
	}
	
	// Getters
	public String getFilmID() {return filmID;}
	public String getFilmTitle() {return filmTitle;}	
	public String getFilmCategory() {return filmCategory;}	
	public String getFilmDescription() {return filmDescription;}
	
	// Setters
	public void setFilmID(String filmID) {this.filmID = filmID;}	
	public void setFilmTitle(String filmTitle) {this.filmTitle = filmTitle;}	
	public void setFilmCategory(String filmCategory) {this.filmCategory = filmCategory;}
	public void setFilmDescription(String filmDescription) {this.filmDescription = filmDescription;}
	
	
}
