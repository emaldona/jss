/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package org.mozilla.jss;

public final class InvalidLengthException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidLengthException() {}
    public InvalidLengthException(String mesg) {
        super(mesg);
    }

    public InvalidLengthException(String mesg, Throwable cause) {
        super(mesg, cause);
    }

    public InvalidLengthException(String mesg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(mesg, cause, enableSuppression, writableStackTrace);
    }
}
