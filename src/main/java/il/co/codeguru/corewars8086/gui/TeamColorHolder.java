package il.co.codeguru.corewars8086.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BS
 */
public class TeamColorHolder {
  public static final int MAX_COLORS = 365;
  private final Map<String, Color> teamToColor;
  private final int teamInitialsLength;
	
	private String getTeamInitials(String team) {
		return team.substring(0, Math.min(this.teamInitialsLength, team.length())).toUpperCase();
	}
 
	public TeamColorHolder(String[] teamNames, int teamInitialsLength, String colorsPath) throws IOException {
		// see http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/
		teamToColor = new HashMap<>();
		this.teamInitialsLength = teamInitialsLength;
		
		// If colors file exists - load static team colors into holder
		File colorsFile = new File(colorsPath);
		if (colorsFile.exists() && colorsFile.isFile()) {
			List<String> contents = Files.readAllLines(colorsFile.toPath());
			
			contents.remove(0); // Remove headers
			for (String line : contents) {
				String[] data = line.split(",");
				if (data.length != 2) continue;
				teamToColor.put(data[0].trim(), Color.decode(data[1].trim()));
			}
		}
		
		float golden_ratio_conjugate = 0.618033988749895f;
		float x = 0;
		for (String teamName : teamNames) {
			String teamInitials = getTeamInitials(teamName);
			if (!teamToColor.containsKey(teamInitials)) {
				teamToColor.put(teamInitials, Color.getHSBColor(x % 1, 0.8f, 0.95f));
				x += golden_ratio_conjugate;
			}
		}
	}
  
  public Color getColor(String team, boolean darker) {
    String teamInitials = getTeamInitials(team);
    if (darker) {
      return teamToColor.get(teamInitials).darker();
    } else {
      return teamToColor.get(teamInitials);
    }
  }
}
