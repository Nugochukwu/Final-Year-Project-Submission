/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom_ui;
import java.beans.*;
/**
 *
 * @author Ugochukwu Nwodo
 */
public class CustomTitleBarBeanInfo  extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[]{
                make("titleText",         "Title text"),
                make("barColor",          "Bar background color"),
                make("titleColor",        "Title and button icon color"),
                make("closeNormalColor",  "Close button default color"),
                make("closeHoverColor",   "Close button hover color"),
                make("minimizeNormalColor", "Minimize button default color"),
                make("minimizeHoverColor",  "Minimize button hover color"),
                make("titleFont",         "Title font"),
            };
        } catch (IntrospectionException e) {
            return null;
        }
    }

    private PropertyDescriptor make(String prop, String display)
            throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(prop, CustomTitleBar.class);
        pd.setDisplayName(display);
        return pd;
    }
}
