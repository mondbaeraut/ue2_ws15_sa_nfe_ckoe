package bean;

import interfaces.ImageEvent;
import interfaces.ImageListener;

import java.beans.*;

/**
 * Created by mod on 12/3/15.
 */
public class ErodeBeanBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] props = new PropertyDescriptor[0];
        try {
            props = new PropertyDescriptor[]{
                    new PropertyDescriptor("radius", ErodeBean.class),
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
                    new MethodDescriptor(ErodeBean.class.getMethod("onImage", ImageEvent.class))
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
                    new EventSetDescriptor(ErodeBean.class, "image", ImageListener.class, "onImage")
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return super.getEventSetDescriptors();
        }
        return props;
    }
}
