package tddd24.project.client;

import java.util.ArrayList;

import tddd24.project.controllers.ProductDragController;
import tddd24.project.controllers.VerticalPanelDropController;
import tddd24.project.widgets.ProductWidget;
import tddd24.project.widgets.ShoppingCartWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
	private Button checkoutShoppingCartButton = new Button("Checkout");
	
	private VerticalPanel shoppingCartPanel = new VerticalPanel();
	private ShoppingCartWidget shoppingCart = new ShoppingCartWidget();
	private FlowPanel mainPanel = new FlowPanel();
	
	private Label headerLabel = new Label("WebStore");
	private TextBox userNameBox = new TextBox();
	private PasswordTextBox passwordBox = new PasswordTextBox();
	
	private Label userNameLabel = new Label("User Name:");
	private Label passwordLabel = new Label("Password:");
	private Button signInOutButton = new Button();
	private ClickHandler signInListener;
	private ClickHandler signOutListener;
	private HandlerRegistration signOutHandlerReg;
	private HandlerRegistration signInHandlerReg;
	
	//DND
	private ProductDragController productDragController;
	private VerticalPanelDropController dropController;
	private Product draggedProduct;
	private ProductServiceAsync productSvc;

	@Override
	public void onModuleLoad() {
		
		//Header layout
		headerLabel.setStyleName("headerLabel");
		topPanel.add(headerLabel);
		topPanel.add(userNameBox);
		topPanel.add(passwordBox);
		topPanel.add(userNameLabel);
		topPanel.add(passwordLabel);
		topPanel.add(signInOutButton);

		topPanel.setWidgetTopBottom(headerLabel, 0, Unit.PX, 0, Unit.PX);
		
		topPanel.setWidgetRightWidth(userNameBox, 160.0, Unit.PX, 150.0, Unit.PX);
		topPanel.setWidgetTopHeight(userNameBox, 20.0, Unit.PX, 30.0, Unit.PX);
		
		topPanel.setWidgetRightWidth(passwordBox, 0.0, Unit.PX, 150.0, Unit.PX);
		topPanel.setWidgetTopHeight(passwordBox, 20.0, Unit.PX, 30.0, Unit.PX);
		
		topPanel.setWidgetRightWidth(userNameLabel, 160.0, Unit.PX, 147.0, Unit.PX);
		topPanel.setWidgetTopHeight(userNameLabel, 0.0, Unit.PX, 30.0, Unit.PX);
		
		topPanel.setWidgetRightWidth(passwordLabel, 0.0, Unit.PX, 147.0, Unit.PX);
		topPanel.setWidgetTopHeight(passwordLabel, 0.0, Unit.PX, 30.0, Unit.PX);
		
		topPanel.setWidgetRightWidth(signInOutButton, 110.0, Unit.PX, 100.0, Unit.PX);
		topPanel.setWidgetTopHeight(signInOutButton, 60.0, Unit.PX, 30.0, Unit.PX);
				
		
		//Sign in/out button handling
		signInListener = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String userName = userNameBox.getText();
				String password = passwordBox.getText();
				signIn(userName, password);
			}
		};
		
		signOutListener = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				changeToSignedOutUI();
			}
		};
		
		changeToSignedOutUI();
		
		bodyPanel.setBorderWidth(1);

		topPanel.addStyleName("TopPanel");
		bodyPanel.addStyleName("Body");
		categoryPanel.addStyleName("CornerPanel");
		mainPanel.addStyleName("MainPanel");
		shoppingCartPanel.addStyleName("CornerPanel");
		
		checkoutShoppingCartButton.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	              Checkout();
	          }
	      });
		shoppingCart.add(checkoutShoppingCartButton);

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
		
		//Category Layout
		initiateCategories();
		categoryPanel.add(tree);
		
		shoppingCartPanel.add(shoppingCart);
		bodyPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		bodyPanel.add(categoryPanel);
		bodyPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bodyPanel.add(mainPanel);
		bodyPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
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
	
	private void Checkout()
	{
		mainPanel.clear();
		VerticalPanel mainPanelUI = new VerticalPanel();
		FlowPanel productPreviewPanel = new FlowPanel();
		final ArrayList<Product> order = shoppingCart.GetProducts();
		ProductMainPanelUI.SetFlowPanelPreviewData(productPreviewPanel, order);
		Label previewLabel = new Label("Products Preview");
		previewLabel.getElement().getStyle().setPadding(10, Style.Unit.PX);
		previewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);// .getElement().getStyle().setV
		Button button = new Button("Go to Payment");
		button.getElement().getStyle().setPadding(20, Style.Unit.PX);
		
		button.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	              //go to payment accepted view
	          }
	      });
		
		
		mainPanelUI.add(previewLabel);
		mainPanelUI.add(productPreviewPanel);
		mainPanelUI.add(button);
		mainPanel.add(mainPanelUI);
		
	}

	

	public void UpdateMainList(ArrayList<Product> products) {
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
	
	protected void signIn(String userName, String password) {
		if (productSvc == null) {
			productSvc = GWT.create(ProductService.class);
		}

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(Boolean valid) {
				if(valid)
					changeToSignedInUI();
			}
		};
		productSvc.verifyAccount(userName, password, callback);
	}
	
	private void changeToSignedInUI()
	{
		//Hide sign in elements
		userNameBox.setVisible(false);
		userNameLabel.setVisible(false);
		passwordBox.setVisible(false);
		passwordLabel.setVisible(false);
		
		//Change to sign out elements
		signInOutButton.setText("Sign Out");
		if(signInHandlerReg != null)
			signInHandlerReg.removeHandler();
		signOutHandlerReg = signInOutButton.addClickHandler(signOutListener);
		
	}
	
	protected void changeToSignedOutUI() {
		
		//Show sign in elements
		userNameBox.setVisible(true);
		userNameLabel.setVisible(true);
		passwordBox.setVisible(true);
		passwordLabel.setVisible(true);
		
		//change to sign in elements
		signInOutButton.setText("Sign In");
		if(signOutHandlerReg != null)
			signOutHandlerReg.removeHandler();
		signInHandlerReg = signInOutButton.addClickHandler(signInListener);
	}
	
	protected void confirmOrder(ArrayList<Product> order) {
		if (productSvc == null) {
			productSvc = GWT.create(ProductService.class);
		}

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(Boolean valid) {
				if(valid)
					headerLabel.setText("success");
				else
					headerLabel.setText("fail");
			}
		};
		productSvc.confirmOrder(order, callback);
	}
}
