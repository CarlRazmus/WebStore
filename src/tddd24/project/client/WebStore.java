package tddd24.project.client;

import java.util.ArrayList;

import tddd24.project.controllers.ProductDragController;
import tddd24.project.controllers.VerticalPanelDropController;
import tddd24.project.widgets.ProductWidget;
import tddd24.project.widgets.ShoppingCartWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WebStore implements EntryPoint {

	private LayoutPanel topPanel = new LayoutPanel();
	private HorizontalPanel bodyPanel = new HorizontalPanel();
	private VerticalPanel categoryPanel = new VerticalPanel();
	private Tree tree = new Tree(); // Tree that shows the categories
	
	private VerticalPanel shoppingCartPanel = new VerticalPanel();
	private ShoppingCartWidget shoppingCart = new ShoppingCartWidget();
	private FlowPanel mainPanel = new FlowPanel();
	
	private Label headerLabel = new Label("WebStore");
	private TextBox userNameBox = new TextBox();
	private PasswordTextBox passwordBox = new PasswordTextBox();
	
	private Label userNameLabel = new Label("User Name:");
	private Label passwordLabel = new Label("Password:");

	//DND
	private ProductDragController productDragController;
	private VerticalPanelDropController dropController;
	private Product draggedProduct;
	private ProductServiceAsync productSvc;

	@Override
	public void onModuleLoad() {
		//header layout
		headerLabel.setStyleName("headerLabel");
		topPanel.add(headerLabel);
		topPanel.add(userNameBox);
		topPanel.add(passwordBox);
		topPanel.add(userNameLabel);
		topPanel.add(passwordLabel);
//		userNameBox.setStyleName("userNameBox");
//		userNameLabel.setStylePrimaryName("userNameLabel");
//		passwordBox.setStyleName("passwordBox");
//		passwordLabel.setStyleName("passwordLabel");
		topPanel.setWidgetTopBottom(headerLabel, 0, Unit.PX, 0, Unit.PX);
		topPanel.setWidgetRightWidth(userNameBox, 160.0, Unit.PX, 150.0, Unit.PX);
		topPanel.setWidgetTopHeight(userNameBox, 20.0, Unit.PX, 30.0, Unit.PX);
		topPanel.setWidgetRightWidth(passwordBox, 0.0, Unit.PX, 150.0, Unit.PX);
		topPanel.setWidgetTopHeight(passwordBox, 20.0, Unit.PX, 30.0, Unit.PX);
		topPanel.setWidgetRightWidth(userNameLabel, 160.0, Unit.PX, 147.0, Unit.PX);
		topPanel.setWidgetTopHeight(userNameLabel, 0.0, Unit.PX, 30.0, Unit.PX);
		topPanel.setWidgetRightWidth(passwordLabel, 0.0, Unit.PX, 147.0, Unit.PX);
		topPanel.setWidgetTopHeight(passwordLabel, 0.0, Unit.PX, 30.0, Unit.PX);
		
		
		bodyPanel.setBorderWidth(1);

		topPanel.addStyleName("TopPanel");
		bodyPanel.addStyleName("Body");
		categoryPanel.addStyleName("CornerPanel");
		mainPanel.addStyleName("MainPanel");
		shoppingCartPanel.addStyleName("CornerPanel");

		// add selection handler to tree and show categories
		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				String filter = ((Label)item.getWidget()).getText();
				if (filter.equals("Alla Kategorier"))
					filter = null;
				getProducts(filter);
			}
		});
		initiateCategories();
		categoryPanel.add(tree);
		
		shoppingCartPanel.add(shoppingCart);
		bodyPanel.add(categoryPanel);
		bodyPanel.add(mainPanel);
		bodyPanel.add(shoppingCartPanel);

		bodyPanel.setCellWidth(categoryPanel, "10%");
		bodyPanel.setCellWidth(mainPanel, "70%");
		bodyPanel.setCellWidth(shoppingCartPanel, "20%");
		
		//Drag and drop initialization
		productDragController = new ProductDragController(RootPanel.get("Home Page"), false, this);
		dropController = new VerticalPanelDropController(shoppingCart, this);
		productDragController.setBehaviorDragProxy(true);
		productDragController.registerDropController(dropController);
		
		RootPanel.get("Home Page").getElement().getStyle().setPosition(Position.RELATIVE);
		
		getProducts(null);

		RootPanel.get("Top Panel").add(topPanel);
		RootPanel.get("Home Page").add(bodyPanel);
	}

	private void UpdateMainList(ArrayList<Product> products) {
		mainPanel.clear();
		for (Product product : products) {
			ProductWidget productWidget = new ProductWidget(product, this);
			productWidget.setDraggable(productDragController);
			mainPanel.add(productWidget);
		}
	}

	// gets categories from server and updates category list.
	public void initiateCategories() {
		if (productSvc == null) {
			productSvc = GWT.create(ProductService.class);
		}

		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(ArrayList<String> result) {
				updateCategories(result);
			}
		};
		productSvc.getAllCategorys(callback);
	}

	public void updateCategories(ArrayList<String> categories) {
		tree.clear();
		
		TreeItem treeItem = new TreeItem(new Label("Alla Kategorier"));
		for (String name : categories) {
			treeItem.addItem(new Label(name));
		}
		tree.addItem(treeItem);
		treeItem.setState(true);
	}

	/**
	 * Get products from sercer
	 * 
	 * @param filter
	 *            after category or null if all products should be returned.
	 */
	private void getProducts(String filter) {
		
		if (productSvc == null) {
			productSvc = GWT.create(ProductService.class);
		}

		AsyncCallback<ArrayList<Product>> callback = new AsyncCallback<ArrayList<Product>>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(ArrayList<Product> result) {
				UpdateMainList(result);
			}
		};
		productSvc.getProducts(filter, callback);
	}

	public void setCurrentProduct(Product product) {
		draggedProduct = product;
	}

	public Product getDraggedProduct() {
		return draggedProduct;
	}

}
