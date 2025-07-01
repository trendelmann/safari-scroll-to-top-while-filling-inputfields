package com.example.app.base.ui.view;

import jakarta.annotation.security.PermitAll;

import com.example.app.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.HasValueChangeMode;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;


@Route("safari-issue")
@PageTitle("Safari Issue")
@Menu(order = 0, title = "Show Safari Issue")
@PermitAll // When security is enabled, allow all authenticated users
public class SafariIssueView extends Div {
	
	private static final String issuesDesc = """
<div>
<h3>Description</h3>
We use the Vaadin Dialog to create kind of a resizable <code>Window</code> with a title bar and a max/min button
together with a close button to allow users to control that like a Window in Microsoft Windows.
<h3>Issue</h3>
There is now a problem, if it gets opened by a Safari Browser. There the window flickers and the form itself scrolls
always back to the top of the form, if a user interacts with the input fields.
""";
	
	public SafariIssueView() {
		setSizeFull();
		addClassNames( LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
					   LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);
		
		add(new ViewToolbar( "Safari Issue" ));
		add(new Html(issuesDesc));
		
		var openDialogButton = new Button("Open Dialog (bad!)...", e -> openNativeDialogWithDateFieldBad());
		openDialogButton.setMaxWidth( "250px" );
		add( openDialogButton );
		
		openDialogButton = new Button("Open Dialog (good?)...", e -> openNativeDialogWithDateFieldGood());
		openDialogButton.setMaxWidth( "250px" );
		add( openDialogButton );

	}
	
	private void openNativeDialogWithDateFieldBad() {
		var nativeDialog = new Dialog();
		nativeDialog.setHeaderTitle( "Try to provide input, please" );
		nativeDialog.setMaxWidth( "400px" );
		nativeDialog.setMaxHeight( "250px" );
		nativeDialog.setModal( true );
		nativeDialog.setCloseOnOutsideClick( false );		// !!! close only per custom button
		nativeDialog.setDraggable( true );
		
		nativeDialog.add( new Scroller( buildSafariJumpingContent() ) );
		nativeDialog.open();
	}
	
	private void openNativeDialogWithDateFieldGood() {
		var nativeDialog = new Dialog();
		nativeDialog.setHeaderTitle( "Provide input, please" );
		nativeDialog.setMaxWidth( "400px" );
		nativeDialog.setMaxHeight( "250px" );
		nativeDialog.setModal( true );
		nativeDialog.setCloseOnOutsideClick( false );		// !!! close only per custom button
		nativeDialog.setDraggable( true );
		
		nativeDialog.add( new Scroller( buildNormalContent() ) );
		nativeDialog.open();
	}
	
	Component buildSafariJumpingContent() {
		var content = new Div();
		content.addClassName( "flexform" );
		content.add( new Html(
				"""
						<div>
						<h4>Problematic input form</h4>
						There the window flickers and the form itself scrolls
						always back to the top of the form, if you interacts with the input fields.
						""" ) );
		var textArea = new TextArea();
		textArea.addClassName( "flexformline" );
		
		textArea.setWidthFull();
		textArea.setValueChangeMode( ValueChangeMode.LAZY );
		textArea.setValueChangeTimeout( HasValueChangeMode.DEFAULT_CHANGE_TIMEOUT );
		// TODO: this seems to block other keys like ESC:
// 		when still in focus, ESC doesn't close a window/editor
//		maybe there's a way to filter key down events?
		ElementUtils.stopKeyDownEventPropagation( this );
		ElementUtils.stopInputEventPropagation( this );
		
		textArea.setMinRows( 3 );
		
		content.add( textArea );
		var dp = new DatePicker();
		dp.addClassName( "flexformline" );
		content.add( dp );
		return content;
	}
	
	Component buildNormalContent() {
		var content = new VerticalLayout();
		content.addClassName( "flexform" );
		content.add( new Html(
				"""
						<div>
						<h4>Fine input form</h4>
						There the window stays and the form itself did not scroll
						always back to the top of the form, if you interacts with the input fields.
						""" ) );
		
		var textArea = new TextArea();
		textArea.addClassName( "flexformline" );
		content.add( textArea );
		
		return content;
	}
}
