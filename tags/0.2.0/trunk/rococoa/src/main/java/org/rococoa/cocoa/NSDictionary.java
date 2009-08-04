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
 
package org.rococoa.cocoa;

import org.rococoa.ID;
import org.rococoa.NSClass;
import org.rococoa.NSObject;
import org.rococoa.Rococoa;

public interface NSDictionary extends NSObject {
    public static final _Class CLASS = Rococoa.createClass("NSDictionary", _Class.class); //$NON-NLS-1$

    public interface _Class extends NSClass {
        NSDictionary dictionaryWithObjects_forKeys(NSArray objects, NSArray keys);
        NSDictionary dictionaryWithObjectsAndKeys(NSObject...objects);
    }
    
    ID objectForKey(ID aKey);
    int count();    
}