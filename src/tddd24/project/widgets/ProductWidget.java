package tddd24.project.widgets;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;

public class ProductWidget extends DockPanel{
	
	public ProductWidget(String name, String price, String description) {
		
		add(new Label(name), DockPanel.NORTH);
		add(new Label(price + " kr"), DockPanel.SOUTH);
		Label descriptionLabel = new Label(description);
		descriptionLabel.setWidth("100px");
		add(descriptionLabel, DockPanel.EAST);
		
		Label imageLabel = new Label();
		imageLabel.setSize("100px", "100px");
		imageLabel.setText("NO IMAGE");
		imageLabel.addStyleName("imageLabel");
		add(imageLabel, DockPanel.WEST);
		setStylePrimaryName("somestyle");
	}
}
