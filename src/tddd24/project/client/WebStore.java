package tddd24.project.client;

import java.util.ArrayList;

import tddd24.project.widgets.ProductWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class WebStore implements EntryPoint {

	private HorizontalPanel topPanel = new HorizontalPanel();

	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private FlowPanel indexPanel = new FlowPanel();
	private FlowPanel adPanel = new FlowPanel();
	private FlowPanel mainPanel = new FlowPanel();

	// private List itemList = new List();

	private ProductServiceAsync productSvc;

	@Override
	public void onModuleLoad() {

		bodyPanel.setBorderWidth(1);
		/*
		 * bodyPanel.setWidth("50%"); bodyPanel.setHeight("400px");
		 * mainPanel.setWidth("500px"); mainPanel.setHeight("600px");
		 * indexPanel.setWidth("15%"); indexPanel.setHeight("600px");
		 * adPanel.setWidth("15%"); adPanel.setHeight("600px");
		 */

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

	private void UpdateMainList() {
		if (productSvc == null) {
			productSvc = GWT.create(ProductService.class);
		}

		AsyncCallback<ArrayList<Product>> callback = new AsyncCallback<ArrayList<Product>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(ArrayList<Product> result) {
				mainPanel.clear();

				for (Product product : result) {
					ProductWidget productWidget = new ProductWidget(
							product.getName(),
							String.valueOf(product.getPrice()),
							"Description test test test test test test test test test");
					mainPanel.add(productWidget);
				}
			}
		};
		productSvc.getAll(callback);
	}
}
