package org.rococoa.internal;


import org.rococoa.ID;
import org.rococoa.Selector;

import com.sun.jna.Library;
import com.sun.jna.Structure;

/**
 * JNA Library for special message send calls, called and marshalled specially.
 */
public interface MsgSendLibrary extends Library {                
    // This doesn't exist in the library, but is synthesised by msgSendHandler
    Object syntheticSendMessage(Class<?> returnType, ID receiver, Selector selector,  Object... args);
    
    // We don't call these directly, but through syntheticSendMessage
    Object objc_msgSend(ID receiver, Selector selector, Object... args);        
    Structure objc_msgSend_stret(ID receiver, Selector selector, Object... args);         
}