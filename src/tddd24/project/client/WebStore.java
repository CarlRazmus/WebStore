package tddd24.project.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class WebStore implements EntryPoint {

	private Label label = new Label("test");
	ProductServiceAsync productSvc; 
	
	@Override
	public void onModuleLoad() {
		RootPanel.get("HomePageBody").add(label);
		
		   // Initialize the service proxy.
	    if (productSvc == null) {
	      productSvc = GWT.create(ProductService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Void> callback2 = new AsyncCallback<Void>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(Void result) {
	        label.setText("success");
	      }
	    };

	    // Make the call to the stock price service.
	    productSvc.addProduct("football", 123, callback2);
	    
	    // Set up the callback object.
	    AsyncCallback<ArrayList<Product>> callback = new AsyncCallback<ArrayList<Product>>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(ArrayList<Product> result) {
	        label.setText("success");
	      }
	    };

	    // Make the call to the stock price service.
	    productSvc.getAll(callback);
	    
	    

	}
}
