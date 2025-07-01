package com.example.app.base.ui.view;

import com.vaadin.flow.component.HasElement;


public class ElementUtils {
	
	/**
	 * Do not propagate key down (incl. ENTER-key) events to outside of that particular element. Direct attached
	 * Key-Pressed listener's are still working.
	 * @param c HasElement
	 */
	public static void stopKeyDownEventPropagation( HasElement c ) {
		if( c == null )
			return;
		
		c.getElement().addEventListener( "keydown", event -> {} ).stopPropagation();
	}
	
	/**
	 * Stop propagating touch- and mouse-down events in order to avoid automatic closing of menus.
	 * TODO: review if something strange happens while NOT inside ob menus/submenus
	 */
	public static void stopInputEventPropagation( HasElement c ) {
		if( c == null )
			return;
		
		c.getElement().addEventListener( "mouseup", event -> {} ).stopPropagation();
		c.getElement().addEventListener( "click", event -> {} ).stopPropagation();
		
		// TODO: test this separately:
//		c.getElement().addEventListener( "touchend", event -> {} ).stopPropagation();
		c.getElement().addEventListener( "touchstart", event -> {} ).stopPropagation();
		
	}
	
}
