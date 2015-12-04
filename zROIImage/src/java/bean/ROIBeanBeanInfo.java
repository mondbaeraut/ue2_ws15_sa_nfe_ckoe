package bean;

import interfaces.ImageEvent;
import interfaces.ImageListener;

import java.beans.*;

/**
 * Created by mod on 12/3/15.
 */
public class ROIBeanBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] props = new PropertyDescriptor[0];
        try {
            props = new PropertyDescriptor[]{
                    new PropertyDescriptor("roiX", ROIBean.class),
                    new PropertyDescriptor("roiY", ROIBean.class),
                    new PropertyDescriptor("height", ROIBean.class),
                    new PropertyDescriptor("width", ROIBean.class),
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
                    new MethodDescriptor(ROIBean.class.getMethod("onImage", ImageEvent.class))
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
                    new EventSetDescriptor(ROIBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getEventSetDescriptors();
        }
        return props;
    }
}
