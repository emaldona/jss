package org.mozilla.jss.netscape.security.util;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class IA5Charset extends Charset {

    public IA5Charset() {
        super("ASN.1-IA5", null);
    }

    @Override
    public boolean contains(Charset cs) {
        return false;
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new IA5CharsetDecoder(this);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new IA5CharsetEncoder(this);
    }
}
