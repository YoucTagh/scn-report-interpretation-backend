package fr.minesstetienne.ci.scnri.service;

import org.apache.jena.shacl.ValidationReport;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

/**
 * @author YoucTagh
 */
public class UtilService {

    public static boolean isMediaTypeContainsInList(MediaType mediaType, List<MediaType> mediaTypeList) {
        for (MediaType m : mediaTypeList) {
            if (m.includes(mediaType))
                return true;
        }
        return false;
    }

    public static List<MediaType> getSemanticAcceptedMediaTypes() {
        return Arrays.asList(
                MediaType.parseMediaType("application/rdf+xml"),
                MediaType.parseMediaType("text/turtle"),
                MediaType.parseMediaType("application/n-triples"));
    }

    public static boolean isBetterRepresentationThan(ValidationReport report1, ValidationReport report2) {
        return report1.getEntries().size() < report2.getEntries().size();
    }

}
