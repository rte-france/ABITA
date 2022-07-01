package com.services.common.util;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Path resolver.
 * @author
 *
 */
public class ClasspathResolver implements EntityResolver {

    /** resolver map */
    private static Map<String, String> publicIdsToNames = new HashMap<String, String>();

    /**
     * Default contructor.
     */
    public ClasspathResolver() {
        //
    }
    /**
     *
     * @param publicId public id
     * @param resource resource name
     */
    public static void registerDocType(String publicId, String resource) {
        publicIdsToNames.put(publicId, resource);
    }

    /**
     * @param publicId public resource id
     * @param systemId default resource if puublicId not found
     * @throws IOException I/O exception
     * @return input source
     */
    public InputSource resolveEntity(String publicId, String systemId) throws IOException {
        String resource = publicIdsToNames.get(publicId);
        if (resource == null) {
            resource = systemId;
        }
        InputStream stream = getClass().getResourceAsStream(resource);
        if (stream == null) {
            return null;
        } else {
            return new InputSource(stream);
        }
    }
}
