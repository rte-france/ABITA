package com.services.auditservice;

import java.io.IOException;
import java.util.List;

/**
 * Interface in charge of the management of the activity logs.
 * @author
 * @version 1.0
 */
public interface ActivityTrackerRestit {

	/**
	 * Explore count file indicator
	 * @param repository repository contains count file
	 * @param idApp application's id
	 * @return file contents
	 * @throws IOException I/O exception
	 */
	List<String[]> exploreCountFile(String repository, String idApp) throws IOException;

	/**
	 * Explore duration file indicator
	 * @param repository repository contains file
	 * @param idApp application's id
	 * @return file contents
	 * @throws IOException I/O exception
	 */
	List<String[]> exploreDurationFile(String repository, String idApp) throws IOException;

	/**
	 * Explore connected users file indicator
	 * @param repository repository contains file
	 * @param idApp application's id
	 * @return file contents
	 * @throws IOException I/O exception
	 */
	List<String[]> exploreConnectedFile(String repository, String idApp) throws IOException;

	/**
	 * Populate applications
	 * @return list applications
	 */
	List<String> populateApplications();

	/**
	 * Populate trackers
	 * @return trakers
	 */
	List<String> populateTrackers();

	/**
	 * Populate repositorys
	 * @return list repositorys
	 */
	List<String> populateRepositorys();

}
