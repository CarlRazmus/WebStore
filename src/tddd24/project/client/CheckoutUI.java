package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheckoutUI extends VerticalPanel{
	
	public CheckoutUI(FlowPanel mainPanel, final WebStore webStore) {
		mainPanel.setWidth("100%");
		
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		FlowPanel productPreviewPanel = new FlowPanel();
		
		final ArrayList<Product> order = new ArrayList<Product>();
		for(Product p : webStore.getShoppingCart().GetProducts()){
			order.add(p);
		}
		ProductMainPanelUI.SetFlowPanelPreviewData(productPreviewPanel, order);
		Label previewLabel = new Label("Products Preview");
		previewLabel.getElement().getStyle().setPadding(10, Style.Unit.PX);
		previewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);// .getElement().getStyle().setV
		Button button = new Button("Go to Payment");
		button.getElement().getStyle().setPadding(20, Style.Unit.PX);

		button.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	              webStore.confirmOrder(order);
	          }
	      });
		
		
		this.add(previewLabel);
		this.add(productPreviewPanel);
		
		/* User Fill-In information fields  */ 
		
		HorizontalPanel panel = new HorizontalPanel();
		Label label = new Label("First name");
		TextBox textBox = new TextBox();
		label.setStyleName("PaymentDetailsLabel");
		panel.add(label);
		panel.add(textBox);
		this.add(panel);
		

		HorizontalPanel panel2 = new HorizontalPanel();
		Label label2 = new Label("Last name");
		TextBox textBox2 = new TextBox();
		label2.setStyleName("PaymentDetailsLabel");
		panel2.add(label2);
		panel2.add(textBox2);
		this.add(panel2);
		

		HorizontalPanel panel3 = new HorizontalPanel();
		Label label3 = new Label("Phone Number");
		TextBox textBox3 = new TextBox();
		label3.setStyleName("PaymentDetailsLabel");
		panel3.add(label3);
		panel3.add(textBox3);
		this.add(panel3);
		

		HorizontalPanel panel4 = new HorizontalPanel();
		Label label4 = new Label("Adress");
		TextBox textBox4 = new TextBox();
		label4.setStyleName("PaymentDetailsLabel");
		panel4.add(label4);
		panel4.add(textBox4);
		this.add(panel4);
		

		HorizontalPanel panel5 = new HorizontalPanel();
		Label label5 = new Label("Zip Code");
		TextBox textBox5 = new TextBox();
		label5.setStyleName("PaymentDetailsLabel");
		panel5.add(label5);
		panel5.add(textBox5);
		this.add(panel5);

		//this.setCellHorizontalAlignment(productPreviewPanel, Style.VerticalAlign.MIDDLE);
		
		
		this.add(button);
	}
}
