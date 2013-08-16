package de.vagtsi.ligaplan.spielplan.model;

import java.util.Date;


/**
 * One Event within the 'Spielplan': two teams meeting at certain place for a game.  
 * 
 * @author Jens Vagts
 */
public class SpielplanEvent {
	private String homeTeamName;	//the name of the home team
	private String guestTeamName;	//the name of the guest team
	private Date gameTime;			//time and date when the game starts
	private SpielplanLocation location;	//the name and place of the sports hall
	private int gameNumber; 		//the unique number of the game within the season
	
	public SpielplanEvent(String homeTeamName, String guestTeamName,
			Date gameTime, SpielplanLocation location) {
		this.homeTeamName = homeTeamName;
		this.guestTeamName = guestTeamName;
		this.gameTime = gameTime;
		this.location = location;
	}
	
	public SpielplanEvent() {
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}
	
	public String getGuestTeamName() {
		return guestTeamName;
	}
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	public Date getGameTime() {
		return gameTime;
	}
	public void setGameTime(Date gameTime) {
		this.gameTime = gameTime;
	}

	public SpielplanLocation getLocation() {
		return location;
	}
	public void setLocation(SpielplanLocation location) {
		this.location = location;
	}
	
	public int getGameNumber() {
		return gameNumber;
	}
	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
	}
	
}
