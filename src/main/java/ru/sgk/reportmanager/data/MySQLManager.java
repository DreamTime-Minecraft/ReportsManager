package ru.sgk.reportmanager.data;

import ru.sgk.dreamtimeapi.data.Database;
import ru.sgk.reportmanager.ReportManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLManager 
{
	private static Database db;
	private static Connection connection;
	public static class Requests
	{
		/**
		 * <h3>Creates table with following fields: </h3>
		 * <p><b>id</b> - id of report<br>
		 * <b>responded</b> - was the repors responded or not <br>
		 * <b>reporter_player_name </b>- name of player that sends report <br>
		 * <b>reported_player_name</b> - name of player to whom report sends or theme of report<br>
		 * <b>to_player</b> if report about player<br>
		 * <b>responder</b> - name of admin that replied on report (null if report is not replied)<br>
		 * <b>response</b> - text of response<br>
		 * <b>text</b> - text of report<br></p>
		 */
		public static void createTable()
		{
			db.execute(
					  "CREATE TABLE IF NOT EXISTS `reportmanager`("
					+ "`id` INT(8) PRIMARY KEY AUTO_INCREMENT," // айди репорта
					+ "`responded` BOOLEAN DEFAULT FALSE,"		// был ли ответ на репорт
					+ "`reporter_player_name` VARCHAR(255),"	// ник того, кто создал репорт
					+ "`reported_player_name` VARCHAR(255),"	// ник того, на кого жалоба
					+ "`to_player` BOOLEAN DEFAULT TRUE,"		// Если репорт об игроке
					+ "`responder` VARCHAR(255),"				// Ник админа, который ответил на репорт
					+ "`response` TEXT,"						// ответ от админа
					+ "`checked` BOOLEAN DEFAULT FALSE,"		// Был ли репорт просмотрен (мб уберу, ибо хз как это будет работать через гуи)
					+ "`text` TEXT) Engine=InnoDB DEFAULT CHARSET=utf8;");
		}

		private static Report getReportFromResult(ResultSet rs) throws SQLException {

			long id = rs.getInt("id");
			String reporter = rs.getString("reporter_player_name");
			String reported = rs.getString("reported_player_name");
			boolean responded = rs.getBoolean("responded");
			String text = rs.getString("text");
			boolean toPlayer = rs.getBoolean("to_player");
			String response = rs.getString("response");
			String responder = rs.getString("responder");
			boolean checked = rs.getBoolean("checked");
			return new Report(id, responded, reporter, reported, toPlayer, text, response, responder, checked);
		}
		
		/**
		 * Gets reports of specific player 
		 * @param playername name of player
		 * @param index index of page (one page is 5 reports)
		 * @return list of reports of player, or null or empty list if player have no reports 
		 */
		public static List<Report> getPlayerReports(String playername, int index)
		{

			try (ResultSet rs = db.query("SELECT * FROM `reportmanager` WHERE `reporter_player_name` = ? ORDER BY `id` DESC limit ?, 5", playername, index-1))
			{

				List<Report> reportList = new ArrayList<>();
				
				while (rs.next())
				{
					reportList.add(getReportFromResult(rs));
				}
				
				return index != 1 && reportList.isEmpty() ? null : reportList;
			}
			catch (SQLException e) { e.printStackTrace(); }
			return null;
			
		}

		/**
		 * Sends response to report of specific id
		 * @param id id of report
		 * @param str string to append
		 * @return false if exist no reports with ID <i>id</i>. Otherwise returns true
		 */
		public static boolean sendResponse(int id, String str, String responder)
		{
				return db.execute("UPDATE `reportmanager` SET `response` = ?, `responder` = ?, `responded` = TRUE, `checked` = FALSE where id = ?"
						, str, responder, id) > 0;
		}
		
		/**
		 * Sends respons of report
		 * @param reporter name of player that sends report
		 * @param reported name of player to that report sends or theme of report
		 * @param text text of report
		 */
		public static long sendReport(String reporter, String reported, List<String> text, boolean toPlayer)
		{
			StringBuilder sb = new StringBuilder();
			for (String s : text)
			{
				sb.append(s).append("\n");
			}
				
				db.execute("INSERT INTO `reportmanager`(`reporter_player_name`, `reported_player_name`, `text`, `to_player`) VALUES (?, ?, ?, ?)",
						reporter,
						reported,
						sb.toString(),
						toPlayer);
				
				try (ResultSet rs = db.query("SELECT * FROM `reportmanager` WHERE `reporter_player_name` = ? ORDER BY id DESC LIMIT 1", reporter))
				{
					rs.next();
					int i = rs.getInt("id");
					
					return i;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return -1;
		}
		public static boolean checkReport(long id)
		{
			if (db.execute("UPDATE `reportmanager` SET `checked` = TRUE WHERE `id` = ?", id) > 0)
				return true;
			return false;
		}
		public static Report getReport(long id)
		{
			try (ResultSet rs = db.query("SELECT * FROM `reportmanager` WHERE id = ?", id))
			{
				if (rs.next())
				{
					return getReportFromResult(rs);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/** 
		 * @param index - index of page
		 * @return List of reports of some page. List contains only non-responded values
		 */
		public static List<Report> getReports(int index, int pageSize)
		{
			int from = (index-1)*pageSize;
			
			
			try (ResultSet rs = db.query("SELECT * FROM `reportmanager` WHERE `responded` = FALSE ORDER BY id LIMIT ?, ?", from, pageSize))
			{
				List<Report> reportList = new ArrayList<>(pageSize);
				
				while (rs.next())
				{
					reportList.add(getReportFromResult(rs));
				}
				
				return index != 1 && reportList.isEmpty() ? null : reportList;
				
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return null;
		}

		/**
		 *
		 */
		public static List<Report> getReports()
		{
			try (ResultSet rs = db.query("SELECT * FROM `reportmanager` WHERE `responded` = FALSE ORDER BY id"))
			{
				List<Report> reportList = new ArrayList<>();

				while (rs.next())
				{
					reportList.add(getReportFromResult(rs));
				}

				return reportList.isEmpty() ? null : reportList;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * @return true if connection established
	 */
	public static boolean isConnected() 
	{ 
		return connection != null;
	}
	/**
	 * connect to database
	 * @param host - host
	 * @param database - database name
	 * @param user - username
	 * @param password - password of database user
	 */
	public static void connect(String host, String database, String user, String password)
	{
		if (!isConnected())
		{
			try {
				connection  = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database+"?autoReconnect=true",user,password);
				ReportManager.log("§aConnection with database succesful complete");
			} catch (SQLException e) {
				ReportManager.log("§cCannot connect to database: ");
				System.out.println(e.getMessage());
			}
		}
	}
	/**
	 * closes database connection
	 */
	public static void closeConnection()
	{
		try {
			if (db != null)
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isConnected())
		{
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
	
				ReportManager.log("§cError with close the connectoin:");
				System.out.println(e.getMessage());
			}
		}
	}
	public static Database getDB() {
		return db;
	}
}
