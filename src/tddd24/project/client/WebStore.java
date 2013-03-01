package tddd24.project.client;


import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WebStore implements EntryPoint {

	private HorizontalPanel topPanel = new HorizontalPanel();
	
	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private VerticalPanel indexPanel = new VerticalPanel();
	private VerticalPanel adPanel = new VerticalPanel();
	private FlowPanel mainPanel = new FlowPanel();
	
	//private List itemList = new List();	
	
	@Override
	public void onModuleLoad() {
		
		topPanel.setStylePrimaryName("TopPanel");

	    Image image = new Image("resources/Derp.png");
		mainPanel.add(image);

		bodyPanel.setBorderWidth(1);
		bodyPanel.setWidth("100%");
		bodyPanel.setHeight("400px");

		bodyPanel.add(indexPanel);
		bodyPanel.add(mainPanel);
		bodyPanel.add(adPanel);
		
		mainPanel.setWidth("500px");
		mainPanel.setHeight("600px");
		
		indexPanel.setWidth("100px");
		indexPanel.setHeight("600px");
		
		adPanel.setWidth("100px");
		adPanel.setHeight("600px");

		
		RootPanel.get("Top Panel").add(topPanel);
		RootPanel.get("Home Page").add(bodyPanel);
		
	}
	
	private void ReadFromDatabase()
	{
		
	}
	
	
	
	private void addItemsToIndexList()
	{
		
	}
	
	private void addItemToIndexList(Product item)
	{
		//indexPanel.add(new Label(item.category));
	}

}
