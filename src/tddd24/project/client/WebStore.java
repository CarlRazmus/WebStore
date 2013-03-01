package tddd24.project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class WebStore implements EntryPoint {

	FlowPanel flowPanel = new FlowPanel();
	FlowPanel horPanel;
	Label label;
	Button button;
	
	@Override
	public void onModuleLoad() {
		flowPanel.setSize("500px", "500px");
		flowPanel.addStyleName("somestyle");
		for(int i = 0; i < 10; i++)
			flowPanel.add(createProductPanel());
		RootPanel.get("HomePageBody").add(flowPanel);
	}
	
	private Widget createProductPanel(){
		horPanel = new FlowPanel();
		//horPanel.addStyleName("somestyle");
//		horPanel.add(label);
	//	horPanel.add(button);
		return horPanel;
	}
}
