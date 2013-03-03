package tddd24.project.client;


import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;


public class WebStore implements EntryPoint {

	private HorizontalPanel topPanel = new HorizontalPanel();
	
	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private FlowPanel indexPanel = new FlowPanel();
	private FlowPanel adPanel = new FlowPanel();
	private FlowPanel mainPanel = new FlowPanel();
	
	//private List itemList = new List();	

	private TextArea label = new TextArea();
	private ProductServiceAsync productSvc;

	
	@Override
	public void onModuleLoad() {

		bodyPanel.setBorderWidth(1);
		/*bodyPanel.setWidth("50%");
		bodyPanel.setHeight("400px");
		mainPanel.setWidth("500px");
		mainPanel.setHeight("600px");
		indexPanel.setWidth("15%");
		indexPanel.setHeight("600px");
		adPanel.setWidth("15%");
		adPanel.setHeight("600px");*/

		topPanel.addStyleName("TopPanel");
		bodyPanel.addStyleName("Body");
		indexPanel.addStyleName("CornerPanel");
		mainPanel.addStyleName("MainPanel");
		adPanel.addStyleName("CornerPanel");

		bodyPanel.add(indexPanel);
		bodyPanel.add(mainPanel);
		bodyPanel.add(adPanel);
		
		bodyPanel.setCellWidth(indexPanel, "10%");
		bodyPanel.setCellWidth(mainPanel, "80%");
		bodyPanel.setCellWidth(adPanel, "10%");
		
		UpdateMainList();

		
		RootPanel.get("Top Panel").add(topPanel);
		RootPanel.get("Home Page").add(bodyPanel);
	}

	
	private void UpdateMainList()
	{
		if(productSvc == null){
			productSvc = GWT.create(ProductService.class);
		}
		
		AsyncCallback<ArrayList<Product>> callback = new AsyncCallback<ArrayList<Product>>() {
			public void onFailure(Throwable caught){
				
			}
			public void onSuccess(ArrayList<Product> result){
				mainPanel.clear();
				
				for(Product product : result)
				{
					DockPanel p = new DockPanel();
					
					Label nameLabel = new Label();
					nameLabel.setSize("200px", "20px");
					nameLabel.setText(product.getName());
					nameLabel.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);
					p.
					p.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);
					
					Label priceLabel = new Label();
					priceLabel.setSize("200px", "20px");
					priceLabel.setText(String.valueOf(product.getPrice()));
					//priceLabel.addStyleName("ProductLayout");
					
					Label descriptionLabel = new Label();
					descriptionLabel.setSize("100px", "150px");
					descriptionLabel.setText("Description test test test test test test test test test test test test test test test test test test test ");
					//descriptionLabel.addStyleName("ProductLayout");

					Label imageLabel = new Label();
					imageLabel.setSize("100px", "150px");
					imageLabel.setText("Image");
					//imageLabel.addStyleName("ProductLayout");
					
					p.add(nameLabel, DockPanel.NORTH);
					p.add(priceLabel, DockPanel.SOUTH);
					p.add(descriptionLabel, DockPanel.EAST);
					p.add(imageLabel, DockPanel.WEST);
					//p.setStylePrimaryName("ProductLayout");	
					//p.setBorderWidth(1);
					p.getElement().getStyle().setPaddingBottom(40, Style.Unit.PX);
					
					mainPanel.add(p);
					//p.addWest(new HTML(product.getDescription()), 10);
					//p.add(new HTML(product.getImage());
				}
			}
		};
		productSvc.getAll(callback);
	}
	
}
