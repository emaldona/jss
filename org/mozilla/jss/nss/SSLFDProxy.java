package org.mozilla.jss.nss;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;

import org.mozilla.jss.crypto.X509Certificate;
import org.mozilla.jss.pkcs11.PK11Cert;
import org.mozilla.jss.ssl.SSLAlertEvent;
import org.mozilla.jss.util.GlobalRefProxy;

public class SSLFDProxy extends PRFDProxy {
    public PK11Cert clientCert;
    public GlobalRefProxy globalRef;

    public ArrayList<SSLAlertEvent> inboundAlerts;
    public int inboundOffset;

    public ArrayList<SSLAlertEvent> outboundAlerts;
    public int outboundOffset;

    public SSLFDProxy(byte[] pointer) {
        super(pointer);
    }

    public void SetClientCert(X509Certificate cert) throws IllegalArgumentException {
        if (!(cert instanceof PK11Cert)) {
            throw new IllegalArgumentException("Unable to cast given certificate to PK11Cert: " + cert.getClass().getName());
        }

        clientCert = (PK11Cert)cert;
    }

    protected native void releaseNativeResources();

    protected void finalize() throws Throwable {
        super.finalize();
    }
}
