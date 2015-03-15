# Hello World Tutorial for NIBs #

This tutorial will help you bootstrap a full blown Cocoa application without any AWT. It discusses autorelease pools, loading of NIB files etc.

## Main class to load NSApplication ##

Create a main class similar to

```
import org.rococoa.cocoa.foundation. NSAutoreleasePool

public class MainController {
	public static void main(String[] args) {
		final NSAutoreleasePool pool = NSAutoreleasePool.push();
		try {			
                        // This method also makes a connection to the window server and completes other initialization.
                        // Your program should invoke this method as one of the first statements in main();
                        // The NSApplication class sets up autorelease pools (instances of the NSAutoreleasePool class)
                        // during initialization and inside the event loop—specifically, within its initialization
                        // (or sharedApplication) and run methods.
			final NSApplication app = NSApplication.sharedApplication();
			WindowController w = new WindowController();
                        // Starts the main event loop. The loop continues until a stop: or terminate: message is
                        // received. Upon each iteration through the loop, the next available event
                        // from the window server is stored and then dispatched by sending it to NSApp using sendEvent:.
                        // The global application object uses autorelease pools in its run method.
			app.run();
		} finally {
			pool.drain();
		}
	}
}
```

Make sure to register this class with the key in the Info.plist dictionary of your application bundle in the Java section. Refer to http://developer.apple.com/mac/library/documentation/Java/Reference/Java_InfoplistRef/Articles/JavaDictionaryInfo.plistKeys.html for more information.

## Make sure no AWT thread is started ##

  * In the Info.plist of your application add  the `StartOnMainThread` flag like
```
       <key>Java</key>
       <dict>
           <key>StartOnMainThread</key>
           <true/>
       </dict>
```
The event processing on the AppKit thread should then work after invoking `NSApplication.run()` as expected.

## Design NIB file ##
Create a NIB file in _InterfaceBuilder_ with a `NSWindow` instance and a `NSButton`.

![http://pixel.recoil.org/rococoa/Nib.png](http://pixel.recoil.org/rococoa/Nib.png)

> Define a new outlet in the NIB file owner named `mainWindow` and connect it the the `NSWindow` instance. Define an action named `buttonClicked:` on the file owner and control drag from the `NSButton` instance to the file owner to connect the action. All outlets and actions in the NIB should have Objective-C syntax. Leave the file owner in the NIB file of type `NSObject` as is.

![http://pixel.recoil.org/rococoa/Outlets.png](http://pixel.recoil.org/rococoa/Outlets.png)
![http://pixel.recoil.org/rococoa/Connections.png](http://pixel.recoil.org/rococoa/Connections.png)

## Implement the controller class (aka file owner) ##
Implement the window controller to load your first NIB and become the file owner.

```
import org.rococoa.ID;
import org.rococoa.NSObject;
import org.rococoa.Rococoa;

public class WindowController {

    public WindowController() {
        String bundleName = "MyNib"
        // Load the NIB file and pass it our Rococoa proxy as the file owner.
        if(!NSBundle.loadNibNamed(bundleName, this.id())) {
            System.err.println("Couldn't load " + bundleName + ".nib");
            return;
        }
    }

    // Injected outlet from NIB
    private NSWindow window;
    
    // Called when loading NIB using NSBundle. NIB has a mainWindow outlet defined.
    public void setMainWindow(NSWindow mainWindow) {
        System.out.println("Outlet set to: " + mainWindow.title());
    }
    
    // NSButton in NIB has an action to the file owner named buttonClicked:    
    public void buttonClicked(ID sender) {
        System.out.println("Hello World from: " + sender);
    }
	
    /**
     * You need to keep a reference to the returned value for as long as it is
     * active. When it is GCd, it will release the Objective-C proxy.
     */
    private NSObject proxy;

    private ID id;

    public NSObject proxy() {
	 return this.proxy(NSObject.class);
    }

    public NSObject proxy(Class<? extends NSObject> type) {
        if(null == proxy) {
            proxy = Rococoa.proxy(this, type);
	}
	return proxy;
    }

    public org.rococoa.ID id() {
	 return this.id(NSObject.class);
    }

    public org.rococoa.ID id(Class<? extends NSObject> type) {
	if(null == id) {
            id = this.proxy(type).id();
	}
	return id;
    }
}
```

## Implementing a delegate ##

See also [Memory Management](Memory#Java_weak_references_and_Rococoa_proxies.md).

## Missing Rococoa Classes? ##

  * If you are looking in Rococoa for classes such as NSBundle, NSWindow etc., or for some of the methods used above, be aware that they do not come all bundled yet with Rococoa. The [JNAerator](http://code.google.com/p/jnaerator/) project helps in generating the necessary stubs for Obj-C classes.

  * A good part of the Foundation and AppKit classes can be found in the [Cyberduck](http://cyberduck.ch) source code repository ([AppKit](http://svn.cyberduck.ch/branches/rococoa/source/ch/cyberduck/ui/cocoa/application/) GPL, [Foundation](http://svn.cyberduck.ch/branches/rococoa/source/ch/cyberduck/ui/cocoa/foundation/), GPL). These are generated using JNAerator and edited to fix generation errors.

  * You might want to take a look at the state of the [Nativelibs4java](http://code.google.com/p/nativelibs4java/wiki/MacOSXFrameworks) project which aims to cover all major OS X frameworks with auto-generated classes.

  * See the ticket [Foundation and AppKit framework coverage	](http://code.google.com/p/rococoa/issues/detail?id=4).