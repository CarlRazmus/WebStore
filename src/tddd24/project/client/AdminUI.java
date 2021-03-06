package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminUI extends VerticalPanel {

	private ListBox listBox;
	private FlexTable table;
	private boolean initiated = false;
	
	public AdminUI(FlowPanel mainPanel, WebStore webStore) {

		mainPanel.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		getProducts();
	}

	private void getProducts() {
		ProductServiceAsync productSvc = GWT.create(ProductService.class);

		AsyncCallback<ArrayList<Product>> callback = new AsyncCallback<ArrayList<Product>>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(ArrayList<Product> result) {
				if(!initiated){
					fillListBox(result);
					initiated  = true;
				}
				updateTable(result);
			}
		};

		productSvc.getProducts(null, callback);
	}

	protected void updateTable(ArrayList<Product> products) {
		//Table
		table.clear();
		Label productTableLabel = new Label("Product");
		Label inventoryTableLabel = new Label("Inventory");
		
		productTableLabel.setStyleName("productTableLabel");
		inventoryTableLabel.setStyleName("inventoryTableLabel");
		table.setWidget(0, 0, productTableLabel);
		table.setWidget(0, 1, inventoryTableLabel);
		
		int row = 1;
		for(Product p : products){
			table.setWidget(row, 0, new Label(p.getName()));
			table.setWidget(row, 1, new Label(Integer.toString(p.getInventory())));
			row++;
		}
	}

	private void fillListBox(ArrayList<Product> products)
	{
		listBox = new ListBox();
		table = new FlexTable();
		
		final TextBox amountBox = new TextBox();
		Button sendButton = new Button("Update Inventory");
		
		HorizontalPanel addInventoryPanel = new HorizontalPanel();
		addInventoryPanel.add(listBox);
		addInventoryPanel.add(amountBox);
		addInventoryPanel.add(sendButton);
		this.add(addInventoryPanel);
		this.add(table);
		
		
		//add products to listbox
		for(Product p : products)
		{
			listBox.addItem(p.getName());
		}
		
		//add clicklistener to button
		sendButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int productId = getProductId();
				int amount = Integer.parseInt(amountBox.getText());
				sendAddInventoryRequest(productId, amount);
			}
		});
	}

	private int getProductId() {
		return listBox.getSelectedIndex()+1;
	}
	
	protected void sendAddInventoryRequest(int productId, int amount) {
		ProductServiceAsync productSvc = GWT.create(ProductService.class);

		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(Void result) {
				getProducts();
			}
		};

		productSvc.addInventory(productId, amount, callback);
	}
}
