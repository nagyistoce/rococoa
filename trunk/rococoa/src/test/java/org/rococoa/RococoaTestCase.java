/*
 * Copyright 2007, 2008 Duncan McGregor
 * 
 * This file is part of Rococoa, a library to allow Java to talk to Cocoa.
 * 
 * Rococoa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Rococoa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rococoa.  If not, see <http://www.gnu.org/licenses/>.
 */
 
package org.rococoa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import org.rococoa.cocoa.NSAutoreleasePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 * A TestCase which runs tests with an autorelease pool in place.
 * 
 * @author duncan
 */
@SuppressWarnings("nls")
public abstract class RococoaTestCase extends TestCase {
    
    // stress our memory management
    public static boolean gcAfterTest = true;

    static {
    	initializeLogging();
    };

    protected static Logger logging = LoggerFactory.getLogger("org.rococoa.RococoaTestCase");

    public static void initializeLogging() {
        try {
            InputStream is = null;
            try {
                is = RococoaTestCase.class.getResourceAsStream("/test-logging.properties");
                if (is == null)
                    throw new FileNotFoundException("Cannot find test-logging.properties");
                LogManager.getLogManager().readConfiguration(is);
            } finally {
                if (is != null)
                    is.close();
            }
        } catch (IOException x) {
            throw new RuntimeException("Could not initialize logging", x);
            
        }
    }
    
    protected NSAutoreleasePool pool;
    
    @Override
    public void runBare() throws Throwable {
        preSetup();
        try {
            super.runBare();
        } finally {
            postTeardown();
        }
    }

    protected void preSetup() {
        logging.info("Starting test {}.{}", new Object[] {getClass().getName(), getName()});
        pool = NSAutoreleasePool.new_();
    }

    protected void postTeardown() {
        logging.info("Ending test {}", getName());
        if (gcAfterTest)
            gc();
        pool.release();
        logging.info("Ended test {}", getName());
    }

    private void gc() {
        System.gc();
        System.gc();
        System.runFinalization();
    }

}
