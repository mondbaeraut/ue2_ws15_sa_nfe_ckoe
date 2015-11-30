package bean;

import filter.ForwardingFilter;
import interfaces.Readable;
import interfaces.Writeable;

import java.security.InvalidParameterException;

/**
 * Created by mod on 11/29/15.
 */
public class ClosingFilter extends ForwardingFilter {

    public ClosingFilter(Readable input, Writeable output) throws InvalidParameterException {
        super(input, output);
    }
}
