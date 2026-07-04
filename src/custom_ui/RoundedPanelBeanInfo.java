package custom_ui;

import java.beans.*;

public class RoundedPanelBeanInfo extends SimpleBeanInfo {

    @Override
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor bd = new BeanDescriptor(RoundedPanel.class);
        bd.setDisplayName("Rounded Panel");
        bd.setShortDescription("A JPanel with rounded corners");
        return bd;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor cornerRadius = new PropertyDescriptor(
                    "cornerRadius", RoundedPanel.class);
            cornerRadius.setDisplayName("cornerRadius");
            cornerRadius.setShortDescription("Radius of the rounded corners in pixels");

            PropertyDescriptor background = new PropertyDescriptor(
                    "bac   ckground", RoundedPanel.class);
            background.setDisplayName("background");
            background.setShortDescription("Background fill color of the panel");

            PropertyDescriptor foreground = new PropertyDescriptor(
                    "foreground", RoundedPanel.class);
            foreground.setDisplayName("foreground");
            foreground.setShortDescription("Border color of the panel");

            return new PropertyDescriptor[]{
                cornerRadius, background, foreground
            };
        } catch (IntrospectionException e) {
            return null;
        }
    }
}