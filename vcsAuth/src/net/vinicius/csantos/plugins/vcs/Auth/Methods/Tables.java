package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.util.ArrayList;

public class Tables {
	
	private ArrayList<String> tables = new ArrayList<>();

	public ArrayList<String> getTables() {
		return tables;
	}


	public void setTables(ArrayList<String> tables) {
		this.tables = tables;
	}
	
	public ArrayList<String> setTablesCreation() {
		ArrayList<String> tables = new ArrayList<String>();
		tables.add("CREATE TABLE IF NOT EXISTS `vcsUsers` ( `USERID` INT NOT NULL AUTO_INCREMENT , `USERUUID` VARCHAR(255) NOT NULL , `USERNAME` VARCHAR(50) NOT NULL , `REALNAME` VARCHAR(50) NOT NULL, `USERPASSWORD` VARCHAR(255) NOT NULL , `USEREMAIL` VARCHAR(100) NULL , `USERREGISTERIP` VARCHAR(20) NOT NULL , `USERLASTIP` VARCHAR(20) NOT NULL, `USERLASTLOGIN` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `USERDTREGISTER` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `USERTEMPASS` VARCHAR(255) NULL, `USERONLINE` BOOLEAN NOT NULL DEFAULT FALSE , `USERLOCATION` VARCHAR(100) , `STATUS` INT NOT NULL , PRIMARY KEY (`USERID`), UNIQUE (`USEREMAIL`)) ENGINE = InnoDB;");
		tables.add("CREATE TABLE IF NOT EXISTS `vcslogins` ( `LOGINID` INT NOT NULL AUTO_INCREMENT , `USERID` INT NOT NULL , `DTLOGIN` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP , `LOGINIP` VARCHAR(20) NOT NULL , `LOGINLOCATION` VARCHAR(50) NOT NULL , PRIMARY KEY (`LOGINID`), INDEX `USERID` (`USERID`)) ENGINE = InnoDB;");
		this.setTables(tables);
		
		return this.getTables();
	}

}
