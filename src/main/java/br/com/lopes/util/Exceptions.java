package br.com.lopes.util;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

public final class Exceptions {

	public final static String messageSQLExceptionFromPersistenceException(PersistenceException e){
		String msg = null;
		Throwable cause = e;
		
		while (cause != null && !(cause instanceof SQLException)) {
	        cause = cause.getCause();
	    }
		
		if (cause instanceof SQLException){
			msg = ((SQLException)cause).getMessage();
		}else{
			msg = e.getMessage();
		}
		
		return msg;
	}
}
