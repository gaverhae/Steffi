/*******************************************************************************
 * Copyright (c) 2014 EURA NOVA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Aldemar Reynaga - initial API and implementation
 *     Salim Jouili - initial API and implementation
 ******************************************************************************/
package com.tinkerpop.blueprints.impls.imgraph;



import com.imgraph.Main;
import com.imgraph.common.Configuration;
import com.imgraph.networking.ClusterConfigManager;
import com.imgraph.networking.NodeServer;
import com.imgraph.storage.CacheContainer;

/**
 * @author Aldemar Reynaga
 * Main functions to control and access the Imgraph database
 */
public class ImgraphFactory {
	
	public static void startInfinispan() {
		CacheContainer.getCacheContainer().start();
		CacheContainer.getCellCache().start();
	}
	
	public static void stopInfinispan() {
		CacheContainer.getCellCache().stop();
		CacheContainer.getCacheContainer().stop();
	}
	
	public static void startImgraphEngine() throws Exception {
		startImgraphEngine(null);
	}
	
	public static void startImgraphEngine(String configFile) throws Exception {
		
		if (configFile != null)
			Configuration.loadProperties(configFile);
		System.setProperty("java.net.preferIPv4Stack" , "true");
		CacheContainer.getCacheContainer().start();
		CacheContainer.getCellCache().start();
		new Thread(new NodeServer()).start();
		System.out.println("Imgraph engine started");
	}
	
	public static void stopImgraphEngine() {
		Main.sendStopMessage(Configuration.getProperty(Configuration.Key.NODE_PORT));
		System.out.println("Imgraph engine stopped");
	}
	
	public static ImgraphGraph getInstance() {
		return ImgraphGraph.getInstance();
	}
	
	
	public static void configureCluster() {
		ClusterConfigManager configManager = new ClusterConfigManager();
		try {
			configManager.initialize();
			System.out.println("Configuration successfuly executed");
		} catch (Exception x) {
			x.printStackTrace();
		} finally {
			configManager.closeClientThreads();
			
		}
	}
}
