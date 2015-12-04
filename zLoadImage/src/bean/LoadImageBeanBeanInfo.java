package bean;

import interfaces.ImageListener;

import java.awt.event.ActionEvent;
import java.beans.*;

/**
 * Created by mod on 12/3/15.
 */
public class LoadImageBeanBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] props = new PropertyDescriptor[0];
        try {
            props = new PropertyDescriptor[]{
                    new PropertyDescriptor("filename", LoadImageBean.class),
                    new PropertyDescriptor("maxCalls", LoadImageBean.class),
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return props;
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        MethodDescriptor[] props = new MethodDescriptor[0];
        try {
            props = new MethodDescriptor[]{
                    new MethodDescriptor(LoadImageBean.class.getMethod("start", ActionEvent.class))
            };
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return super.getMethodDescriptors();
        }
        return props;
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] props = new EventSetDescriptor[0];
        try {
            props = new EventSetDescriptor[]{
                    new EventSetDescriptor(LoadImageBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getEventSetDescriptors();
        }
        return props;
    }
}
