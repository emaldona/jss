# Legacy Build Instructions

These instructions have been preserved from the README; if you are referring
to specific parts of the build instructions, they have been moved here.


Build/Test Instructions
-----------------------

### 1. Prepare a work area

(a) For upstream builds which checkout and utilize
the current NSPR and NSS source repositories:

```
# mkdir sandbox
# cd sandbox
# hg clone https://hg.mozilla.org/projects/nspr
# hg clone https://hg.mozilla.org/projects/nss
# git clone git@github.com:dogtagpki/jss.git
# cd ..
```

There is no need to clone every time. For additional builds,
simply use:

```
# cd nspr
# hg pull -u -v
# cd ..
# cd nss
# hg pull -u -v
# cd ..
# cd jss
# git pull -v
# cd ..
````

(b) Alternatively, for upstream builds which use
the NSPR and NSS installed on the system:

```
# mkdir sandbox
# cd sandbox
# export USE_INSTALLED_NSPR=1
# export USE_INSTALLED_NSS=1
# export PKG_CONFIG_ALLOW_SYSTEM_LIBS=1
# export PKG_CONFIG_ALLOW_SYSTEM_CFLAGS=1
# export NSPR_INCLUDE_DIR=`/usr/bin/pkg-config --cflags-only-I nspr | sed 's/-I//'`
# export NSPR_LIB_DIR=`/usr/bin/pkg-config --libs-only-L nspr | sed 's/-L//'`
# export NSS_INCLUDE_DIR=`/usr/bin/pkg-config --cflags-only-I nss | sed 's/-I//'`
# export NSS_LIB_DIR=`/usr/bin/pkg-config --libs-only-L nss | sed 's/-L//'`
# export XCFLAGS="-g"
# git clone https://hg.mozilla.org/projects/jss
# cd ..
```

There is no need to clone every time. For additional builds,
simply use:

```
# cd jss
# git pull -v
# cd ..
```

### 2. Prepare an interactive shell for building

```
# export JAVA_HOME=/etc/alternatives/java_sdk_1.8.0_openjdk
# export USE_64=1
```

NOTE:  JSS will now attempt to verify whether or not these two
environment variables have been set (JAVA_HOME is mandatory;
USE_64 is mandatory on 64-bit platforms when building 64-bit).

The following steps are optional, and left to the discretion of the user:

#### Normal vs. Optimized builds

By default, JSS will be built as a normal binaries.
To create an optimized build, set the following
environment variable:

```
# export BUILD_OPT=1
```

#### Beta vs. Non-Beta builds

Finally, by default, JSS is not built as a "beta" release (as
specified in 'org/mozilla/jss/util/jssver.h'):

```
#define JSS_BETA     PR_FALSE
```

If a "beta" version of JSS is desired, reset this #define (as
specified in 'org/mozilla/jss/util/jssver.h') to:

```
#define JSS_BETA     PR_TRUE
```

### 3. Build JSS

To build JSS, execute the following commands:

```
# cd sandbox/jss
# make clean all
# cd ../..
```

or you can run:

```
# script -c 'make clean all' typescript.build
```

NOTE: When build method (1)(a) is being utilized, if nss has not been
built, it will now automatically be built before jss; if nss has
already been built, only jss will be built/re-built.

### 4. Install JSS on the System (Optional)

If JSS already exists on the system, run something similar to the
following command(s):

```
# sudo mv /usr/lib/java/jss.jar /usr/lib/java/jss.jar.orig
```

If the platform is 32-bit Linux:

```
# sudo mv /usr/lib/jss/libjss.so /usr/lib/jss/libjss.so.orig
```

else if the platform is 64-bit Linux:

```
# sudo mv /usr/lib64/jss/libjss.so /usr/lib64/jss/libjss.so.orig
```

Then install the new JSS binaries:

```
# sudo cp sandbox/dist/xpclass.jar /usr/lib/java/jss.jar
# sudo chown root:root /usr/lib/java/jss.jar
# sudo chmod 644 /usr/lib/java/jss.jar

# sudo cp sandbox/jss/lib/Linux*.OBJ/libjss.so /usr/lib64/jss/libjss.so
# sudo chown root:root /usr/lib64/jss/libjss.so
# sudo chmod 755 /usr/lib64/jss/libjss.so
```

### 5. Run JSS Tests (Optional, but only if build method (1)(a) was utilized)

If build method (1)(a) is being utilized, it is possible to run the built-in JSS tests:

```
# cd sandbox/jss
# make test_jss
# cd ../..
```

or you can run:

```
# script -c 'make test_jss' typescript.tests
```

NOTE: This command is currently only available on Linux and Macintosh
platforms when method (1)(a) has been utilized to build JSS
since the tests are dependent upon the work area as setup in
this method; currently JSS must be built via 'make clean all' before
execution of this command (e.g. - build is separate from test).

### 6. Restoration of non-Test-Only Systems (Optional)

If step (4) above was run, and the system is being used for purposes
other than test, the user may wish to restore the original system JSS
by running the following commands:

```
# sudo mv /usr/lib/java/jss.jar.orig /usr/lib/java/jss.jar
```

If the platform is 32-bit Linux:

```
# sudo mv /usr/lib/jss/libjss.so.orig /usr/lib/jss/libjss.so
```

else if the platform is 64-bit Linux:

```
# sudo mv /usr/lib64/jss/libjss.so.orig /usr/lib64/jss/libjss.so
```

NOTE:  For this procedure, no ownership or permission changes should
be necessary.

### 7. Tagging the Source Code for a Release

During development, several releases may be made.  Consequently, it is
good practice to create a "regular tag" to the source code at these
various points in time using the following format:

```
# git tag -m "message" JSS_<major>_<minor>_<YYYYMMDD>
```

where:

* major = JSS Major Version Number
* minor = JSS Minor Version Number
* YYYY  = 4-digit year (e. g. - 2017)
* MM    = 2-digit month (e. g. - 01, ..., 12)
* DD    = 2-digit day of the month (e. g. - 01, ..., 31)

For example:

```
# git rev-parse HEAD
b3e864205ff0...

# git tag -m "Added tag JSS_4_4_20170328 for changeset b3e864205ff0" JSS_4_4_20170328
```

At the appropriate time, a new major.minor version may be created.  At this
time, it is important to create a maintenance branch for any future changes
to the previous major.minor version:

For example:

```
# git rev-parse HEAD
f00f00f00f00...

# git checkout -b JSS_4_4_BRANCH
```

### 8. Known Issues

Some of the known issues are:

* Mozilla Bug #1346410 - Load JSS libraries appropriately

NOTE:  This issue should not occur unless step (4) above was skipped.

Testing failures were found while working on Bug 1346410 when loading the
JSS libraries to meet requirements of certain operating systems.  Our
investigation revealed that due to the nature of the changes made via this
patch and its interaction with the HMAC Tests (both non-FIPS and FIPS),
that a failure may be encountered on one or more of the HMAC algorithms
causing these two tests to fail.  On 64-bit Linux, for example, the
workaround for this issue is to perform the following steps before
re-running the tests:

* Install the new JSS builds by executing step (4) above
* Execute the following commands:

```
# cd sandbox/jss
# make test_jss
```

NOTE:  If the system is being used for purposes other than test, the user
may wish to restore the original JSS by executing step (6) above.

