/* 
 * Copyright (c) 2004-2005 QOS.ch
 * 
 * All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.osgi.logservice.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>LogServiceImpl</code> is a simple OSGi LogService implemenation that delegates to a slf4j 
 * Logger.
 * 
 * @author John Conlon
 */
public class LogServiceImpl implements LogService {

	private static final String UNKNOWN = "[Unknown]";

	private final Logger delegate;

	/**
	 * Creates a new instance of LogServiceImpl.
	 * 
	 */
	public LogServiceImpl(Bundle bundle) {
		String name = (String) bundle.getHeaders().get(
				Constants.BUNDLE_SYMBOLICNAME);
		String version = (String) bundle.getHeaders().get(
				Constants.BUNDLE_VERSION);
		delegate = LoggerFactory.getLogger(name + '.' + version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.log.LogService#log(int, java.lang.String)
	 */
	public void log(int level, String message) {
		switch (level) {
		case LOG_DEBUG:
			delegate.debug(message);
			break;
		case LOG_ERROR:
			delegate.error(message);
			break;
		case LOG_INFO:
			delegate.info(message);
			break;
		case LOG_WARNING:
			delegate.warn(message);
			break;
		default:
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.log.LogService#log(int, java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void log(int level, String message, Throwable exception) {
		switch (level) {
		case LOG_DEBUG:
			delegate.debug(message, exception);
			break;
		case LOG_ERROR:
			delegate.error(message, exception);
			break;
		case LOG_INFO:
			delegate.info(message, exception);
			break;
		case LOG_WARNING:
			delegate.warn(message, exception);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.log.LogService#log(org.osgi.framework.ServiceReference,
	 *      int, java.lang.String)
	 */
	public void log(ServiceReference sr, int level, String message) {
		
		switch (level) {
		case LOG_DEBUG:
			if(delegate.isDebugEnabled()){
				delegate.debug(createMessage(sr, message));
			}
			break;
		case LOG_ERROR:
			if(delegate.isErrorEnabled()){
				delegate.error(createMessage(sr, message));
			}
			break;
		case LOG_INFO:
			if(delegate.isInfoEnabled()){
				delegate.info(createMessage(sr, message));
			}
			break;
		case LOG_WARNING:
			if(delegate.isWarnEnabled()){
				delegate.warn(createMessage(sr, message));
			}
			break;
		default:
			break;
		}
	}

	/**
	 * createMessage.
	 *
	 * @param sr
	 * @param message
	 * @return
	 */
	private String createMessage(ServiceReference sr, String message) {
		StringBuffer output = new StringBuffer();
		if (sr != null) {
			output.append('[').append(sr.toString()).append(']')
			.append(message);
		} else {
			output.append(UNKNOWN).append(message);
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.log.LogService#log(org.osgi.framework.ServiceReference,
	 *      int, java.lang.String, java.lang.Throwable)
	 */
	public void log(ServiceReference sr, int level, String message,
			Throwable exception) {

		switch (level) {
		case LOG_DEBUG:
			if(delegate.isDebugEnabled()){
				delegate.debug(createMessage(sr, message), exception);
			}
			break;
		case LOG_ERROR:
			if(delegate.isErrorEnabled()){
				delegate.error(createMessage(sr, message), exception);
			}
			break;
		case LOG_INFO:
			if(delegate.isInfoEnabled()){
				delegate.info(createMessage(sr, message), exception);
			}
			break;
		case LOG_WARNING:
			if(delegate.isWarnEnabled()){
				delegate.warn(createMessage(sr, message), exception);
			}
			break;
		default:
			break;
		}
	}

}
