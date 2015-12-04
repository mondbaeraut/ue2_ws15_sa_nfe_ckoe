package bean;

import interfaces.ImageEvent;

import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by mod on 11/30/15.
 */
public class DisplayimageBeanBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] props = {
        };
        return props;
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        MethodDescriptor[] props = new MethodDescriptor[0];
        try {
            props = new MethodDescriptor[]{
                    new MethodDescriptor(DisplayimageBean.class.getMethod("onImage", ImageEvent.class))
            };
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return props;
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] props = {

        };
        return props;
    }
}
