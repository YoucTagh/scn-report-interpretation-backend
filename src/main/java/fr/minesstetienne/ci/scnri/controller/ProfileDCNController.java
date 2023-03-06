package fr.minesstetienne.ci.scnri.controller;

import fr.minesstetienne.ci.scnri.service.ProfileDCNService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author YoucTagh
 */
@Controller
@RequestMapping("/swc/api")
public class ProfileDCNController {

    private final ProfileDCNService profileDCNService;

    public ProfileDCNController(ProfileDCNService profileDCNService) {
        this.profileDCNService = profileDCNService;
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/profile", produces = {"text/turtle"})
//    public ResponseEntity getBestRepresentationWithProfile(@RequestParam String iri, @Nullable @RequestHeader("accept-profile") String profileURI) {
//
//        if (profileURI == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        ResponseEntity<String> representationIfAvailable = profileDCNService.getRepresentationIfAvailable(iri, profileURI);
//        if (representationIfAvailable.getStatusCode().equals(HttpStatus.OK)) {
//            return representationIfAvailable;
//        }
//
//        ResourceDetail resourceDetail = sameAsSearchService.findSameResources(iri, UtilService.getSemanticAcceptedMediaTypes());
//        HttpHeaders headers = new HttpHeaders();
//
//        //String bestRepresentationIRI;
//        RepresentationDetail bestRepresentation = new RepresentationDetail()
//                .setValid(false)
//                .setTripleNumber(0L);
//
//        ArrayList<AlternateHeaderItemProfile> alternateHeaderItems = new ArrayList<>();
//
//        for (RepresentationDetail sameAsRepresentation : resourceDetail.getRepresentationDetails()) {
//            if (sameAsRepresentation.getStatus().equals(HttpStatus.OK)) {
//                RepresentationDetail representationDetail = profileDCNService.checkConformanceOfRepresentation(sameAsRepresentation.getIri(), profileURI);
//                if (representationDetail.isValid()) {
//                    if (UtilService.isBetterRepresentationThan(representationDetail, bestRepresentation)) {
//                        if (bestRepresentation.isValid())
//                            alternateHeaderItems.add(
//                                    (AlternateHeaderItemProfile) new AlternateHeaderItemProfile()
//                                            .setNumberOfTriples(bestRepresentation.getTripleNumber())
//                                            .setIri(bestRepresentation.getIri())
//                                            .setMediaType(bestRepresentation.getContentType()));
//                        bestRepresentation = representationDetail;
//                    } else {
//                        alternateHeaderItems.add(
//                                (AlternateHeaderItemProfile) new AlternateHeaderItemProfile()
//                                        .setNumberOfTriples(representationDetail.getTripleNumber())
//                                        .setIri(representationDetail.getIri())
//                                        .setMediaType(representationDetail.getContentType()));
//                    }
//                }
//            }
//        }
//
//        if (bestRepresentation.isValid()) {
//            // Format Alternate header
//            StringBuilder alternateHeaderSB = new StringBuilder();
//            for (AlternateHeaderItemProfile alternateHeaderItem : alternateHeaderItems) {
//                alternateHeaderItem.setAcceptabilityValue((float) alternateHeaderItem.getNumberOfTriples() / bestRepresentation.getTripleNumber());
//                String toString = alternateHeaderItem.toString();
//                alternateHeaderSB.append(toString).append(", ");
//            }
//
//            headers.setContentType(MediaType.valueOf("text/turtle"));
//            headers.set(HttpHeaders.LOCATION, bestRepresentation.getIri());
//            headers.set(HttpHeaders.LINK, "<" + profileURI + ">" + ";rel=\"profile\"");
//            headers.setVary(Stream.of("accept-profile").collect(Collectors.toList()));
//            headers.set("Alternates", alternateHeaderSB.toString());
//
//            return new ResponseEntity<>(bestRepresentation.getContent(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//
//    }

    @RequestMapping(method = RequestMethod.GET, path = "/profile", produces = {"text/turtle"})
    public ResponseEntity getBestRepresentationWithProfile(@Nullable @RequestHeader("accept-profile") String profileURI) {

        if (profileURI == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return profileDCNService.checkConformanceOfRepresentation(profileURI);
    }
}
