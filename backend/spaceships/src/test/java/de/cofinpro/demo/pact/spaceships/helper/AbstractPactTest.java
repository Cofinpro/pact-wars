package de.cofinpro.demo.pact.spaceships.helper;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * This is a very ugly workaround, using an instance of PactTest which is loaded across different classLoaders
 * to contain the state for mocking.
 * Based on the workaround here:
 * https://github.com/quarkusio/quarkus/issues/22611#issuecomment-1005905445
 */
public abstract class AbstractPactTest {

    protected static Map<String, Object> instanceMap = loadInstanceMap();

    @SuppressWarnings("unchecked")
    protected static Map<String, Object> loadInstanceMap() {
        try {
            // load PactClassLoaderAttributeHolder from the system classloader, so it can be shared between distinct classloaders.
            final Class<?> attributeHolderClass = ClassLoader.getSystemClassLoader().loadClass(PactClassLoaderAttributeHolder.class.getName());

            final Method getInstanceMapMethod = attributeHolderClass.getMethod("getInstanceMap");

            Map<String, Object> imap = (Map<String, Object>) getInstanceMapMethod.invoke(null);
            imap.put(PactState.class.getName(), new PactState());
            return imap;
        } catch(Exception e) {
            throw new IllegalStateException("An unexpected error occurred, while loading PactRestVerificationStates: ", e);
        }
    }

    protected void setState(String state) {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            // use the same classLoader always when reading/writing state
            Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
            Object helperInstance = instanceMap.get(PactState.class.getName());

            if (helperInstance == null) {
                throw new IllegalArgumentException("Could not find instance for " + PactState.class.getName());
            }

            final Method helperMethod = helperInstance.getClass().getMethod("setState", String.class);

            helperMethod.invoke(helperInstance, state);
        } catch(Exception e) {
            throw new IllegalStateException("An unexpected error occurred, while loading invoking the helper: ", e);
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }

    protected String getState() {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            // use the same classLoader always when reading/writing state
            Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
            Object helperInstance = instanceMap.get(PactState.class.getName());

            if (helperInstance == null) {
                throw new IllegalArgumentException("Could not find instance for " + PactState.class.getName());
            }

            final Method helperMethod = helperInstance.getClass().getMethod("getState");

            return (String) helperMethod.invoke(helperInstance);
        } catch(Exception e) {
            throw new IllegalStateException("An unexpected error occurred, while loading invoking the helper: ", e);
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }

}