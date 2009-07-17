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
 
package org.rococoa.cocoa.foundation;

import org.rococoa.ID;
import org.rococoa.NSClass;
import org.rococoa.NSObject;
import org.rococoa.Rococoa;
import org.rococoa.Selector;


public interface NSNotificationCenter extends NSObject {
    public static final _Class CLASS = Rococoa.createClass("NSNotificationCenter", _Class.class); //$NON-NLS-1$
    public interface _Class extends NSClass {
        public NSNotificationCenter defaultCenter();
    }
    
    @Deprecated
    void addObserver_selector_name_object(ID notificationObserver,
            Selector notificationSelector,
            String notificationName,
            NSObject notificationSender);

    void addObserver_selector_name_object(NSObject notificationObserver,
            Selector notificationSelector,
            String notificationName,
            NSObject notificationSender);

    void postNotification(NSNotification notification);

}