package tddd24.project.client;

import java.util.ArrayList;

import tddd24.project.widgets.ProductWidget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class ProductMainPanelUI {

	
	static void SetFlowPanelPreviewData(FlowPanel mainPanel, ArrayList<Product> products) {
		
		mainPanel.clear();
		Label costPreviewLabel = new Label();
		costPreviewLabel.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
		costPreviewLabel.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
		mainPanel.add(costPreviewLabel);
		costPreviewLabel.setText(String.valueOf(AddProducts(mainPanel, products)));
	}
	
	static int AddProducts(FlowPanel mainPanel, ArrayList<Product> products)
	{
		int cost = 0;
		mainPanel.clear();
		for (Product product : products) {
			ProductWidget productWidget = new ProductWidget(product);
			mainPanel.add(productWidget);
			cost += product.getPrice() * product.getInCurrentCart();
		}
		return cost;
	}
}
